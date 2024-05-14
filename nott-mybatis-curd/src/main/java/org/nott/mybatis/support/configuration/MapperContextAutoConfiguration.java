package org.nott.mybatis.support.configuration;

import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.Interceptor;
import org.nott.mybatis.support.Interceptor.MybatisAopInterceptor;
import org.nott.mybatis.support.config.MybatisAopConfig;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 基于mapper的aop配置
 * @author Nott
 * @date 2024-5-13
 */

@Configuration
@EnableConfigurationProperties(MybatisAopConfig.class)
@RequiredArgsConstructor
public class MapperContextAutoConfiguration {

    private final MybatisAopConfig mybatisAopConfig;

    @Bean
    public Interceptor setMybatisAopInterceptor(){
        return new MybatisAopInterceptor();
    }

    @Bean
    public PointcutAdvisor setMyBatisPointcutAdvisor(){
        // 设置拦截表达式
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        String baseAopPackage = mybatisAopConfig.getBaseAopPackageExpression();
        advisor.setExpression(baseAopPackage);
        String[] appendAopPackage = mybatisAopConfig.getAppendAopPackageExpression();
        if (appendAopPackage != null && appendAopPackage.length > 0) {
            for (String packageExpression : appendAopPackage) {
                advisor.setExpression(packageExpression);
            }
        }

        // 切面
        advisor.setAdvice(setMybatisAopInterceptor());

        return advisor;

    }
}
