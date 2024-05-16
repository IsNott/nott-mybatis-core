package org.nott.mybatis.sql.enums;


import lombok.Getter;

@Getter
public enum SqlOperator {
    EQ("="),
    NEQ("<>"),
    GE(">="),
    GT(">"),
    LE("<="),
    LT(">"),
    IN("in")

    ;

    private String value;

    SqlOperator(String value) {
        this.value = value;
    }
}
