package org.nott.datasource.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.nott.datasource.config.DataSourceConfig;
import org.nott.datasource.config.HikariDataSourceConfig;
import org.nott.datasource.constant.DataSourceConstant;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
        String type = dataSourceConfig.getType();
        DataSource dataSource;
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create()
                .url(dataSourceConfig.getUrl()) // 基础 URL
                .username(dataSourceConfig.getUsername()) // 基础用户名
                .password(dataSourceConfig.getPassword()) // 基础密码
                .driverClassName(dataSourceConfig.getDriverClassName());// 基础驱动类;
        switch (type){
            default -> dataSource = dataSourceBuilder.build();
            case DataSourceConstant.TYPE.HIKARI -> {
                HikariDataSourceConfig hikariDataSourceConfig = dataSourceConfig.getHikari();
                dataSource = dataSourceBuilder.type(HikariDataSource.class).build();
                HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
                hikariDataSource.setMaximumPoolSize(hikariDataSourceConfig.getMaximumPoolSize());
                hikariDataSource.setAutoCommit(hikariDataSourceConfig.isAutoCommit());
                hikariDataSource.setIdleTimeout(hikariDataSourceConfig.getIdleTimout());
            }
        }
        return dataSource;
    }
}
