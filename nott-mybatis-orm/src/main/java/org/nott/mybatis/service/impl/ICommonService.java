package org.nott.mybatis.service.impl;

import org.nott.mybatis.exception.OrmOperateException;
import org.nott.mybatis.service.CommonService;
import org.nott.mybatis.mapper.CommonMapper;
import org.nott.mybatis.model.Page;
import org.nott.mybatis.sql.QuerySqlConditionBuilder;
import org.nott.mybatis.sql.UpdateSqlConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * CommonService 实现类
 * @author Nott
 * @date 2024-5-17
 */

@Component
@Scope("prototype")
@Transactional(rollbackFor = OrmOperateException.class)
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
    public T save(T t) {
        int insert = commonMapper.insert(t);
        if(insert > 0){
            return t;
        }
        throw new OrmOperateException("Save method failed.");
    }

    @Override
    public boolean removeById(Serializable id) {
        return commonMapper.deleteById(id) > 0;
    }

    @Override
    public boolean removeByIds(List ids) {
        if (commonMapper.deleteByIds(ids) == ids.size()) {
            return true;
        }
        throw new OrmOperateException("RemoveByIds method failed.");
    }

    @Override
    public T updateById(T t) {
        int updated = commonMapper.updateById(t);
        if(updated > 0){
            return t;
        }
        throw new OrmOperateException("UpdateById method failed.");
    }

    @Override
    public boolean update(UpdateSqlConditionBuilder updateSqlConditionBuilder) {
        if (commonMapper.updateByCondition(updateSqlConditionBuilder) > 0) {
            return true;
        }
        throw new OrmOperateException("Update method failed.");
    }
}
