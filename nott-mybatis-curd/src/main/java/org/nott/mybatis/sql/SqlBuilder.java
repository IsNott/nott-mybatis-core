package org.nott.mybatis.sql;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.nott.mybatis.annotations.CustTableName;
import org.nott.mybatis.sql.model.Colum;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Nott
 * @date 2024-5-13
 */
public class SqlBuilder {

    public static String buildSql(Object t,SimpleSqlConditionBuilder simpleSqlConditionBuilder){
        Class<?> aClass = t.getClass();
        boolean annotationPresent = aClass.isAnnotationPresent(CustTableName.class);
        String tableName;
        if(annotationPresent){
            CustTableName custTableName = (CustTableName) aClass.getAnnotation(CustTableName.class);
            tableName = custTableName.name();
        }else {
            String simpleName = aClass.getSimpleName();
            // 大驼峰转下划线处理
            tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);
        }

        SQL sql = new SQL();
        sql.FROM(tableName);

        String finallySql;

        if(simpleSqlConditionBuilder == null){
            sql.SELECT("*");
        }else {
            List<SqlConditions> sqlConditions = simpleSqlConditionBuilder.getSqlConditions();
            List<Colum> sqlColum = simpleSqlConditionBuilder.getSqlColum();
            if(CollectionUtils.isEmpty(sqlColum)){
                sql.SELECT("*");
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
