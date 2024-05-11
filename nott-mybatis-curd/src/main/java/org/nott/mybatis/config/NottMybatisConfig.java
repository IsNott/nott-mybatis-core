package org.nott.mybatis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Nott
 * @date 2024-5-10
 */
@Setter
@Getter
@Component
@ConfigurationProperties("nott.mybatis")
public class NottMybatisConfig {

    /**
     * mybatis 配置文件地址
     */
    private String mybatisConfigLocation = "META-INF/spring/mybatis-config.xml";

    private String mapperLocation;

//    private String mybatisBasePackage;
}
