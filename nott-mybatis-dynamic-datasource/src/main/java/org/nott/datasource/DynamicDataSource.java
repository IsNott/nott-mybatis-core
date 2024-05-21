package org.nott.datasource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * @author Nott
 * @date 2024-5-20
 */
@Getter
@Setter
public class DynamicDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> defineTargetDataSources;

    /**
     * 重写AbstractRoutingDataSource的determineCurrentLookupKey，定义选中当前数据源key的方法
     * @return DataSource’s key
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDynamicDataSourceKey();
    }


}
