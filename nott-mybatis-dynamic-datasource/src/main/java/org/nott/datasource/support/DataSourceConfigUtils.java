package org.nott.datasource.support;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.nott.datasource.config.DataSourceConfig;
import org.nott.datasource.config.DruidDataSourceConfig;
import org.nott.datasource.config.HikariDataSourceConfig;
import org.nott.datasource.constant.DataSourceConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

/**
 * @author Nott
 * @date 2024-5-17
 */

public class DataSourceConfigUtils {

    public static DataSource createDataSource(DataSourceConfig dataSourceConfig){
        DataSource dataSource;
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create()
                .url(dataSourceConfig.getUrl()) // 基础 URL
                .username(dataSourceConfig.getUsername()) // 基础用户名
                .password(dataSourceConfig.getPassword()); // 基础密码
        //.driverClassName(dataSourceConfig.getDriverClassName());// 基础驱动类;
        dataSource = DataSourceConfigUtils.tranConfigToActuallyClass(dataSourceBuilder,dataSourceConfig);
        return dataSource;
    }

    public static DataSource tranConfigToActuallyClass(DataSourceBuilder dataSourceBuilder, DataSourceConfig dataSourceConfig) {
        String type = dataSourceConfig.getType();
        DataSource dataSource;
        switch (type) {
            default -> dataSource = dataSourceBuilder.build();
            case DataSourceConstant.TYPE.HIKARI -> {
                dataSource = tranHikariDataSource(dataSourceBuilder, dataSourceConfig);
            }
            case DataSourceConstant.TYPE.DRUID -> {
                dataSource = tranDruidDataSource(dataSourceBuilder,dataSourceConfig);
            }
        }
        return dataSource;
    }

    private static DataSource tranDruidDataSource(DataSourceBuilder dataSourceBuilder, DataSourceConfig dataSourceConfig) {
        DruidDataSourceConfig druid = dataSourceConfig.getDruid();
        DruidDataSource dataSource = (DruidDataSource)dataSourceBuilder.type(DruidDataSource.class).build();
        BeanUtils.copyProperties(druid,dataSource);
        return dataSource;
    }


    private static DataSource tranHikariDataSource(DataSourceBuilder dataSourceBuilder, DataSourceConfig dataSourceConfig) {
        DataSource dataSource;
        dataSourceBuilder.driverClassName(dataSourceConfig.getDriverClassName());
        HikariDataSourceConfig hikariDataSourceConfig = dataSourceConfig.getHikari();
        dataSource = dataSourceBuilder.type(HikariDataSource.class).build();
        HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
        BeanUtils.copyProperties(hikariDataSourceConfig,hikariDataSource);
//        hikariDataSource.setAutoCommit(hikariDataSourceConfig.isAutoCommit());
//        hikariDataSource.setConnectionTimeout(hikariDataSourceConfig.getConnectionTimeOut());
//        hikariDataSource.setMaximumPoolSize(hikariDataSourceConfig.getMaximumPoolSize());
//        hikariDataSource.setIdleTimeout(hikariDataSourceConfig.getIdleTimout());
//        hikariDataSource.setConnectionTestQuery(hikariDataSourceConfig.getConnectionTestQuery());
//        hikariDataSource.setMaxLifetime(hikariDataSourceConfig.getMaxLifeTime());
//        hikariDataSource.setMinimumIdle(hikariDataSourceConfig.getMinimumIdle());
//        hikariDataSource.setPoolName(hikariDataSourceConfig.getPoolName());
//        hikariDataSource.setDriverClassName(hikariDataSourceConfig.getDriverClassName());
//        hikariDataSource.setCatalog(hikariDataSource.getCatalog());
        return hikariDataSource;
    }
}
