package org.nott.datasource.configuration;

import lombok.RequiredArgsConstructor;
import org.nott.datasource.DynamicDataSource;
import org.nott.datasource.config.DataSourceConfig;
import org.nott.datasource.config.MultiplyDataSourceConfig;
import org.nott.datasource.support.SpringContextUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties({MultiplyDataSourceConfig.class})
@RequiredArgsConstructor
public class MultiplyDataSourceContextConfiguration {

    @Autowired
    private BeanFactory beanFactory;

    private final MultiplyDataSourceConfig multiplyDataSourceConfig;

    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(beanFactory.getBean("default-db"));
        if (multiplyDataSourceConfig == null) {
            return dynamicDataSource;
        }
        Map<Object, Object> defineTargetDataSources = new HashMap<>(16);
        List<DataSourceConfig> dataSourceConfigs = multiplyDataSourceConfig.getDataSourceConfigs();
        dataSourceConfigs.forEach(r -> {
            Object bean = SpringContextUtils.getBean(r.getName());
            defineTargetDataSources.put(bean.getClass().getSimpleName(), bean);
        });
//        dynamicDataSource.setDefaultTargetDataSource(setDriverManagerDataSource());
        dynamicDataSource.setDefaultTargetDataSource(beanFactory.getBean("default-db"));
        dynamicDataSource.setDefineTargetDataSources(defineTargetDataSources);
        return dynamicDataSource;
    }


}
