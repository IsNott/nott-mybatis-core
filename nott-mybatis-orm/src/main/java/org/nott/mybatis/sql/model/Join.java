package org.nott.mybatis.sql.model;

import lombok.Getter;
import lombok.Setter;
import org.nott.mybatis.sql.builder.SqlConditions;
import org.nott.mybatis.sql.enums.JoinTableMode;
import org.nott.mybatis.sql.enums.SqlOperator;
import org.nott.mybatis.sql.interfaces.SFunction;
import org.nott.mybatis.support.aop.utils.BaseUtils;

/**
 * @author Nott
 * @date 2024-6-5
 */
@Setter
@Getter
public class Join {

    private SqlConditions joinCondition;

    private JoinTableMode joinMode;

    private String name;

    private String alias;

    public static Join on(String field,String joinField,SqlOperator operator){
        Join join = new Join();
        SqlConditions sqlConditions = new SqlConditions(field, joinField, operator);
        join.setJoinCondition(sqlConditions);
        return join;
    }

    public static Join on(String field,String joinField){
        Join join = new Join();
        SqlConditions sqlConditions = new SqlConditions(field, joinField, SqlOperator.EQ);
        join.setJoinCondition(sqlConditions);
        return join;
    }

    public static <T, R, T1, R1> Join on(String table0, SFunction<T, R> function0, String table, SFunction<T1, R1> function) {
        return on(table0, function0, table, function, SqlOperator.EQ);
    }

    public static <T, R, T1, R1> Join on(String table0, SFunction<T, R> function0, String table, SFunction<T1, R1> function, SqlOperator sqlOperator) {
        Join join = new Join();
        SqlConditions sqlConditions = new SqlConditions(BaseUtils.getColumName(table0, function0), BaseUtils.getColumName(table, function)
                , sqlOperator);
        join.setJoinCondition(sqlConditions);
        return join;
    }
}
