package org.nott.mybatis.service;

import org.apache.ibatis.annotations.Param;
import org.nott.mybatis.model.Page;
import org.nott.mybatis.sql.builder.QuerySqlConditionBuilder;
import org.nott.mybatis.sql.builder.SqlConditionBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 通用Service层
 * @author Nott
 * @date 2024-5-17
 */
public interface CommonService<T> {

    Page<T> page(Page<T> page);

    Page<T> page(Page<T> page, QuerySqlConditionBuilder querySqlConditionBuilder);

    T getById(Serializable id);

    List<T> getList();

    List<T> getList(SqlConditionBuilder querySqlConditionBuilder);

    T getOne();

    T getOne(SqlConditionBuilder querySqlConditionBuilder);

    T save(T t);

    boolean removeById(Serializable id);

    boolean removeByIds(@Param("ids") List ids);

    int delete(SqlConditionBuilder deleteSqlConditionBuilder);

    T updateById(@Param("entity") T t);

    boolean update(SqlConditionBuilder updateSqlConditionBuilder);


}
