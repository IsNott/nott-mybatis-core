package org.nott.mybatis.sql;

import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Nott
 * @date 2024-6-5
 */
@Component
public class MyBatisStaticUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    public static void setContext(ApplicationContext ctx) {
        context = ctx;
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        if (context == null) {
            throw new IllegalStateException("ApplicationContext has not been set");
        }
        return (SqlSessionFactory) context.getBean("sqlSessionFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
