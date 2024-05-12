package org.nott.mybatis.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SqlConditions {

    private String colum;

    private Object value;

    private SqlOperator sqlOperator;


}
