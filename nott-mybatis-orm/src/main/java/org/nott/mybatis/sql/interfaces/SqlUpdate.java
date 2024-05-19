package org.nott.mybatis.sql.interfaces;

import org.nott.mybatis.sql.builder.SqlConditionBuilder;

public interface SqlUpdate {

    SqlConditionBuilder set(String colum, Object val);

}
