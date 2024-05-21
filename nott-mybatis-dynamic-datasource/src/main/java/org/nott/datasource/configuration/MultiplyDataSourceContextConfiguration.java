package org.nott.datasource.configuration;

import lombok.RequiredArgsConstructor;
import org.nott.datasource.DynamicDataSource;
import org.nott.datasource.config.DataSourceConfig;
import org.nott.datasource.config.MultiplyDataSourceConfig;
import org.nott.datasource.constant.DataSourceConstant;
import org.nott.datasource.provider.YamlDataSourceInfoProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nott
 * @date 2024-5-20
 */

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "nott", name = "enable-dynamic-datasource", havingValue = "true")
public class MultiplyDataSourceContextConfiguration {

    @Autowired
    private BeanFactory beanFactory;

    @Bean
    public YamlDataSourceInfoProvider yamlDataSourceInfoProvider(){
        org.nott.datasource.provider.YamlDataSourceInfoProvider provider = new YamlDataSourceInfoProvider();
        provider.setMultiplyDataSourceConfig(provider.loadDataSourceConfigs());
        return provider;
    }

    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        MultiplyDataSourceConfig multiplyDataSourceConfig = yamlDataSourceInfoProvider().getMultiplyDataSourceConfig();
        dynamicDataSource.setDefaultTargetDataSource(beanFactory.getBean(DataSourceConstant.DEFAULT_DB));
        if (multiplyDataSourceConfig == null) {
            return dynamicDataSource;
        }
        Map<Object, Object> defineTargetDataSources = new HashMap<>(16);
        List<DataSourceConfig> dataSourceConfigs = multiplyDataSourceConfig.getDataSourceConfigs();
        for (DataSourceConfig config : dataSourceConfigs) {
            Object bean = beanFactory.getBean(config.getName());
            defineTargetDataSources.put(config.getName(), bean);
        }
        dynamicDataSource.setDefaultTargetDataSource(beanFactory.getBean(DataSourceConstant.DEFAULT_DB));
        dynamicDataSource.setTargetDataSources(defineTargetDataSources);
        dynamicDataSource.setDefineTargetDataSources(defineTargetDataSources);
        return dynamicDataSource;
    }


}
