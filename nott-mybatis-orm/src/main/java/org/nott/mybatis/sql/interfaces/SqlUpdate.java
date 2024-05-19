package org.nott.mybatis.sql.interfaces;

import org.nott.mybatis.sql.SqlConditionBuilder;
import org.nott.mybatis.sql.UpdateSqlConditionBuilder;

public interface SqlUpdate {

    SqlConditionBuilder set(String colum, Object val);

}
