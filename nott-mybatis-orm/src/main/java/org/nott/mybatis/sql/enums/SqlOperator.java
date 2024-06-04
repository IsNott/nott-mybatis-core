package org.nott.mybatis.sql.enums;


import lombok.Getter;

@Getter
public enum SqlOperator {
    EQ(" = "),
    NEQ(" <> "),
    GE(" >= "),
    GT(" > "),
    LE(" <= "),
    LT(" > "),
    IN(" in "),
    LIKE(" LIKE "),

    IS_NULL(" IS NULL"),

    IS_NOT_NULL(" IS NOT NULL");

    private String value;

    SqlOperator(String value) {
        this.value = value;
    }


}
