package org.nott.mybatis.provider;


import org.nott.mybatis.model.MybatisSqlBean;
import org.nott.mybatis.sql.QuerySqlConditionBuilder;
import org.nott.mybatis.sql.SqlBuilder;
import org.nott.mybatis.support.aop.ConCurrentMapperAopFactory;

import java.io.Serializable;
import java.util.Map;


public class BaseSelectProvider {

    public static String selectById(Map<String,Serializable> map){
        Serializable id = map.get("id");
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildFindByPkSql(bean, id);
    }


    public static String selectOne(){
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        QuerySqlConditionBuilder.build().limit(1);
        return SqlBuilder.buildSql(bean,null);
    }

    public static String selectOneByCondition(QuerySqlConditionBuilder querySqlConditionBuilder){
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        querySqlConditionBuilder.setLimit(1);
        return SqlBuilder.buildSql(bean, querySqlConditionBuilder);
    }

    public static String selectList(){
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildSql(bean,null);
    }

    public static String selectListByCondition(QuerySqlConditionBuilder querySqlConditionBuilder){
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildSql(bean, querySqlConditionBuilder);
    }

    //TODO Page select
}
