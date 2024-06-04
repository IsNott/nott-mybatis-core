package org.nott.mybatis.sql.builder;

import com.google.common.base.CaseFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.nott.mybatis.annotations.CustTableName;
import org.nott.mybatis.sql.enums.JoinTableMode;
import org.nott.mybatis.sql.model.JoinTable;
import org.nott.mybatis.sql.model.Table;

import java.util.List;
import java.util.Objects;

/**
 * TODO multiple sql condition
 * @author Nott
 * @date 2024-5-17
 */

@Setter
@Getter
public class ComplexityConditionBuilder extends QuerySqlConditionBuilder {

    private Class<?> aClass;

    private Table rootTable;

    private List<JoinTable> joinTables;


    public ComplexityConditionBuilder() {
    }

    public ComplexityConditionBuilder(Table rootTable) {
        this.rootTable = rootTable;
    }

    public static ComplexityConditionBuilder build(Class<?> clazz) {
        return ComplexityConditionBuilder.build(clazz, null);
    }

    public static ComplexityConditionBuilder build(Class<?> clazz, String alias) {
        ComplexityConditionBuilder builder = new ComplexityConditionBuilder();
        Table table = new Table(findTableNameByClass(clazz),alias);
        builder.setRootTable(table);
        return builder;
    }

    public ComplexityConditionBuilder leftJoin(Class<?> clazz, String alias, SqlConditions joinCondition) {
       return this.joinBase(clazz,alias,joinCondition,JoinTableMode.LEFT);
    }

    public ComplexityConditionBuilder rightJoin(Class<?> clazz, String alias, SqlConditions joinCondition) {
        return this.joinBase(clazz,alias,joinCondition,JoinTableMode.RIGHT);
    }

    public ComplexityConditionBuilder innerJoin(Class<?> clazz, String alias, SqlConditions joinCondition) {
        return this.joinBase(clazz,alias,joinCondition,JoinTableMode.INNER_JOIN);
    }

    private ComplexityConditionBuilder joinBase(Class<?> clazz, String alias, SqlConditions joinCondition,JoinTableMode mode){
        JoinTable table = new JoinTable(findTableNameByClass(clazz),alias,joinCondition, mode);
        this.joinTables.add(table);
        return this;
    }

    private static String findTableNameByClass(Class<?> clazz) {
        String tableName;
        if (clazz.isAnnotationPresent(CustTableName.class)) {
            tableName = (StringUtils.isNotEmpty(clazz.getDeclaredAnnotation(CustTableName.class).name()) ?
                    clazz.getDeclaredAnnotation(CustTableName.class).name() : clazz.getDeclaredAnnotation(CustTableName.class).value());
        } else {
            tableName = (CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName()));
        }
        Objects.requireNonNull(tableName);
        return tableName;
    }





}
