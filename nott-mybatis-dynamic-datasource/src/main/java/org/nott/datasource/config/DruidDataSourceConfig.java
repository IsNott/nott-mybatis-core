package org.nott.datasource.config;

import lombok.Data;

import java.util.Properties;

/**
 * Druid配置类
 * @author Nott
 * @date 2024-5-17
 */
@Data
public class DruidDataSourceConfig {

    private Properties connectProperties;

    private String connectionProperties;

    // 连接超时时间 0=永远等待
    private int connectTimeout = 0;

    // 物理连接初始化时执行的sql
    private String[] connectionInitSqls;

    private String dbType;

    private String createScheduler;

    private String destroyScheduler;

    private String driverClassName;

    /**
     * 属性类型是逗号隔开的字符串，通过别名的方式配置扩展插件，
     * 插件别名列表请参考druid jar包中的 /META-INF/druid-filter.properties,常用的插件有：
     * 监控统计用的filter:stat
     * 日志用的filter:log4j
     * 防御sql注入的filter:wall
     * 防御sql注入的filter:wall
     */
    private String filters;

    private int initialSize;

    private boolean keepAlive;

    private String validationQuery = "select 1";

    private int validationQueryTimeout = -1;

    private int transactionQueryTimeout = 0;

    private int maxActive = 1;

    private int minIdle = 0;

    private String name;

    private int maxWait;

    private long minEvictableIdleTimeMillis;

}
