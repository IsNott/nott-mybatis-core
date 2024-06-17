package org.nott.mybatis.sql.builder;

import com.google.common.base.CaseFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.nott.mybatis.annotations.CustTableName;
import org.nott.mybatis.exception.SqlBuilderException;
import org.nott.mybatis.sql.MybatisSqlFactory;
import org.nott.mybatis.sql.enums.JoinTableMode;
import org.nott.mybatis.sql.interfaces.SqlQuery;
import org.nott.mybatis.sql.model.Colum;
import org.nott.mybatis.sql.model.Join;
import org.nott.mybatis.sql.model.Table;

import java.util.*;

/**
 * multiple sql condition builder
 * @author Nott
 * @date 2024-5-17
 */

@Setter
@Getter
public class ComplexityWrapper extends QuerySqlConditionBuilder implements SqlQuery {

    private Class<?> aClass;

    private Table rootTable;

    private List<Join> joinTables = new ArrayList<>();

    private List<SqlConditions> sqlConditions = new ArrayList<>();

    private HashMap<String,String> orderByMap = new HashMap<>(16);

    private List<Colum> groupByColums = new ArrayList<>();

    private List<Colum> colums = new ArrayList<>();

    private Integer rowBound;

    public ComplexityWrapper() {
    }

    public ComplexityWrapper(Table rootTable) {
        this.rootTable = rootTable;
    }

    public static ComplexityWrapper build(Class<?> clazz) {
        return ComplexityWrapper.build(clazz, null);
    }

    public ComplexityWrapper alias(String name){
        if(this.rootTable == null){
            throw new SqlBuilderException("rootTable为空");
        }
        this.rootTable.setAlias(name);
        return this;
    }

    public static ComplexityWrapper build(Class<?> clazz, String alias) {
        ComplexityWrapper builder = new ComplexityWrapper();
        Table table = new Table(findTableNameByClass(clazz),alias);
        builder.setRootTable(table);
        return builder;
    }

    public ComplexityWrapper leftJoin(Class<?> clazz, String alias, Join join) {
       return this.joinBase(clazz,alias,JoinTableMode.LEFT,join);
    }


    public ComplexityWrapper rightJoin(Class<?> clazz, String alias, Join join) {
        return this.joinBase(clazz,alias,JoinTableMode.RIGHT,join);
    }

    public ComplexityWrapper innerJoin(Class<?> clazz, String alias, Join join) {
        return this.joinBase(clazz,alias,JoinTableMode.INNER_JOIN,join);
    }

    public ComplexityWrapper condition(SqlConditions... sqlConditions){
        this.sqlConditions.addAll(Arrays.asList(sqlConditions));
        return this;
    }

    public ComplexityWrapper orderByDesc(String field){
        this.orderByMap.put(field," DESC ");
        return this;
    }

    public ComplexityWrapper orderByAsc(String field){
        this.orderByMap.put(field," ASC ");
        return this;
    }

    public <T> List<T> beanType(Class<T> clazz){
        return MybatisSqlFactory.doExecute(clazz,this);
    }

    public ComplexityWrapper groupBy(String... columNames){
        for (String columName : columNames) {
            Colum colum = new Colum();
            colum.setFieldName(columName);
            this.groupByColums.add(colum);
        }
        return this;
    }

    public ComplexityWrapper limit(Integer rowBound){
        this.rowBound = rowBound;
        return this;
    }

    public ComplexityWrapper colums(Colum... colum){
        this.colums.addAll(Arrays.asList(colum));
        return this;
    }


    private ComplexityWrapper joinBase(Class<?> clazz, String alias, JoinTableMode mode, Join join) {
        join.setName(findTableNameByClass(clazz));
        join.setAlias(alias);
        join.setJoinMode(mode);
        joinTables.add(join);
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
