package org.nott.mybatis.registrar;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.nott.mybatis.mapper.CommonMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;


/**
 *  验证外部应用加载bean上下文时是否自动注册CommonMapper bean
 * @author Nott
 * @date 2024-5-22
 */
@Configuration
@ConditionalOnMissingBean(CommonMapper.class)
public class NottMapperScannerRegistrar implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
        builder.addPropertyValue("processPropertyPlaceHolders", true);
        List<String> packageName = Arrays.asList("org.nott.mybatis");
        builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(packageName));
        registry.registerBeanDefinition("commonMapper",builder.getBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // do nothing
    }
}
