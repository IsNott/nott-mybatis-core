package org.nott.datasource.configuration;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.nott.datasource.DynamicDataSource;
import org.nott.datasource.config.DataSourceConfig;
import org.nott.datasource.config.MultiplyDataSourceConfig;
import org.nott.datasource.support.DataSourceConfigUtils;
import org.nott.datasource.support.SpringContextUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({DataSourceConfig.class})
@EnableTransactionManagement // 启用注解驱动的事务管理
public class DataSourceConfiguration {

    private final DataSourceConfig dataSourceConfig;

    @Bean("dataSource")
    public DataSource setDriverManagerDataSource() {
        return DataSourceConfigUtils.createDataSource(dataSourceConfig);
    }


}
