package org.nott.datasource.configuration;

import lombok.RequiredArgsConstructor;
import org.nott.datasource.config.DataSourceConfig;
import org.nott.datasource.support.DataSourceConfigUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({DataSourceConfig.class})
@EnableTransactionManagement // 启用注解驱动的事务管理
public class DataSourceConfiguration {

    private final DataSourceConfig dataSourceConfig;

    @Bean("dataSource")
    public DataSource setDriverManagerDataSource() {
        return DataSourceConfigUtils.createDataSource(dataSourceConfig);
    }


}
