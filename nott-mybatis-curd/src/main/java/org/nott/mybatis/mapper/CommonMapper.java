package org.nott.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.nott.mybatis.provider.BaseSelectProvider;
import org.nott.mybatis.sql.SimpleSqlConditionBuilder;

import java.util.List;

public interface CommonMapper<T> {

    @SelectProvider(type = BaseSelectProvider.class,method = "selectOne")
    public T selectOne(T t);

    @SelectProvider(type = BaseSelectProvider.class,method = "selectList")
    public List<T> selectList(T t);

    @SelectProvider(type = BaseSelectProvider.class, method = "selectOneByCondition")
    public T selectOneByCondition(T t, @Param("simpleSqlConditionBuilder") SimpleSqlConditionBuilder simpleSqlBuilder);


}
