package org.nott.datasource.configuration;

import lombok.RequiredArgsConstructor;
import org.nott.datasource.config.DataSourceConfig;
import org.nott.datasource.config.MultiplyDataSourceConfig;
import org.nott.datasource.support.DataSourceConfigUtils;
import org.nott.datasource.support.MultiplyDataSourceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({DataSourceConfig.class})
//@EnableTransactionManagement // 启用注解驱动的事务管理
public class DataSourceConfiguration {

    private final DataSourceConfig dataSourceConfig;

    @Bean("dataSource")
    public DataSource setDriverManagerDataSource() {
        DataSource dataSource;
        if (dataSourceConfig == null || dataSourceConfig.getUrl() == null) {
            MultiplyDataSourceSupport support = new MultiplyDataSourceSupport();
            MultiplyDataSourceConfig multiplyDataSourceConfig = support.loadDataSourceConfigs();
            Assert.notNull(multiplyDataSourceConfig, "DataSource And MutiplySource must not be null at same time");
            Assert.notNull(multiplyDataSourceConfig.getDataSourceConfigs(), "MutiplySource list not allow null");
            DataSourceConfig primaryDSConfig = multiplyDataSourceConfig.getDataSourceConfigs().stream()
                    .filter(DataSourceConfig::isPrimary).findAny().orElse(null);
            if (primaryDSConfig == null) {
                primaryDSConfig = multiplyDataSourceConfig.getDataSourceConfigs().get(0);
            }
            dataSource = DataSourceConfigUtils.createDataSource(primaryDSConfig);
        } else {
            dataSource = DataSourceConfigUtils.createDataSource(dataSourceConfig);
        }
        return dataSource;
    }

    /**
     * 事务定义
     * @param dataSource 数据源
     * @return 返回事务
     */
    @Bean("dataSourceTransactionManager")
    public TransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager tradeTransactionMgr = new DataSourceTransactionManager();
        tradeTransactionMgr.setDataSource(dataSource);
        return tradeTransactionMgr;
    }

    /**
     * 用于编程式事务管理的工具类
     * @param dataSourceTransactionManager 事务管理器
     * @return TransactionTemplate bena
     */
    @Bean
    @DependsOn("dataSourceTransactionManager")
    public TransactionTemplate transactionTemplate(@Qualifier("dataSourceTransactionManager") DataSourceTransactionManager dataSourceTransactionManager) {
        return new TransactionTemplate(dataSourceTransactionManager);
    }


}
