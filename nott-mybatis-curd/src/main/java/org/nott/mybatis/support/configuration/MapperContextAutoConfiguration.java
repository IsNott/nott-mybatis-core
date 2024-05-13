package org.nott.mybatis.support.configuration;

import org.nott.mybatis.config.NottMybatisConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置aop拦截的mapper
 * @author Nott
 * @date 2024-5-13
 */

@Configuration
@EnableConfigurationProperties(NottMybatisConfig.class)
public class MapperContextAutoConfiguration {
}
