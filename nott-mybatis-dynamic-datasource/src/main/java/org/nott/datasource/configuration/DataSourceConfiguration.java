package org.nott.datasource.configuration;

import lombok.RequiredArgsConstructor;
import org.nott.datasource.config.DataSourceConfig;
import org.nott.datasource.config.MultiplyDataSourceConfig;
import org.nott.datasource.support.DataSourceConfigUtils;
import org.nott.datasource.support.MultiplyDataSourceSupport;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Assert;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({DataSourceConfig.class})
@EnableTransactionManagement // 启用注解驱动的事务管理
public class DataSourceConfiguration {

    private final DataSourceConfig dataSourceConfig;

    @Bean("dataSource")
    public DataSource setDriverManagerDataSource() {
        DataSource dataSource;
        if (dataSourceConfig == null || dataSourceConfig.getUrl() == null) {
            MultiplyDataSourceSupport support = new MultiplyDataSourceSupport();
            MultiplyDataSourceConfig multiplyDataSourceConfig = support.loadDataSourceConfigs();
            Assert.notNull(multiplyDataSourceConfig, "DataSource And MutiplySource must not be null at same time");
            Assert.notNull(multiplyDataSourceConfig.getDataSourceConfigs(), "MutiplySource list not allow null");
            DataSourceConfig primaryDSConfig = multiplyDataSourceConfig.getDataSourceConfigs().stream()
                    .filter(DataSourceConfig::isPrimary).findAny().orElse(null);
            if (primaryDSConfig == null) {
                primaryDSConfig = multiplyDataSourceConfig.getDataSourceConfigs().get(0);
            }
            dataSource = DataSourceConfigUtils.createDataSource(primaryDSConfig);
        } else {
            dataSource = DataSourceConfigUtils.createDataSource(dataSourceConfig);
        }
        return dataSource;
    }


}
