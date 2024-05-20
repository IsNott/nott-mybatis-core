package org.nott.mybatis.sql.builder;

import lombok.Data;

@Data
public class QuerySqlConditionBuilder extends Builder {

    public static QuerySqlConditionBuilder build() {
        return new QuerySqlConditionBuilder();
    }


}
