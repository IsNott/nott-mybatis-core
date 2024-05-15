package org.nott.mybatis.sql;

import lombok.Data;
import org.nott.mybatis.sql.enums.SqlOperator;
import org.nott.mybatis.sql.interfaces.SqlQuery;
import org.nott.mybatis.sql.model.Colum;
import org.nott.mybatis.sql.model.InSelect;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuerySqlConditionBuilder implements SqlQuery {

    List<SqlConditions> sqlConditions = new ArrayList<>();

    List<Colum> sqlColum = new ArrayList<>();

    Integer limit;

    String append;


    public static QuerySqlConditionBuilder build(){
        return new QuerySqlConditionBuilder();
    }

    @Override
    public QuerySqlConditionBuilder eq(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum,val, SqlOperator.EQ);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public QuerySqlConditionBuilder neq(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum,val,SqlOperator.NEQ);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public QuerySqlConditionBuilder gt(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum,val,SqlOperator.GT);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public QuerySqlConditionBuilder ge(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum,val,SqlOperator.GE);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public QuerySqlConditionBuilder lt(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum,val,SqlOperator.LT);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public QuerySqlConditionBuilder le(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum,val,SqlOperator.LE);
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


    public QuerySqlConditionBuilder limit(Integer value){
        this.limit = value;
        return this;
    }

    @Override
    public QuerySqlConditionBuilder append(String sql) {
        this.append = sql;
        return this;
    }
}