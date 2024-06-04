package org.nott.mybatis.sql.builder;

import lombok.Builder;
import lombok.Data;
import org.nott.mybatis.exception.SqlBuilderException;
import org.nott.mybatis.sql.enums.LikeMode;
import org.nott.mybatis.sql.enums.SqlOperator;

@Data
public class SqlConditions {

    private String colum;

    private Object value;

    private SqlOperator sqlOperator;

    private LikeMode likeMode;

    public SqlConditions() {
    }

    public SqlConditions(String colum, SqlOperator sqlOperator) {
        if(!SqlOperator.IS_NULL.equals(sqlOperator) && !SqlOperator.IS_NOT_NULL.equals(sqlOperator)){
            throw new SqlBuilderException("Other SqlOperator Without Value Not Allow Use IsNull/NotNull Sql Condition");
        }
        this.colum = colum;
        this.sqlOperator = sqlOperator;
    }

    @Builder(builderMethodName = "basicBuilder")
    public SqlConditions(String colum, Object value, SqlOperator sqlOperator) {
        this.colum = colum;
        this.value = value;
        this.sqlOperator = sqlOperator;
    }

    @Builder(builderMethodName = "advancedBuilder")
    public SqlConditions(String colum, Object value, SqlOperator sqlOperator, LikeMode likeMode) {
        if(!SqlOperator.LIKE.equals(sqlOperator)){
            throw new SqlBuilderException("Other SqlOperator Do Not Allow Use LikeMode");
        }
        this.colum = colum;
        this.value = value;
        this.sqlOperator = sqlOperator;
        this.likeMode = likeMode;
    }
}
