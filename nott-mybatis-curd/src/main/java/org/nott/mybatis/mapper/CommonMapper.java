package org.nott.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.nott.mybatis.provider.BaseSelectProvider;
import org.nott.mybatis.sql.SimpleSqlConditionBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 通用dao层
 * @param <T>
 */
public interface CommonMapper<T> {

    @SelectProvider(type = BaseSelectProvider.class,method = "selectById")
    public T selectById(@Param("id")Serializable id);

    @SelectProvider(type = BaseSelectProvider.class,method = "selectOne")
    public T selectOne();

    @SelectProvider(type = BaseSelectProvider.class,method = "selectList")
    public List<T> selectList();

    @SelectProvider(type = BaseSelectProvider.class, method = "selectOneByCondition")
    public T selectOneByCondition(@Param("simpleSqlConditionBuilder") SimpleSqlConditionBuilder simpleSqlBuilder);


}
