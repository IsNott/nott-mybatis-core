package org.nott.mybatis.provider;

import org.nott.mybatis.model.MybatisSqlBean;
import org.nott.mybatis.sql.SqlBuilder;
import org.nott.mybatis.support.aop.ConCurrentMapperAopFactory;

import java.util.Map;

/**
 * @author Nott
 * @date 2024-5-16
 */
public class BaseInsertProvider {

    public static String insert(Map<String,Object> param){
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        Object entity = param.get("entity");
        return SqlBuilder.buildInsertSql(bean, entity);
    }
}
