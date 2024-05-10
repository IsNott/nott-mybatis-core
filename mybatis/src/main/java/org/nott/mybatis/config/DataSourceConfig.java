package org.nott.mybatis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Nott
 * @date 2024-5-10
 */

@Component
@ConfigurationProperties("spring.datasource")
@Data
public class DataSourceConfig {

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private String type;
}
