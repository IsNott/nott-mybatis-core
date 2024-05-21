package org.nott.datasource.config;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * 读取数据源列表配置类
 * @author Nott
 * @date 2024-5-20
 */

//@Component
//@ConfigurationProperties(prefix = "nott.dynamic-datasource")
@Getter
@Setter
public class MultiplyDataSourceConfig {

    private List<DataSourceConfig> dataSourceConfigs;

}
