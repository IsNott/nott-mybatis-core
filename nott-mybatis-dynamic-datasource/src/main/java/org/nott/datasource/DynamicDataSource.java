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

    // 查找当前线程要使用的数据源
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDynamicDataSourceKey();
    }


}
