package org.nott.mybatis.provider;

import org.nott.mybatis.model.MybatisSqlBean;
import org.nott.mybatis.sql.builder.SqlBuilder;
import org.nott.mybatis.support.aop.ConCurrentMapperAopFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Nott
 * @date 2024-5-16
 */
public class BaseDeleteProvider {

    public static String deleteById(Map<String,Serializable> param){
        Serializable id = param.get("id");
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildDeleteByPkSql(bean,id);
    }

    public static String deleteByIds(Map<String,List<?>> param){
        List<?> ids = param.get("ids");
        MybatisSqlBean bean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildDeleteListSql(bean, ids);
    }
}
