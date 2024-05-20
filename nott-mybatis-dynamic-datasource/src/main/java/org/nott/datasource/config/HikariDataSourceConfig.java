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

    /**
     * 自动提交从池中返回的连接
     */
    private boolean autoCommit = true;

    /**
     * 等待来自池的连接的最大毫秒数
     */
    private Long connectionTimeOut = 30000L;

    /**
     *  最大连接数，小于等于0会被重置为默认值10
     */
    private Integer maximumPoolSize = 5;

    /**
     * 空闲连接超时时间，默认值600000（10分钟），
     * 大于等于max-lifetime且max-lifetime>0，会被重置为0；
     * 不等于0且小于10秒，会被重置为10秒
     */
    private Long idleTimeout = 600000L;

    /**
     * 测试连接是否可用的查询语句
     */
    private String connectionTestQuery = "select 1";

    /**
     *  连接最大存活时间，不等于0且小于30秒，会被重置为默认值30分钟
     */
    private Long maxLifeTime;

    /**
     * 最小空闲连接，默认值10
     */
    private Integer minimumIdle;

    /**
     * 连接池定义名称
     */
    private String poolName;

    /**
     * 驱动
     */
    private String driverClassName;

    /**
     * 为支持 catalog 概念的数据库设置默认 catalog
     */
    private String catalog;



}
