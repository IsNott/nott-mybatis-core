package org.nott.datasource.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @author Nott
 * @date 2024-5-20
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "nott.dynamic-datasource")
public class MultiplyDataSourceConfig {

    private List<DataSourceConfig> dataSourceConfigs;

}
