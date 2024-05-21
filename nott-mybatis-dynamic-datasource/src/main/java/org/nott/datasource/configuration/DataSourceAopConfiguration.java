package org.nott.datasource.configuration;

import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Nott
 * @date 2024-5-21
 */
@Configuration
@EnableAspectJAutoProxy
public class DataSourceAopConfiguration {

    @Bean
    public AspectJAwareAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        AspectJAwareAdvisorAutoProxyCreator creator = new AspectJAwareAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}
