package org.nott.mybatis.sql;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author Nott
 * @date 2024-6-5
 */
public class MyBatisStaticUtil {

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
}
