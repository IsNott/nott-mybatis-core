package org.nott.mybatis.sql;

import lombok.Data;
import org.nott.mybatis.sql.model.Colum;

import java.util.ArrayList;
import java.util.List;

@Data
public class SimpleSqlConditionBuilder implements SqlQuery{

    List<SqlConditions> sqlConditions = new ArrayList<>();

    List<Colum> sqlColum;

    Integer limit;


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
    public SimpleSqlConditionBuilder selectColum(String... colum) {

        return null;
    }

    public SimpleSqlConditionBuilder limit(Integer value){
        this.limit = value;
        return this;
    }

}
