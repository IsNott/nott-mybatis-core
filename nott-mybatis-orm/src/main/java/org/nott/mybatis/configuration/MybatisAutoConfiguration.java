package org.nott.mybatis.configuration;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.nott.mybatis.config.NottMybatisConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.util.ArrayList;


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
        Resource[] commonMapperResources = resolver.getResources(nottMybatisConfig.getCommonMapperXmlLocation());
        Resource[] resources = resolver.getResources(nottMybatisConfig.getMapperLocation());
        ArrayList<FileSystemResource> list = new ArrayList<>();
        for (Resource resource : resources) {
            list.add((FileSystemResource) resource);
        }
        for (Resource resource : commonMapperResources) {
            list.add((FileSystemResource) resource);
        }
        sqlSessionFactoryBean.setMapperLocations(list.toArray(Resource[]::new));
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(nottMybatisConfig.getMybatisConfigLocation()));
        return sqlSessionFactoryBean;
    }

}
