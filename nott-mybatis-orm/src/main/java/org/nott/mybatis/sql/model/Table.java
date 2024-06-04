package org.nott.mybatis.sql.model;

import lombok.Data;

/**
 * @author Nott
 * @date 2024-6-4
 */

@Data
public class Table {

    private String name;

    private String alias;

    public Table() {
    }

    public Table(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }
}
