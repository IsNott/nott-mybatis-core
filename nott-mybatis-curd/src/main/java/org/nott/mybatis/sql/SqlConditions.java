package org.nott.mybatis.sql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nott.mybatis.sql.enums.SqlOperator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SqlConditions {

    private String colum;

    private Object value;

    private SqlOperator sqlOperator;


}
