package org.nott.datasource.support;

import org.nott.datasource.config.DataSourceConfig;
import org.nott.datasource.config.MultiplyDataSourceConfig;
import org.nott.datasource.exception.DynamicInitException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;


import javax.sql.DataSource;
import java.io.InputStream;
/**
 * @author Nott
 * @date 2024-5-20
 */
@Configuration
public class MultiplyDataSourceSupport implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        MultiplyDataSourceConfig multiplyDataSourceConfig = loadDataSourceConfigs();
        if (multiplyDataSourceConfig == null || multiplyDataSourceConfig.getDataSourceConfigs() == null) {
            throw new DynamicInitException("Dont found any dynamic data source config info.");
        }
        for (DataSourceConfig properties : multiplyDataSourceConfig.getDataSourceConfigs()) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(DataSource.class);
            DataSource dataSource = DataSourceConfigUtils.createDataSource(properties);
            beanDefinition.setInstanceSupplier(() -> dataSource);
            registry.registerBeanDefinition(properties.getName(), beanDefinition);
            if (properties.isPrimary()) {
                registry.registerBeanDefinition("default-db", beanDefinition);
            }

        }
    }

    private MultiplyDataSourceConfig loadDataSourceConfigs() {

        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("data-source.yml");
        Constructor constructor = new Constructor(MultiplyDataSourceConfig.class, new LoaderOptions());
        TypeDescription typeDescription = new TypeDescription(MultiplyDataSourceConfig.class);
        constructor.addTypeDescription(typeDescription);
        Yaml yaml = new Yaml(constructor);
        MultiplyDataSourceConfig multiplyDataSourceConfig = yaml.loadAs(inputStream, MultiplyDataSourceConfig.class);
        return multiplyDataSourceConfig;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
