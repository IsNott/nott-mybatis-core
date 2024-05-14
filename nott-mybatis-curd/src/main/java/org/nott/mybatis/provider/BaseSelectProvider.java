package org.nott.mybatis.provider;


import org.nott.mybatis.model.MybatisSqlBean;
import org.nott.mybatis.sql.SimpleSqlConditionBuilder;
import org.nott.mybatis.sql.SqlBuilder;
import org.nott.mybatis.support.aop.ConCurrentMapperAopFactory;


public class BaseSelectProvider {


    public String selectOne(){
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        SimpleSqlConditionBuilder.create(bean.getOriginalBeanClass()).limit(1);
        return SqlBuilder.buildSql(bean,null);
    }

    public String selectOneByCondition(SimpleSqlConditionBuilder simpleSqlConditionBuilder){
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        simpleSqlConditionBuilder.setLimit(1);
        return SqlBuilder.buildSql(bean, simpleSqlConditionBuilder);
    }

    public String selectList(){
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildSql(bean,null);
    }

    public String selectListByCondition(SimpleSqlConditionBuilder simpleSqlConditionBuilder){
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildSql(bean, simpleSqlConditionBuilder);
    }
}
