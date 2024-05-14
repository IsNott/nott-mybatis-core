package org.nott.mybatis.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nott.mybatis.sql.enums.SqlOperator;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SqlConditions {

    private String colum;

    private Object value;

    private SqlOperator sqlOperator;


}
