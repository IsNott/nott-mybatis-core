package org.nott.mybatis.support.aop;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.ArrayUtils;
import org.nott.mybatis.annotations.Colum;
import org.nott.mybatis.annotations.CustTableName;
import org.nott.mybatis.annotations.TableId;
import org.nott.mybatis.exception.BeanInstanceException;
import org.nott.mybatis.exception.GenericClassException;
import org.nott.mybatis.model.MybatisSqlBean;
import org.nott.mybatis.model.Pk;
import org.nott.mybatis.support.Interceptor.MybatisAopInterceptor;
import org.nott.mybatis.support.model.ExecuteMapperContextBean;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Nott
 * @date 2024-5-13
 */

public class ConCurrentMapperAopFactory {

    public ConCurrentMapperAopFactory() {
    }

    public static final ConCurrentMapperAopFactory FACTORY = new ConCurrentMapperAopFactory();

    /**
     * mapper与所属泛型的map
     */
    public static final Map<String,Class<?>> mapperGenericClassMap = new ConcurrentHashMap<>();

    /**
     * 所属实体的map
     */
    public static final Map<String, MybatisSqlBean> beanMap = new ConcurrentHashMap<>();

    public static MybatisSqlBean getBean(){
        return FACTORY.getCurrentMapperBean();
    }

    public static Class<?> getGenericClass(){
        return FACTORY.getCurrentMapperGenericClass();
    }

    // 获取泛型并实例化bean
    // 封装对象所属sql表/字段属性
    // TODO 封装分页方法信息、拓展支持的返回类class
    public MybatisSqlBean getCurrentMapperBean() {

        Class<?> currentMapperClass = getCurrentMapperClass();

        String name = Objects.requireNonNull(currentMapperClass).getName();

        MybatisSqlBean sqlBean = beanMap.get(name);

        // Single Check
        if (Objects.nonNull(sqlBean)) {
            return sqlBean;
        }

        // 仿双重校验锁
        synchronized (currentMapperClass) {
            sqlBean = beanMap.get(name);
            // Double Check
            if (sqlBean != null) {
                return sqlBean;
            }
            // 获取commonMapper的泛型
            Class<?> genericSuperClass = getGenericSuperClass(currentMapperClass);
            Object bean = null;
            try {
                bean = genericSuperClass.getDeclaredConstructor().newInstance();
                sqlBean = buildSqlBeanByOriginal(bean);
            } catch (Exception e) {
                throw new BeanInstanceException(e.getMessage());
            }

            beanMap.put(name, sqlBean);
            return sqlBean;
        }

    }

    private static MybatisSqlBean buildSqlBeanByOriginal(Object bean) {
        if (bean == null) {
            throw new RuntimeException("Build SqlBean Requires Origin Bean Non Null");
        }
        Class<?> beanClass = bean.getClass();
        String tableName = getTableName(beanClass);
        List<String> colums = getTableColums(bean);
        Pk pk = getPk(bean);
        MybatisSqlBean sqlBean = MybatisSqlBean.builder().originalBean(bean).tableName(tableName)
                .tableColums(colums).pk(pk).build();
        return sqlBean;
    }

    private static Pk getPk(Object bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        Objects.requireNonNull(fields);
        if (fields.length == 0) {
            throw new RuntimeException(String.format("Bean %s Field length == 0", bean.getClass()));
        }
        Field pkField = Arrays.stream(fields).filter(r -> r.isAnnotationPresent(TableId.class)).findAny().orElse(null);
        Objects.requireNonNull(pkField);
        return Pk.builder().name(pkField.getName()).type(pkField.getType()).build();
    }

    private static List<String> getTableColums(Object bean) {
        List<String> colums = new ArrayList<>();
        Field[] fields = bean.getClass().getDeclaredFields();
        Objects.requireNonNull(fields);
        if (fields.length == 0) {
            throw new RuntimeException(String.format("Bean %s Field length == 0", bean.getClass()));
        }
        for (Field field : fields) {
            // 字段保存value
            if (field.isAnnotationPresent(Colum.class)) {
                colums.add(field.getDeclaredAnnotation(Colum.class).value());
            } else {
                colums.add(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()));
            }
        }
        return colums;
    }

    public static String getTableName(Class<?> beanClass){
        boolean annotationPresent = beanClass.isAnnotationPresent(CustTableName.class);
        String tableName;
        if(annotationPresent){
            CustTableName custTableName = (CustTableName) beanClass.getAnnotation(CustTableName.class);
            tableName = custTableName.name();
        }else {
            String simpleName = beanClass.getSimpleName();
            // 大驼峰转下划线处理
            tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);
        }
        return tableName;
    }

    private static Class<?> getCurrentMapperClass() {
        ThreadLocal<ExecuteMapperContextBean> context = MybatisAopInterceptor.getContext();

        ExecuteMapperContextBean bean = context.get();

        Class<?> currentMapperClass = bean.getCurrentMapperClass();

        if(Objects.isNull(currentMapperClass)){
            return null;
        }

        return currentMapperClass;

    }

    // 获取泛型
    public Class<?> getCurrentMapperGenericClass() {

        Class<?> currentMapperClass = getCurrentMapperClass();

        String name = Objects.requireNonNull(currentMapperClass).getName();

        Class<?> clazz = mapperGenericClassMap.get(name);

        if (Objects.nonNull(clazz)) {
            return clazz;
        }

        synchronized (currentMapperClass) {
            clazz = mapperGenericClassMap.get(name);
            if (clazz != null) {
                return clazz;
            }
            Class<?> genericSuperClass = getGenericSuperClass(currentMapperClass);
            mapperGenericClassMap.put(name, genericSuperClass);

            clazz = genericSuperClass;
        }
        return clazz;
    }

    private static Class<?> getGenericSuperClass(Class<?> currentMapperClass) {
        // 获取当前mapper的上一级的第一个泛型
        Type[] interfaces = currentMapperClass.getGenericInterfaces();
       if(ArrayUtils.isEmpty(interfaces)){
           throw new RuntimeException("Current Mapper Don't extend any Interface");
       }
        Type extendInterface = interfaces[0];
        ParameterizedType parameterizedType = (ParameterizedType) extendInterface;
        Class<?> interfaceGenericClass = (Class)parameterizedType.getActualTypeArguments()[0];
        Objects.requireNonNull(interfaceGenericClass);
        return interfaceGenericClass;
    }


}
