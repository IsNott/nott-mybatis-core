package org.nott.mybatis.sql.enums;

/**
 * @author Nott
 * @date 2024-5-15
 */
public enum OrderMode {

    ASC(" ASC"),

    DESC(" DESC");

    String mode;

    OrderMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
