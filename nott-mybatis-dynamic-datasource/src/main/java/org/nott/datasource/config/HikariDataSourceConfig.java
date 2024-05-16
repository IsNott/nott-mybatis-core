package org.nott.datasource.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Nott
 * @date 2024-5-16
 */

@Setter
@Getter
public class HikariDataSourceConfig {

    private boolean autoCommit;

    private Long connectionTimeOut;

    private Integer maximumPoolSize = 5;

    private Long idleTimout = 600000L;

    private String connectionTestQuery = "select 1";

}
