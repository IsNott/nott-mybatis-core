package org.nott.mybatis.sql.model;

import org.nott.mybatis.sql.builder.SqlConditions;
import org.nott.mybatis.sql.enums.LikeMode;
import org.nott.mybatis.sql.enums.SqlOperator;

import java.util.List;

/**
 * 静态Where SqlConditions构造封装
 * @author Nott
 * @date 2024-6-4
 */


public class Where {

    public static SqlConditions eq(String field, Object value) {
        return new SqlConditions(field, value, SqlOperator.EQ);
    }

    public static SqlConditions le(String field, Object value) {
        return new SqlConditions(field, value, SqlOperator.LE);
    }

    public static SqlConditions neq(String field, Object value) {
        return new SqlConditions(field, value, SqlOperator.NEQ);
    }

    public static SqlConditions ge(String field, Object value) {
        return new SqlConditions(field, value, SqlOperator.GE);
    }

    public static SqlConditions in(String field, List obj) {
        return new SqlConditions(field, obj, SqlOperator.IN);
    }

    public static SqlConditions lt(String field, Object value) {
        return new SqlConditions(field, value, SqlOperator.LT);
    }

    public static SqlConditions like(String field, Object value, LikeMode mode) {
        return new SqlConditions(field, value, SqlOperator.LIKE, mode);
    }

    public static SqlConditions isNull(String field) {
        return new SqlConditions(field,SqlOperator.IS_NULL);
    }

    public static SqlConditions NotNull(String field) {
        return new SqlConditions(field, SqlOperator.IS_NOT_NULL);
    }



}
