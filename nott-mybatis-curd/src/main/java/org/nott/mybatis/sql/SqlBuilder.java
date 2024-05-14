package org.nott.mybatis.sql;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.nott.mybatis.annotations.CustTableName;
import org.nott.mybatis.model.MybatisSqlBean;
import org.nott.mybatis.sql.model.Colum;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Nott
 * @date 2024-5-13
 */
public class SqlBuilder {

    public static String buildSql(MybatisSqlBean sqlBean, SimpleSqlConditionBuilder simpleSqlConditionBuilder){
        String tableName = sqlBean.getTableName();

        SQL sql = new SQL();
        sql.FROM(tableName);

        String finallySql;

        if(simpleSqlConditionBuilder == null){
            for (String tableColum : sqlBean.getTableColums()) {
                sql.SELECT(tableColum);
            }
        }else {
            List<SqlConditions> sqlConditions = simpleSqlConditionBuilder.getSqlConditions();
            List<Colum> sqlColum = simpleSqlConditionBuilder.getSqlColum();
            if(CollectionUtils.isEmpty(sqlColum)){
                for (String tableColum : sqlBean.getTableColums()) {
                    sql.SELECT(tableColum);
                }
            }else {
                StringBuilder builder = new StringBuilder();
                for (Colum colum : sqlColum) {
                    String asName = colum.getAsName();
                    String fieldName = colum.getFieldName();
                    builder.append(StringUtils.isNotEmpty(asName) ? fieldName + " as " + asName : fieldName);
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
}
