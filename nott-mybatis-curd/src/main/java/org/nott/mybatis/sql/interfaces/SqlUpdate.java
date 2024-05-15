package org.nott.mybatis.sql.interfaces;

import org.nott.mybatis.sql.UpdateSqlConditionBuilder;

public interface SqlUpdate {

    UpdateSqlConditionBuilder set(String colum, Object val);
}
