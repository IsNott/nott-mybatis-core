package org.nott.mybatis.provider;

import org.nott.mybatis.model.MybatisSqlBean;
import org.nott.mybatis.sql.builder.SqlBuilder;
import org.nott.mybatis.sql.builder.UpdateSqlConditionBuilder;
import org.nott.mybatis.support.aop.ConCurrentMapperAopFactory;
import java.util.Map;

/**
 * Base update sql provider
 *
 * @author Nott
 * @date 2024-5-14
 */
public class BaseUpdateProvider {

    public static String updateById(Map<String, Object> map) {
        MybatisSqlBean mybatisSqlBean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildUpdateByIdSql(mybatisSqlBean,map.get("entity"));
    }

    public static  String updateByCondition(UpdateSqlConditionBuilder updateSqlConditionBuilder){
        MybatisSqlBean mybatisSqlBean = ConCurrentMapperAopFactory.getBean();
        return SqlBuilder.buildUpdateSql(mybatisSqlBean,updateSqlConditionBuilder);

    }

}
