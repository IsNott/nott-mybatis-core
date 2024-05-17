package org.nott.mybatis.dao;

import org.nott.mybatis.mapper.CommonMapper;
import org.nott.mybatis.model.Page;
import org.nott.mybatis.sql.QuerySqlConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Nott
 * @date 2024-5-17
 */
public class CommonDAO<T> {

    @Autowired
    CommonMapper<T> commonMapper;

    public Page<T> page(Page<T> page) {
        return this.page(page, null);
    }

    public Page<T> page(Page<T> page, QuerySqlConditionBuilder querySqlConditionBuilder) {
        long count = commonMapper.count();
        page.setTotalResult(count);
        if (count > 0) {
            List<T> pageList = commonMapper.page(page.getCurrentPage(), page.getSize(), querySqlConditionBuilder);
            page.setRecords(pageList);
        }
        return page;
    }


}
