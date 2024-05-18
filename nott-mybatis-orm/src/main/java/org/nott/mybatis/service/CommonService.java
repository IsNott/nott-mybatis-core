package org.nott.mybatis.service;

import org.apache.ibatis.annotations.Param;
import org.nott.mybatis.model.Page;
import org.nott.mybatis.sql.QuerySqlConditionBuilder;

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

    List<T> getList(QuerySqlConditionBuilder querySqlConditionBuilder);

    T getOne();

    T getOne(QuerySqlConditionBuilder querySqlConditionBuilder);

    int save(T t);

    int removeById(Serializable id);

    int removeByIds(@Param("ids") List ids);

    int updateById(@Param("entity") T t);


}
