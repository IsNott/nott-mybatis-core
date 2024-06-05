package org.nott.mybatis.sql.model;

import lombok.Getter;
import lombok.Setter;
import org.nott.mybatis.sql.builder.SqlConditions;
import org.nott.mybatis.sql.enums.JoinTableMode;
import org.nott.mybatis.sql.enums.SqlOperator;

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

}
