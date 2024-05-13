package org.nott.mybatis.sql;

import lombok.Data;
import org.nott.mybatis.sql.model.Colum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class SimpleSqlConditionBuilder implements SqlQuery{

    List<SqlConditions> sqlConditions = new ArrayList<>();

    List<Colum> sqlColum = new ArrayList<>();

    Integer limit;

    String append;


    public static SimpleSqlConditionBuilder create(Class tClass){
        return new SimpleSqlConditionBuilder();
    }

    @Override
    public SimpleSqlConditionBuilder eq(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum,val,SqlOperator.EQ);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public SimpleSqlConditionBuilder neq(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum,val,SqlOperator.NEQ);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public SimpleSqlConditionBuilder gt(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum,val,SqlOperator.GT);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public SimpleSqlConditionBuilder ge(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum,val,SqlOperator.GE);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public SimpleSqlConditionBuilder lt(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum,val,SqlOperator.LT);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public SimpleSqlConditionBuilder le(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum,val,SqlOperator.LE);
        this.sqlConditions.add(conditions);
        return this;
    }

    @Override
    public SimpleSqlConditionBuilder select(Colum... colum) {
        this.sqlColum.addAll(Arrays.asList(colum));
        return this;
    }


    public SimpleSqlConditionBuilder limit(Integer value){
        this.limit = value;
        return this;
    }

    @Override
    public SimpleSqlConditionBuilder append(String sql) {
        return null;
    }

}
