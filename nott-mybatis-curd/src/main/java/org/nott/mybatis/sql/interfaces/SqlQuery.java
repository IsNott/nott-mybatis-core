package org.nott.mybatis.sql.interfaces;

import org.nott.mybatis.sql.SimpleSqlConditionBuilder;
import org.nott.mybatis.sql.model.Colum;
import org.nott.mybatis.sql.model.InSelect;

import java.util.List;

/**
 * 定义SQL查询支持的方法接口
 */

public interface SqlQuery {

    SimpleSqlConditionBuilder eq(String colum, Object val);

    SimpleSqlConditionBuilder neq(String colum, Object val);

    SimpleSqlConditionBuilder gt(String colum, Object val);

    SimpleSqlConditionBuilder ge(String colum, Object val);

    SimpleSqlConditionBuilder lt(String colum, Object val);

    SimpleSqlConditionBuilder le(String colum, Object val);

    SimpleSqlConditionBuilder select(InSelect... selects);


    SimpleSqlConditionBuilder limit(Integer value);

    SimpleSqlConditionBuilder append(String sql);


}
