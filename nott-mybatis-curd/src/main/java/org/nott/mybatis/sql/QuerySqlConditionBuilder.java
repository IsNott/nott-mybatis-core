package org.nott.mybatis.sql;

import lombok.Data;
import org.nott.mybatis.sql.enums.LikeMode;
import org.nott.mybatis.sql.enums.OrderMode;
import org.nott.mybatis.sql.enums.SqlOperator;
import org.nott.mybatis.sql.interfaces.SqlQuery;
import org.nott.mybatis.sql.model.Colum;
import org.nott.mybatis.sql.model.InLike;
import org.nott.mybatis.sql.model.InSelect;
import org.nott.mybatis.sql.model.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class QuerySqlConditionBuilder implements SqlQuery {

    private List<SqlConditions> sqlConditions = new ArrayList<>();

    private List<Colum> sqlColum = new ArrayList<>();

    private List<Order> orderColum = new ArrayList<>();

    private List<String> append = new ArrayList<>();

    private List<String> groupByColum = new ArrayList<>();

    private List<String> havingSql = new ArrayList<>();

    private String alias;
    private Integer limit;


    public static QuerySqlConditionBuilder build() {
        return new QuerySqlConditionBuilder();
    }

    @Override
    public QuerySqlConditionBuilder eq(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.EQ);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public QuerySqlConditionBuilder neq(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.NEQ);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public QuerySqlConditionBuilder gt(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.GT);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public QuerySqlConditionBuilder ge(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.GE);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public QuerySqlConditionBuilder lt(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.LT);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public QuerySqlConditionBuilder le(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.LE);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public QuerySqlConditionBuilder select(InSelect... selects) {
        for (InSelect select : selects) {
            this.sqlColum.add(select.getColum());
        }
        return this;
    }

    @Override
    public QuerySqlConditionBuilder like(InLike... inLike) {
        for (InLike like : inLike) {
            this.like(like.getColum(), like.getValue(), like.getLikeMode());
        }
        return this;
    }

    @Override
    public QuerySqlConditionBuilder like(String colum, Object val, LikeMode likeMode) {
        SqlConditions conditions = new SqlConditions(colum, val, SqlOperator.LIKE, likeMode);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public QuerySqlConditionBuilder or(String colum, SqlOperator operator, Object val) {
        return null;
    }


    public QuerySqlConditionBuilder limit(Integer value) {
        this.limit = value;
        return this;
    }

    @Override
    public QuerySqlConditionBuilder append(String sql) {
        this.append.add(sql);
        return this;
    }

    @Override
    public QuerySqlConditionBuilder orderByDesc(String... colum) {
        for (String s : colum) {
            this.addOrderColum(s, OrderMode.DESC);
        }
        return this;
    }

    @Override
    public QuerySqlConditionBuilder orderByAsc(String... colum) {
        for (String s : colum) {
            this.addOrderColum(s, OrderMode.ASC);
        }
        return this;
    }

    @Override
    public QuerySqlConditionBuilder groupBy(String... colum) {
        this.groupByColum.addAll(Arrays.asList(colum));
        return this;
    }

    @Override
    public QuerySqlConditionBuilder having(String... sql) {
        this.havingSql.addAll(Arrays.asList(sql));
        return this;
    }

    private void addOrderColum(String colum, OrderMode orderMode) {
        this.orderColum.add(Order.builder().colum(colum).orderMode(orderMode).build());
    }
}
