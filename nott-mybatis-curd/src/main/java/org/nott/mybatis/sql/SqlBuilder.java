package org.nott.mybatis.sql;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.nott.mybatis.annotations.TableId;
import org.nott.mybatis.exception.SqlParseException;
import org.nott.mybatis.model.MybatisSqlBean;
import org.nott.mybatis.model.Pk;
import org.nott.mybatis.sql.enums.SqlOperator;
import org.nott.mybatis.sql.model.Colum;
import org.nott.mybatis.sql.model.UpdateCombination;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Sql构造器
 * @author Nott
 * @date 2024-5-13
 */
public class SqlBuilder {

    public static String buildSql(MybatisSqlBean sqlBean, QuerySqlConditionBuilder querySqlConditionBuilder){
        String tableName = sqlBean.getTableName();

        SQL sql = new SQL();
        sql.FROM(tableName);

        List<String> tableColums = sqlBean.getTableColums();

        String finallySql;

        if (querySqlConditionBuilder == null) {
            for (String tableColum : tableColums) {
                sql.SELECT(tableColum);
            }
        } else {
            List<SqlConditions> sqlConditions = querySqlConditionBuilder.getSqlConditions();
            List<Colum> sqlColum = querySqlConditionBuilder.getSqlColum();
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
            if (querySqlConditionBuilder.getLimit() != null) {
                sql.LIMIT(querySqlConditionBuilder.getLimit());
            }
        }

        finallySql = sql.toString();
        System.out.println("Generate mybatis sql = " + finallySql);
        return finallySql;
    }

    /**
     * 重组装字符串类型对象的值
     * @param value
     * @return
     */
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

    public static String buildUpdateByIdSql(MybatisSqlBean mybatisSqlBean, Object entity) {
        Pk pk = mybatisSqlBean.getPk();
        String tableName = mybatisSqlBean.getTableName();

        Field[] fields = entity.getClass().getDeclaredFields();
        Field pkField = Arrays.stream(fields).filter(r -> r.isAnnotationPresent(TableId.class)).findFirst().orElse(null);
        Objects.requireNonNull(pkField, "Primary Key Field Not declared");
        if (ArrayUtils.isEmpty(fields)) {
            throw new SqlParseException(String.format("%s Entity fields do not allow empty", entity.getClass().getName()));
        }

        SQL sql = new SQL() {{
            UPDATE(tableName);
        }};

        Object pkValue;
        try {
            pkField.setAccessible(true);
            pkValue = reassembleValue(pkField.get(entity));
            for (Field field : fields) {
                // 需要操作私有变量
                field.setAccessible(true);
                boolean hasAnnotationPresent = field.isAnnotationPresent(org.nott.mybatis.annotations.Colum.class);
                if (field.isAnnotationPresent(TableId.class)) {
                    continue;
                }
                if(Objects.isNull(field.get(entity))){
                    continue;
                }
                String name;
                Object value = reassembleValue(field.get(entity));
                if (hasAnnotationPresent) {
                    name = StringUtils.isNotEmpty(field.getAnnotation(org.nott.mybatis.annotations.Colum.class).value()) ?
                            field.getAnnotation(org.nott.mybatis.annotations.Colum.class).value() : field.getAnnotation(org.nott.mybatis.annotations.Colum.class).name();
                } else {
                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
                }
                sql.SET(name + SqlOperator.EQ.getValue() + value);
            }
            sql.WHERE(pk.getName() + SqlOperator.EQ.getValue() + pkValue);
        } catch (IllegalAccessException e) {
            throw new SqlParseException(e);
        }

        return sql.toString();
    }

    public static String buildUpdateSql(MybatisSqlBean mybatisSqlBean, UpdateSqlConditionBuilder updateSqlConditionBuilder) {
        Pk pk = mybatisSqlBean.getPk();


        String tableName = mybatisSqlBean.getTableName();
        List<UpdateCombination> updateCombinations = updateSqlConditionBuilder.getUpdateCombinations();
        if (updateCombinations.isEmpty()) {
            throw new SqlParseException("Update Combination Empty not allow.");
        }

        SQL sql = new SQL() {{
            UPDATE(tableName);
        }};

        for (UpdateCombination updateCombination : updateCombinations) {
            String key = updateCombination.getKey();
            Object combinationValue = updateCombination.getValue();
            combinationValue = reassembleValue(combinationValue);
            sql.SET(key + SqlOperator.EQ + combinationValue);
        }
//        sql.WHERE(pk.getName() + SqlOperator.EQ + valStr);

        return sql.toString();
    }
}
