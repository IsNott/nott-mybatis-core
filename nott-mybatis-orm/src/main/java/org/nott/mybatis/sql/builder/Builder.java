package org.nott.mybatis.sql.builder;

import lombok.Getter;
import lombok.Setter;
import org.nott.mybatis.exception.MethodNotSupportException;
import org.nott.mybatis.sql.enums.LikeMode;
import org.nott.mybatis.sql.enums.OrderMode;
import org.nott.mybatis.sql.enums.SqlDDLOption;
import org.nott.mybatis.sql.enums.SqlOperator;
import org.nott.mybatis.sql.interfaces.SqlQuery;
import org.nott.mybatis.sql.interfaces.SqlUpdate;
import org.nott.mybatis.sql.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public abstract class Builder implements SqlQuery, SqlConditionBuilder, SqlUpdate {

    private List<SqlConditions> sqlConditions = new ArrayList<>();

    private List<Colum> sqlColum = new ArrayList<>();

    private List<Order> orderColum = new ArrayList<>();

    private List<String> append = new ArrayList<>();

    List<UpdateCombination> updateCombinations = new ArrayList<>();

    private List<String> groupByColum = new ArrayList<>();

    private List<String> havingSql = new ArrayList<>();

    private List<SqlConditions> orConditions = new ArrayList<>();

    private String alias;

    private Integer limit;

    protected Builder methodNotSupport(SqlDDLOption option, String method) {
        throw new MethodNotSupportException(String.format(
                "%s option not allow to call %s method.", option.name(), method
        ));
    }

    @Override
    public Builder eq(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.EQ);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public Builder neq(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.NEQ);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public Builder gt(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.GT);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public Builder ge(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.GE);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public Builder lt(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.LT);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public Builder le(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.LE);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public Builder select(InSelect... selects) {
        for (InSelect select : selects) {
            this.sqlColum.add(select.getColum());
        }
        return this;
    }

    @Override
    public Builder set(String colum, Object val) {
        UpdateCombination combination = new UpdateCombination(colum, val);
        this.updateCombinations.add(combination);
        return this;
    }

    @Override
    public Builder like(InLike... inLike) {
        for (InLike like : inLike) {
            this.like(like.getColum(), like.getValue(), like.getLikeMode());
        }
        return this;
    }

    @Override
    public Builder like(String colum, Object val, LikeMode likeMode) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.LIKE, likeMode);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public Builder or(SqlConditions... sqlConditions) {
        orConditions.addAll(Arrays.asList(sqlConditions));
        return this;
    }

    public Builder isNull(String fieldName){
        this.sqlConditions.add(Where.isNull(fieldName));
        return this;
    }

    public Builder notNull(String fieldName){
        this.sqlConditions.add(Where.notNull(fieldName));
        return this;
    }


    public Builder limit(Integer value) {
        this.limit = value;
        return this;
    }

    @Override
    public Builder append(String sql) {
        this.append.add(sql);
        return this;
    }

    @Override
    public Builder orderByDesc(String... colum) {
        for (String s : colum) {
            this.addOrderColum(s, OrderMode.DESC);
        }
        return this;
    }

    @Override
    public Builder orderByAsc(String... colum) {
        for (String s : colum) {
            this.addOrderColum(s, OrderMode.ASC);
        }
        return this;
    }

    @Override
    public Builder groupBy(String... colum) {
        this.groupByColum.addAll(Arrays.asList(colum));
        return this;
    }

    @Override
    public Builder having(String... sql) {
        this.havingSql.addAll(Arrays.asList(sql));
        return this;
    }

    private void addOrderColum(String colum, OrderMode orderMode) {
        this.orderColum.add(Order.builder().colum(colum).orderMode(orderMode).build());
    }
}
