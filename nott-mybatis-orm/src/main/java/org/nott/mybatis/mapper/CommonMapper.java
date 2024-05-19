package org.nott.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.nott.mybatis.provider.BaseDeleteProvider;
import org.nott.mybatis.provider.BaseInsertProvider;
import org.nott.mybatis.provider.BaseSelectProvider;
import org.nott.mybatis.provider.BaseUpdateProvider;
import org.nott.mybatis.sql.QuerySqlConditionBuilder;
import org.nott.mybatis.sql.SqlConditionBuilder;
import org.nott.mybatis.sql.UpdateSqlConditionBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 通用dao层
 *
 * @param <T>
 */

@Component
@Scope("prototype")
public interface CommonMapper<T> {

    @SelectProvider(type = BaseSelectProvider.class, method = "selectById")
    T selectById(@Param("id") Serializable id);

    @SelectProvider(type = BaseSelectProvider.class, method = "selectOne")
    T selectOne();

    @SelectProvider(type = BaseSelectProvider.class, method = "selectList")
    List<T> selectList();

    @SelectProvider(type = BaseSelectProvider.class, method = "selectListByCondition")
    List<T> selectListByCondition(SqlConditionBuilder querySqlConditionBuilder);

    @SelectProvider(type = BaseSelectProvider.class, method = "pageCount")
    Long count();

    @SelectProvider(type = BaseSelectProvider.class, method = "pageCountByCondition")
    Long countByCondition(SqlConditionBuilder querySqlConditionBuilder);

    @SelectProvider(type = BaseSelectProvider.class, method = "page")
    List<T> page(int page,int size, SqlConditionBuilder querySqlConditionBuilder);

    @SelectProvider(type = BaseSelectProvider.class, method = "selectOneByCondition")
    T selectOneByCondition(SqlConditionBuilder querySqlConditionBuilder);

    @UpdateProvider(type = BaseUpdateProvider.class, method = "updateById")
    int updateById(@Param("entity") T t);

    @UpdateProvider(type = BaseUpdateProvider.class, method = "updateByCondition")
    int updateByCondition(SqlConditionBuilder updateSqlConditionBuilder);

    @InsertProvider(type = BaseInsertProvider.class, method = "insert")
    int insert(@Param("entity") T t);

    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteById")
    int deleteById(@Param("id") Serializable id);

    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("ids") List ids);
}
