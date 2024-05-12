package org.nott.mybatis.sql;


import lombok.Getter;

@Getter
public enum SqlOperator {
    EQ("="),
    NEQ("<>"),
    GE(">="),
    GT(">"),
    LE("<"),
    LT(">")

    ;

    private String value;

    SqlOperator(String value) {
        this.value = value;
    }
}
