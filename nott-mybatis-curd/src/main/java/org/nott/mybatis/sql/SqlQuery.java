package org.nott.mybatis.sql;

public interface SqlQuery {

    public SimpleSqlConditionBuilder eq(String colum, Object val);
    public SimpleSqlConditionBuilder neq(String colum, Object val);

    public SimpleSqlConditionBuilder selectColum(String... colum);

    public SimpleSqlConditionBuilder limit(Integer value);
}
