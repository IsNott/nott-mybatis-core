package org.nott.mybatis.provider;


import org.nott.mybatis.model.MybatisSqlBean;
import org.nott.mybatis.sql.SimpleSqlConditionBuilder;
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
        SimpleSqlConditionBuilder.build().limit(1);
        return SqlBuilder.buildSql(bean,null);
    }

    public static String selectOneByCondition(SimpleSqlConditionBuilder simpleSqlConditionBuilder){
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        simpleSqlConditionBuilder.setLimit(1);
        return SqlBuilder.buildSql(bean, simpleSqlConditionBuilder);
    }

    public static String selectList(){
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildSql(bean,null);
    }

    public static String selectListByCondition(SimpleSqlConditionBuilder simpleSqlConditionBuilder){
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildSql(bean, simpleSqlConditionBuilder);
    }

    //TODO Page select
}
