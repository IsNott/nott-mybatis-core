package org.nott.mybatis.sql.interfaces;

import org.nott.mybatis.sql.Builder;
import org.nott.mybatis.sql.QuerySqlConditionBuilder;
import org.nott.mybatis.sql.SqlConditionBuilder;
import org.nott.mybatis.sql.enums.LikeMode;
import org.nott.mybatis.sql.enums.SqlOperator;
import org.nott.mybatis.sql.model.InLike;
import org.nott.mybatis.sql.model.InSelect;

/**
 * 定义SQL查询支持的方法接口
 */

public interface SqlQuery {

    SqlConditionBuilder eq(String colum, Object val);

    SqlConditionBuilder neq(String colum, Object val);

    SqlConditionBuilder gt(String colum, Object val);

    SqlConditionBuilder ge(String colum, Object val);

    SqlConditionBuilder lt(String colum, Object val);

    SqlConditionBuilder le(String colum, Object val);

    SqlConditionBuilder select(InSelect... selects);

    SqlConditionBuilder like(InLike... inLike);

    SqlConditionBuilder like(String colum, Object val, LikeMode likeMode);

    // TODO build or sql
    SqlConditionBuilder or(String colum, SqlOperator operator,Object val);

    SqlConditionBuilder limit(Integer value);

    SqlConditionBuilder append(String sql);

    SqlConditionBuilder orderByDesc(String... colum);

    SqlConditionBuilder orderByAsc(String... colum);

    SqlConditionBuilder groupBy(String... colum);

    SqlConditionBuilder having(String... sql);


}
