package org.nott.datasource.support;

import org.nott.datasource.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author Nott
 * @date 2024-5-20
 */
@Component
public class DataSourceSupport {

    @Autowired
    private DynamicDataSource dynamicDataSource;

    public void addDefineDynamicDataSource(DataSource dataSource, String dataSourceName){
        Map<Object, Object> defineTargetDataSources = dynamicDataSource.getDefineTargetDataSources();
        defineTargetDataSources.put(dataSourceName, dataSource);
        // 将defineTargetDataSources 传给AbstractRoutingDateSource
        dynamicDataSource.setTargetDataSources(defineTargetDataSources);
        dynamicDataSource.afterPropertiesSet();
    }

}
