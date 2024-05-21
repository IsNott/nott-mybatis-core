package org.nott.datasource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据源基础配置类
 * @author Nott
 * @date 2024-5-10
 */

@Component
@ConfigurationProperties("nott.datasource")
@Data
public class DataSourceConfig {

    private String name;

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private String type;

    private boolean primary = false;

    private HikariDataSourceConfig hikari;

    private DruidDataSourceConfig druid;
}
