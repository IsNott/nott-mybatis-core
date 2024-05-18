package org.nott.mybatis.service.impl;

import org.nott.mybatis.service.CommonService;
import org.nott.mybatis.mapper.CommonMapper;
import org.nott.mybatis.model.Page;
import org.nott.mybatis.sql.QuerySqlConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * CommonService 实现类
 * @author Nott
 * @date 2024-5-17
 */

@Component
@Scope("prototype")
public class ICommonService<T> implements CommonService<T> {

    @Autowired
    CommonMapper<T> commonMapper;

    @Override
    public Page<T> page(Page<T> page) {
        return this.page(page, null);
    }

    public Page<T> page(Page<T> page, QuerySqlConditionBuilder querySqlConditionBuilder) {
        long count = querySqlConditionBuilder != null ? commonMapper.countByCondition(querySqlConditionBuilder) : commonMapper.count();
        page.setTotalResult(count);
        if (count > 0) {
            List<T> pageList = commonMapper.page(page.getCurrentPage(), page.getSize(), querySqlConditionBuilder);
            page.setRecords(pageList);
        }
        return page;
    }

    @Override
    public T getById(Serializable id) {
        return null;
    }

    @Override
    public List<T> getList() {
        return commonMapper.selectList();
    }

    @Override
    public List<T> getList(QuerySqlConditionBuilder querySqlConditionBuilder) {
        return commonMapper.selectListByCondition(querySqlConditionBuilder);
    }

    @Override
    public T getOne() {
        return commonMapper.selectOne();
    }

    @Override
    public T getOne(QuerySqlConditionBuilder querySqlConditionBuilder) {
        return commonMapper.selectOneByCondition(querySqlConditionBuilder);
    }

    @Override
    public int save(T t) {
        return commonMapper.insert(t);
    }

    @Override
    public int removeById(Serializable id) {
        return commonMapper.deleteById(id);
    }

    @Override
    public int removeByIds(List ids) {
        return commonMapper.deleteByIds(ids);
    }

    @Override
    public int updateById(T t) {
        return commonMapper.updateById(t);
    }
}
