package org.nott.mybatis.sql;

import lombok.Data;
import org.nott.mybatis.sql.enums.SqlOperator;
import org.nott.mybatis.sql.interfaces.SqlQuery;
import org.nott.mybatis.sql.interfaces.SqlUpdate;
import org.nott.mybatis.sql.model.Colum;
import org.nott.mybatis.sql.model.InSelect;
import org.nott.mybatis.sql.model.UpdateCombination;

import java.util.ArrayList;
import java.util.List;

@Data
public class SimpleSqlConditionBuilder implements SqlQuery, SqlUpdate {

    List<SqlConditions> sqlConditions = new ArrayList<>();

    List<Colum> sqlColum = new ArrayList<>();

    List<UpdateCombination> updateCombinations = new ArrayList<>();

    Integer limit;

    String append;


    public static SimpleSqlConditionBuilder build(){
        return new SimpleSqlConditionBuilder();
    }

    @Override
    public SimpleSqlConditionBuilder eq(String colum, Object val) {
        SqlConditions conditions = new SqlConditions(colum,val, SqlOperator.EQ);
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
    public SimpleSqlConditionBuilder select(InSelect... selects) {
        for (InSelect select : selects) {
            this.sqlColum.add(select.getColum());
        }
        return this;
    }


    public SimpleSqlConditionBuilder limit(Integer value){
        this.limit = value;
        return this;
    }

    @Override
    public SimpleSqlConditionBuilder append(String sql) {
        this.append = sql;
        return this;
    }

    @Override
    public SimpleSqlConditionBuilder set(String colum, Object val) {
        UpdateCombination combination = new UpdateCombination(colum, val);
        this.updateCombinations.add(combination);
        return this;
    }
}
