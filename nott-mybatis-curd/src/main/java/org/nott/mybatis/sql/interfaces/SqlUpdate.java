package org.nott.mybatis.sql.interfaces;

import org.nott.mybatis.sql.SimpleSqlConditionBuilder;

public interface SqlUpdate {

    SimpleSqlConditionBuilder set(String colum, Object val);
}
