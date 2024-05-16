package org.nott.datasource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Nott
 * @date 2024-5-10
 */

@Component
@ConfigurationProperties("nott.datasource")
@Data
public class DataSourceConfig {

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private String type;

    private HikariDataSourceConfig hikari;
}
