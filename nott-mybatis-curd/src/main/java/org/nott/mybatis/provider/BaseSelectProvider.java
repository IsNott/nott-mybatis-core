package org.nott.mybatis.provider;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.nott.mybatis.annotations.CustTableName;
import org.nott.mybatis.sql.SimpleSqlConditionBuilder;
import org.nott.mybatis.sql.SqlConditions;
import org.nott.mybatis.sql.SqlOperator;
import org.nott.mybatis.sql.model.Colum;
import org.springframework.util.CollectionUtils;


import java.util.List;

public class BaseSelectProvider<T> {

    /**
     * 当前类的泛型
     */
    private Class genericSuperClass;

    public BaseSelectProvider(){
//        Type genericSuperType = this.getClass().getGenericSuperclass();
//        if(genericSuperType == null){
//            throw new GenericClassException("Generic class not found");
//        }
////        Type[] types = ((ParameterizedType) genericSuperType).getActualTypeArguments();
//
//        Type[] p = ((ParameterizedType) genericSuperType).getActualTypeArguments();
//        System.out.println(Arrays.toString(p));
//        this.genericSuperClass = (Class) p[0];
    }

    public String buildSql(T t,SimpleSqlConditionBuilder simpleSqlConditionBuilder){
        Class<?> aClass = t.getClass();
        boolean annotationPresent = aClass.isAnnotationPresent(CustTableName.class);
        String tableName;
        if(annotationPresent){
            CustTableName custTableName = (CustTableName) aClass.getAnnotation(CustTableName.class);
            tableName = custTableName.name();
        }else {
            String simpleName = aClass.getSimpleName();
            // 大驼峰转下划线处理
            tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);
        }

        SQL sql = new SQL();
        sql.FROM(tableName);

        String finallySql;

        if(simpleSqlConditionBuilder == null){
            sql.SELECT("*");
        }else {
            List<SqlConditions> sqlConditions = simpleSqlConditionBuilder.getSqlConditions();
            List<Colum> sqlColum = simpleSqlConditionBuilder.getSqlColum();
            if(CollectionUtils.isEmpty(sqlColum)){
                sql.SELECT("*");
            }else {
                StringBuilder builder = new StringBuilder();
                for (Colum colum : sqlColum) {
                    String asName = colum.getAsName();
                    String fieldName = colum.getFieldName();
                    builder.append(StringUtils.isNotEmpty(asName) ? fieldName + "as " + asName : fieldName);
                }
                sql.SELECT(builder.toString());
            }
            if(!CollectionUtils.isEmpty(sqlConditions)){
                sql.WHERE("1=1");
                for (SqlConditions sqlCondition : sqlConditions) {
                    SqlOperator sqlOperator = sqlCondition.getSqlOperator();
                    Object value = sqlCondition.getValue();
                    Class<?> valClass = value.getClass();
                    String simpleName = valClass.getSimpleName();
                    if("String".equals(simpleName)){
                        value = "'" + value + "'";
                    }
                    sql.WHERE(sqlCondition.getColum() + sqlOperator.getValue() + value);
                }
            }
            if(simpleSqlConditionBuilder.getLimit() != null){
                sql.LIMIT(simpleSqlConditionBuilder.getLimit());
            }
        }

        finallySql = sql.toString();
        System.out.println("Generate mybatis sql = " + finallySql);
        return finallySql;
    }

    public String selectOne(T t){
        SimpleSqlConditionBuilder.create(t.getClass()).limit(1);
        return buildSql(t,null);
    }

    public String selectOneByCondition(T t,SimpleSqlConditionBuilder simpleSqlConditionBuilder){
        simpleSqlConditionBuilder.setLimit(1);
        return buildSql(t, simpleSqlConditionBuilder);
    }

    public String selectList(T t){
        return buildSql(t,null);
    }

    public String selectListByCondition(T t,SimpleSqlConditionBuilder simpleSqlConditionBuilder){
        return buildSql(t, simpleSqlConditionBuilder);
    }
}
