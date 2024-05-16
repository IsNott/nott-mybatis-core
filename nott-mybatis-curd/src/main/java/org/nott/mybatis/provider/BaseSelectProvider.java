package org.nott.mybatis.provider;


import org.nott.mybatis.model.MybatisSqlBean;
import org.nott.mybatis.sql.QuerySqlConditionBuilder;
import org.nott.mybatis.sql.SqlBuilder;
import org.nott.mybatis.support.aop.ConCurrentMapperAopFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * 基础 mybatis selectProvider
 */
public class BaseSelectProvider {

    public static String selectById(Map<String, Serializable> map) {
        Serializable id = map.get("id");
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildFindByPkSql(bean, id);
    }

    public static String selectOne() {
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        QuerySqlConditionBuilder.build().limit(1);
        return SqlBuilder.buildSql(bean, null);
    }

    public static String selectOneByCondition(QuerySqlConditionBuilder querySqlConditionBuilder) {
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        querySqlConditionBuilder.setLimit(1);
        return SqlBuilder.buildSql(bean, querySqlConditionBuilder);
    }

    public static String selectList() {
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildSql(bean, null);
    }

    public static String selectListByCondition(QuerySqlConditionBuilder querySqlConditionBuilder) {
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildSql(bean, querySqlConditionBuilder);
    }

    // TODO 分页修改
    public static String pageCountByCondition(QuerySqlConditionBuilder querySqlConditionBuilder) {
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildPageCount(bean, querySqlConditionBuilder);
    }

    public static String pageCount() {
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildPageCount(bean, null);
    }

    // TODO Page select
    public static String page(Map<String, Object> param) {
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        QuerySqlConditionBuilder builder = param.containsKey("querySqlConditionBuilder") ? (QuerySqlConditionBuilder) param.get("querySqlConditionBuilder") : null;
        Integer page = (Integer) param.get("page");
        Integer size = (Integer) param.get("size");
        return SqlBuilder.buildPage(page, size, bean, builder);
    }
}
