package org.nott.mybatis.configuration;


import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.nott.mybatis.NottMapperScannerRegistrar;
import org.nott.mybatis.config.NottMybatisConfig;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;


import javax.sql.DataSource;

/**
 * mybatis配置类
 * @author Nott
 * @date 2024-5-10
 */

@Configuration
@EnableConfigurationProperties(NottMybatisConfig.class)
@RequiredArgsConstructor
public class MybatisAutoConfiguration {

    private final NottMybatisConfig nottMybatisConfig;


    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(nottMybatisConfig.getMapperLocation()));
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(nottMybatisConfig.getMybatisConfigLocation()));
        return sqlSessionFactoryBean;
    }

    // todo 验证外部应用加载bean上下文时是否自动注册CommonMapper bean
    @Bean("NottMapperScannerRegistrar")
    public NottMapperScannerRegistrar registrar(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry){
        NottMapperScannerRegistrar registrar = new NottMapperScannerRegistrar();
        registrar.registerBeanDefinitions(importingClassMetadata, registry);
        return registrar;
    }

}
