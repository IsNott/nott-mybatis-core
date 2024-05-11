package org.nott.datasource.configuration;

import lombok.RequiredArgsConstructor;
import org.nott.datasource.config.DataSourceConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DataSourceConfig.class)
@RequiredArgsConstructor
public class DataSourceConfiguration {

    private final DataSourceConfig dataSourceConfig;

    @Bean("dataSource")
    public DataSource setDriverManagerDataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(dataSourceConfig.getUrl());
        driverManagerDataSource.setDriverClassName(dataSourceConfig.getDriverClassName());
        driverManagerDataSource.setPassword(dataSourceConfig.getPassword());
        driverManagerDataSource.setUsername(dataSourceConfig.getUsername());
        return driverManagerDataSource;
    }
}
