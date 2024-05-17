package org.nott.datasource.configuration;

import lombok.RequiredArgsConstructor;
import org.nott.datasource.config.DataSourceConfig;
import org.nott.datasource.support.DataSourceConfigUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DataSourceConfig.class)
@RequiredArgsConstructor
public class DataSourceConfiguration {

    private final DataSourceConfig dataSourceConfig;

    @Bean("dataSource")
    public DataSource setDriverManagerDataSource(){
        DataSource dataSource;
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create()
                .url(dataSourceConfig.getUrl()) // 基础 URL
                .username(dataSourceConfig.getUsername()) // 基础用户名
                .password(dataSourceConfig.getPassword()); // 基础密码
                //.driverClassName(dataSourceConfig.getDriverClassName());// 基础驱动类;
        dataSource = DataSourceConfigUtils.tranConfigToActuallyClass(dataSourceBuilder,dataSourceConfig);
        return dataSource;
    }
}
