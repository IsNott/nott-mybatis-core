package org.nott.mybatis.provider;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.nott.mybatis.annotations.CustTableName;
import org.nott.mybatis.sql.SimpleSqlConditionBuilder;
import org.nott.mybatis.sql.SqlBuilder;
import org.nott.mybatis.sql.SqlConditions;
import org.nott.mybatis.sql.SqlOperator;
import org.nott.mybatis.sql.model.Colum;
import org.springframework.util.CollectionUtils;


import java.util.List;

public class BaseSelectProvider<T> {

    /**
     * TODO 去除泛型，从ThreadLocal中获取当前线程的mapper和实体类数据
     * 当前类的泛型
     */
    private Class genericSuperClass;

    public BaseSelectProvider() {
    }

    //    public BaseSelectProvider(){
//        Type genericSuperType = this.getClass().getGenericSuperclass();
//        if(genericSuperType == null){
//            throw new GenericClassException("Generic class not found");
//        }
////        Type[] types = ((ParameterizedType) genericSuperType).getActualTypeArguments();
//
//        Type[] p = ((ParameterizedType) genericSuperType).getActualTypeArguments();
//        System.out.println(Arrays.toString(p));
//        this.genericSuperClass = (Class) p[0];
//    }



    public String selectOne(T t){
        SimpleSqlConditionBuilder.create(t.getClass()).limit(1);
        return SqlBuilder.buildSql(t,null);
    }

    public String selectOneByCondition(T t,SimpleSqlConditionBuilder simpleSqlConditionBuilder){
        simpleSqlConditionBuilder.setLimit(1);
        return SqlBuilder.buildSql(t, simpleSqlConditionBuilder);
    }

    public String selectList(T t){
        return SqlBuilder.buildSql(t,null);
    }

    public String selectListByCondition(T t,SimpleSqlConditionBuilder simpleSqlConditionBuilder){
        return SqlBuilder.buildSql(t, simpleSqlConditionBuilder);
    }
}
