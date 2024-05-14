package org.nott.mybatis.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.nott.mybatis.exception.SqlParseException;
import org.nott.mybatis.model.MybatisSqlBean;
import org.nott.mybatis.model.Pk;
import org.nott.mybatis.sql.enums.SqlOperator;
import org.nott.mybatis.sql.model.Colum;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
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

        List<String> tableColums = sqlBean.getTableColums();

        String finallySql;

        if (simpleSqlConditionBuilder == null) {
            for (String tableColum : tableColums) {
                sql.SELECT(tableColum);
            }
        } else {
            List<SqlConditions> sqlConditions = simpleSqlConditionBuilder.getSqlConditions();
            List<Colum> sqlColum = simpleSqlConditionBuilder.getSqlColum();
            if (CollectionUtils.isEmpty(sqlColum)) {
                for (String tableColum : tableColums) {
                    sql.SELECT(tableColum);
                }
            } else {
                StringBuilder builder = new StringBuilder();
                // 判断查询字段是否符合
                for (Colum colum : sqlColum) {
                    String asName = colum.getAsName();
                    String fieldName = colum.getFieldName();
                    if (!tableColums.contains(fieldName)) {
                        throw new SqlParseException(String.format("Provide field '%s' not exist '%s' table colum.", fieldName, sqlBean.getTableName()));
                    }
                    builder.append(StringUtils.isNotEmpty(asName) ? fieldName + " as " + asName : fieldName);
                }
                sql.SELECT(builder.toString());
            }
            if (!CollectionUtils.isEmpty(sqlConditions)) {
                sql.WHERE("1=1");
                for (SqlConditions sqlCondition : sqlConditions) {
                    SqlOperator sqlOperator = sqlCondition.getSqlOperator();
                    Object value = sqlCondition.getValue();
                    value = reassembleValue(value);
                    sql.WHERE(sqlCondition.getColum() + sqlOperator.getValue() + value);
                }
            }
            if (simpleSqlConditionBuilder.getLimit() != null) {
                sql.LIMIT(simpleSqlConditionBuilder.getLimit());
            }
        }

        finallySql = sql.toString();
        System.out.println("Generate mybatis sql = " + finallySql);
        return finallySql;
    }

    private static Object reassembleValue(Object value) {
        Class<?> valClass = value.getClass();
        String name = valClass.getName();

        if (String.class.getName().equals(name)) {
            value = "'" + value + "'";
        }
        return value;
    }

    public static String buildFindByPkSql(MybatisSqlBean bean, Serializable value) {
        String valStr = value.toString();
        String tableName = bean.getTableName();
        List<String> colum = bean.getTableColums();
        valStr = (String) reassembleValue(valStr);
        Pk pk = bean.getPk();
        SQL sql = new SQL() {{
            for (String colum : colum) {
                SELECT(colum);
            }
            FROM(tableName);
        }};
        sql.WHERE(pk.getName() + SqlOperator.EQ.getValue() + valStr);
        return sql.toString();
    }
}
