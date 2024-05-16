package org.nott.mybatis.sql.interfaces;

import org.nott.mybatis.sql.QuerySqlConditionBuilder;
import org.nott.mybatis.sql.enums.LikeMode;
import org.nott.mybatis.sql.enums.SqlOperator;
import org.nott.mybatis.sql.model.InLike;
import org.nott.mybatis.sql.model.InSelect;

/**
 * 定义SQL查询支持的方法接口
 */

public interface SqlQuery {

    QuerySqlConditionBuilder eq(String colum, Object val);

    QuerySqlConditionBuilder neq(String colum, Object val);

    QuerySqlConditionBuilder gt(String colum, Object val);

    QuerySqlConditionBuilder ge(String colum, Object val);

    QuerySqlConditionBuilder lt(String colum, Object val);

    QuerySqlConditionBuilder le(String colum, Object val);

    QuerySqlConditionBuilder select(InSelect... selects);

    QuerySqlConditionBuilder like(InLike... inLike);

    QuerySqlConditionBuilder like(String colum, Object val, LikeMode likeMode);

    // TODO build or sql
    QuerySqlConditionBuilder or(String colum, SqlOperator operator,Object val);

    QuerySqlConditionBuilder limit(Integer value);

    QuerySqlConditionBuilder append(String sql);

    QuerySqlConditionBuilder orderByDesc(String... colum);

    QuerySqlConditionBuilder orderByAsc(String... colum);

    QuerySqlConditionBuilder groupBy(String... colum);

    QuerySqlConditionBuilder having(String... sql);


}
