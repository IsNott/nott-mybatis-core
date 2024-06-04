package org.nott.mybatis.sql.model;

import lombok.Data;
import org.nott.mybatis.sql.builder.SqlConditions;
import org.nott.mybatis.sql.enums.JoinTableMode;

/**
 * @author Nott
 * @date 2024-6-4
 */

@Data
public class JoinTable extends Table{

    private SqlConditions joinCondition;

    private JoinTableMode joinMode;

    public JoinTable(String name, String alias, SqlConditions joinCondition, JoinTableMode joinMode) {
        super(name, alias);
        this.joinCondition = joinCondition;
        this.joinMode = joinMode;
    }
}
