package org.nott.mybatis.support.aop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Nott
 * @date 2024-5-13
 */
@Data
@Component
@ConfigurationProperties("nott.mybatis.aop")
public class MybatisAopConfig {

    /**
     * 拦截基础的CommonMapper aop表达式
     */
    private String baseAopPackageExpression = "execution(* org.nott.mybatis.mapper.*.*(..))";

    /**
     * 自定义加上的aop表达式
     */
    private String[] appendAopPackageExpression;

}
