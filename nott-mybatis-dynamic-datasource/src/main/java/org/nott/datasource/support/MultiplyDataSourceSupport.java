package org.nott.datasource.support;

import org.nott.datasource.config.DataSourceConfig;
import org.nott.datasource.config.MultiplyDataSourceConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;

/**
 * @author Nott
 * @date 2024-5-20
 */
@Configuration
@EnableConfigurationProperties({MultiplyDataSourceConfig.class})
public class MultiplyDataSourceSupport implements BeanDefinitionRegistryPostProcessor {

    @Autowired
    private MultiplyDataSourceConfig multiplyDataSourceConfig;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (multiplyDataSourceConfig != null) {
            for (DataSourceConfig properties : multiplyDataSourceConfig.getDataSourceConfigs()) {
                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                beanDefinition.setBeanClass(DataSource.class);
                DataSource dataSource = DataSourceConfigUtils.createDataSource(properties);
                beanDefinition.setInstanceSupplier(() -> dataSource);
                registry.registerBeanDefinition(properties.getName(), beanDefinition);
                if(properties.isPrimary()){
                    registry.registerBeanDefinition("default-db", beanDefinition);
                }

            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
