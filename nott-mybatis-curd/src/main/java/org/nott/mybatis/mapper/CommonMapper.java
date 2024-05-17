package org.nott.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.nott.mybatis.model.Page;
import org.nott.mybatis.provider.BaseDeleteProvider;
import org.nott.mybatis.provider.BaseInsertProvider;
import org.nott.mybatis.provider.BaseSelectProvider;
import org.nott.mybatis.provider.BaseUpdateProvider;
import org.nott.mybatis.sql.QuerySqlConditionBuilder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 通用dao层
 *
 * @param <T>
 */

@Component
public interface CommonMapper<T> {

    @SelectProvider(type = BaseSelectProvider.class, method = "selectById")
    public T selectById(@Param("id") Serializable id);

    @SelectProvider(type = BaseSelectProvider.class, method = "selectOne")
    public T selectOne();

    @SelectProvider(type = BaseSelectProvider.class, method = "selectList")
    public List<T> selectList();

    @SelectProvider(type = BaseSelectProvider.class, method = "pageCount")
    public Long count();

    @SelectProvider(type = BaseSelectProvider.class, method = "pageCountByCondition")
    public Long countByCondition(QuerySqlConditionBuilder querySqlConditionBuilder);

    @SelectProvider(type = BaseSelectProvider.class, method = "page")
    public List<T> page(int page,int size, QuerySqlConditionBuilder querySqlConditionBuilder);

    @SelectProvider(type = BaseSelectProvider.class, method = "selectOneByCondition")
    public T selectOneByCondition(QuerySqlConditionBuilder querySqlConditionBuilder);

    @UpdateProvider(type = BaseUpdateProvider.class, method = "updateById")
    public int updateById(@Param("entity") T t);

    @InsertProvider(type = BaseInsertProvider.class, method = "insert")
    public int insert(@Param("entity") T t);

    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteById")
    public int deleteById(@Param("id") Serializable id);

    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteByIds")
    public int deleteByIds(@Param("ids") List ids);
}
