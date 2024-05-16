package org.nott.mybatis.sql;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.nott.mybatis.annotations.TableId;
import org.nott.mybatis.constant.SQLConstant;
import org.nott.mybatis.exception.SqlParseException;
import org.nott.mybatis.model.MybatisSqlBean;
import org.nott.mybatis.model.Pk;
import org.nott.mybatis.sql.enums.LikeMode;
import org.nott.mybatis.sql.enums.SqlDDLOption;
import org.nott.mybatis.sql.enums.SqlOperator;
import org.nott.mybatis.sql.model.Colum;
import org.nott.mybatis.sql.model.Order;
import org.nott.mybatis.sql.model.UpdateCombination;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Sql构造器
 *
 * @author Nott
 * @date 2024-5-13
 */
public class SqlBuilder {

    public static SQL generateBaseSql(MybatisSqlBean sqlBean) {
        return new SQL().FROM(sqlBean.getTableName());
    }

    public static SQL generateBaseSql(MybatisSqlBean sqlBean, boolean isDelete) {
        return isDelete ? new SQL().DELETE_FROM(sqlBean.getTableName()) : generateBaseSql(sqlBean);
    }

    public static SQL buildMybatisSqlEntity(MybatisSqlBean sqlBean, QuerySqlConditionBuilder querySqlConditionBuilder) {
        SQL sql = generateBaseSql(sqlBean);

        List<String> tableColums = sqlBean.getTableColums();
        List<SqlConditions> sqlConditions = querySqlConditionBuilder.getSqlConditions();
        List<Colum> sqlColum = querySqlConditionBuilder.getSqlColum();

        buildSelectSql(sqlBean, sql, tableColums, sqlColum);
        buildWhereSql(sql, sqlConditions);
        buildOrderBySql(sql, querySqlConditionBuilder);
        buildGroupBySql(sql, querySqlConditionBuilder);
        buildLimitSql(sql, querySqlConditionBuilder);
        buildHavingSql(sql, querySqlConditionBuilder);
        return sql;
    }

    private static void buildHavingSql(SQL sql, QuerySqlConditionBuilder querySqlConditionBuilder) {
        List<String> havingSql = querySqlConditionBuilder.getHavingSql();
        if (havingSql.isEmpty()) {
            return;
        }
        sql.HAVING(havingSql.toArray(String[]::new));
    }

    private static void buildGroupBySql(SQL sql, QuerySqlConditionBuilder querySqlConditionBuilder) {
        List<String> groupByColum = querySqlConditionBuilder.getGroupByColum();
        if (groupByColum.isEmpty()) {
            return;
        }
        sql.GROUP_BY(groupByColum.toArray(String[]::new));
    }

    private static void buildAppendSql(QuerySqlConditionBuilder querySqlConditionBuilder, StringBuilder sql) {
        if (!querySqlConditionBuilder.getAppend().isEmpty()) {
            for (String appendSQL : querySqlConditionBuilder.getAppend()) {
                sql.append(appendSQL);
            }
        }
    }

    private static void buildLimitSql(SQL sql, QuerySqlConditionBuilder querySqlConditionBuilder) {
        if (querySqlConditionBuilder.getLimit() != null) {
            sql.LIMIT(querySqlConditionBuilder.getLimit());
        }
    }

    public static String buildSql(MybatisSqlBean sqlBean, QuerySqlConditionBuilder querySqlConditionBuilder) {
        boolean hasBuilder = querySqlConditionBuilder != null;
        if (!hasBuilder) {
            querySqlConditionBuilder = QuerySqlConditionBuilder.build();
        }
        StringBuilder sqlStringBuilder = new StringBuilder(buildMybatisSqlEntity(sqlBean, querySqlConditionBuilder).toString());
        buildAppendSql(querySqlConditionBuilder, sqlStringBuilder);
        String finallySql = sqlStringBuilder.toString();
        System.out.println("Generate mybatis sql = " + finallySql);
        return finallySql;
    }

    private static void buildSelectSql(MybatisSqlBean sqlBean, SQL sql, List<String> tableColums, List<Colum> sqlColum) {
        if (sqlColum == null || CollectionUtils.isEmpty(sqlColum)) {
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
    }

    private static void buildWhereSql(SQL sql, List<SqlConditions> sqlConditions) {
        if (sqlConditions != null && !CollectionUtils.isEmpty(sqlConditions)) {
            sql.WHERE("1=1");
            for (SqlConditions sqlCondition : sqlConditions) {
                boolean isLikeSql = sqlCondition.getLikeMode() != null;
                SqlOperator sqlOperator = sqlCondition.getSqlOperator();
                Object value = sqlCondition.getValue();
                if (isLikeSql) {
                    value = buildLikeAround(sqlCondition.getLikeMode(), value);
                }
                value = reassembleValue(value);
                sql.WHERE(sqlCondition.getColum() + sqlOperator.getValue() + value);
            }
        }
    }

    private static void buildOrderBySql(SQL sql, QuerySqlConditionBuilder querySqlConditionBuilder) {
        List<Order> orderColum = querySqlConditionBuilder.getOrderColum();
        if (orderColum.isEmpty()) {
            return;
        }
        for (Order order : orderColum) {
            String colum = order.getColum();
            String mode = order.getOrderMode().getMode();
            sql.ORDER_BY(colum + mode);
        }
    }

    /**
     * 变量加入"%"通配符
     * @param mode like模式枚举
     * @param value 参与搜索的变量
     * @return 加入通配符后的变量
     */
    private static Object buildLikeAround(LikeMode mode, Object value) {
        switch (mode) {
            default -> value = value;
            case ALL -> value = SQLConstant.PERCENTAGE + value + SQLConstant.PERCENTAGE;
            case BEFORE -> value = SQLConstant.PERCENTAGE+ value;
            case AFTER -> value = value + SQLConstant.PERCENTAGE;
        }
        return value;
    }

    /**
     * 重组装字符串类型对象的值
     *
     * @param value
     * @return
     */
    private static Object reassembleValue(Object value) {
        Class valClass = value.getClass();
        String name = valClass.getName();

        if (String.class.getName().equals(name)) {
            value = SQLConstant.SINGLE_QUOTE + value + SQLConstant.SINGLE_QUOTE;
        }
        if (List.class.isAssignableFrom(valClass)) {
            StringBuilder stringBuilder = new StringBuilder(SQLConstant.LEFT_PARENTHESIS);
            for (Object obj : (List) value) {
                Object objVal = reassembleValue(obj);
                stringBuilder.append(objVal).append(SQLConstant.COMMA);
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(SQLConstant.COMMA));
            stringBuilder.append(SQLConstant.RIGHT_PARENTHESIS);
            value = stringBuilder.toString();
        }
        return value;
    }

    public static String buildFindByPkSql(MybatisSqlBean bean, Serializable value) {
        String valStr = value.toString();
        valStr = (String) reassembleValue(valStr);
        Pk pk = bean.getPk();
        QuerySqlConditionBuilder builder = QuerySqlConditionBuilder.build().eq(pk.getName(), valStr);
        return buildSql(bean, builder);
    }

    public static void buildSingleWhereSql(SQL sql, SqlConditions sqlCondition) {
        SqlOperator operator = sqlCondition.getSqlOperator();
        String sqlOperator = sqlCondition.getSqlOperator().getValue();
        String colum = sqlCondition.getColum();
        Object sqlConditionValue = sqlCondition.getValue();
        switch (operator) {
            default -> sql.WHERE(colum + sqlOperator + sqlConditionValue);
            case IN -> {
                sqlConditionValue = reassembleValue(sqlConditionValue);
                sql.WHERE(colum + SQLConstant.SPACE + sqlOperator + SQLConstant.SPACE + sqlConditionValue);
            }
        }

    }

    public static String buildUpdateByIdSql(MybatisSqlBean mybatisSqlBean, Object entity) {
        Pk pk = mybatisSqlBean.getPk();

        Object pkValue = getEntityPKValue(entity);

        SQL sql = buildUpdateSql(mybatisSqlBean);
        buildSetSql(entity, sql, SqlDDLOption.UPDATE);
        buildSingleWhereSql(sql, SqlConditions.builder().colum(pk.getName()).sqlOperator(SqlOperator.EQ).value(pkValue).build());
        return sql.toString();
    }

    private static Object getEntityPKValue(Object entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        Field pkField = Arrays.stream(fields).filter(r -> r.isAnnotationPresent(TableId.class)).findFirst().orElse(null);
        Objects.requireNonNull(pkField, "Primary Key Field Not declared");
        if (ArrayUtils.isEmpty(fields)) {
            throw new SqlParseException(String.format("%s Entity fields do not allow empty", entity.getClass().getName()));
        }

        Object pkValue;
        try {
            pkField.setAccessible(true);
            pkValue = reassembleValue(pkField.get(entity));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return pkValue;
    }

    private static SQL buildUpdateSql(MybatisSqlBean mybatisSqlBean) {
        String tableName = mybatisSqlBean.getTableName();
        SQL sql = new SQL() {{
            UPDATE(tableName);
        }};
        return sql;
    }

    public static String buildDeleteByPkSql(MybatisSqlBean mybatisSqlBean, Serializable value) {
        SQL sql = generateBaseSql(mybatisSqlBean, true);
        Object valueObj = (Object) reassembleValue(value);
        buildSingleWhereSql(sql, SqlConditions.builder().colum(mybatisSqlBean.getPk().getName()).sqlOperator(SqlOperator.EQ).value(valueObj).build());
        return sql.toString();
    }

    public static String buildDeleteListSql(MybatisSqlBean mybatisSqlBean, List ids) {
        SQL sql = generateBaseSql(mybatisSqlBean, true);
        buildSingleWhereSql(sql, SqlConditions.builder().colum(mybatisSqlBean.getPk().getName()).sqlOperator(SqlOperator.IN).value(ids).build());
        return sql.toString();
    }


    private static void buildSetSql(Object entity, SQL sql, SqlDDLOption ddlOption) {
        Field[] fields = entity.getClass().getDeclaredFields();
        Field pkField = Arrays.stream(fields).filter(r -> r.isAnnotationPresent(TableId.class)).findFirst().orElse(null);

        boolean skipTableIdColum = SqlDDLOption.UPDATE.equals(ddlOption);
        try {
            pkField.setAccessible(true);
            for (Field field : fields) {
                // 需要操作私有变量
                field.setAccessible(true);
                String name;
                boolean hasColumAnnotationPresent = field.isAnnotationPresent(org.nott.mybatis.annotations.Colum.class);
                boolean hasIdAnnotationPresent = field.isAnnotationPresent(TableId.class);
                if (hasIdAnnotationPresent && skipTableIdColum) {
                    continue;
                }
                if (Objects.isNull(field.get(entity))) {
                    continue;
                }
                Object value = reassembleValue(field.get(entity));

                if (hasColumAnnotationPresent) {
                    name = StringUtils.isNotEmpty(field.getAnnotation(org.nott.mybatis.annotations.Colum.class).value()) ?
                            field.getAnnotation(org.nott.mybatis.annotations.Colum.class).value() : field.getAnnotation(org.nott.mybatis.annotations.Colum.class).name();
                } else if (hasIdAnnotationPresent) {
                    name = StringUtils.isNotEmpty(field.getAnnotation(TableId.class).value()) ?
                            field.getAnnotation(TableId.class).value() : field.getAnnotation(TableId.class).name();
                } else {
                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
                }

                switch (ddlOption) {
                    default -> throw new SqlParseException("Other option is not support for now");
                    case UPDATE -> sql.SET(name + SqlOperator.EQ.getValue() + value);
                    case INSERT -> sql.VALUES(name, value + SQLConstant.EMPTY_STRING);
                }
            }

        } catch (IllegalAccessException e) {
            throw new SqlParseException(e);
        }
    }

    public static String buildUpdateSql(MybatisSqlBean mybatisSqlBean, UpdateSqlConditionBuilder updateSqlConditionBuilder) {
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

    public static String buildPageCount(MybatisSqlBean bean, QuerySqlConditionBuilder builder) {
        SQL sql = generateBaseSql(bean);
        sql.SELECT("COUNT(1) as totalResult");
        buildWhereSql(sql, Objects.isNull(builder) ? null : builder.getSqlConditions());
        return sql.toString();
    }

    public static String buildPage(Integer page, Integer size, MybatisSqlBean bean, QuerySqlConditionBuilder builder) {
        SQL sql = buildMybatisSqlEntity(bean, Objects.isNull(builder) ? new QuerySqlConditionBuilder() : builder);
        sql.LIMIT(size);
        sql.OFFSET(size * (page - 1));
        return sql.toString();
    }

    public static String buildInsertSql(MybatisSqlBean bean, Object obj) {
        SQL sql = new SQL() {{
            INSERT_INTO(bean.getTableName());
        }};
        buildSetSql(obj, sql, SqlDDLOption.INSERT);
        System.out.println(sql.toString());
        return sql.toString();
    }
}
