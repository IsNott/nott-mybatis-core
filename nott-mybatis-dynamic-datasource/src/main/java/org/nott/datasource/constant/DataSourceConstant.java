package org.nott.datasource.constant;

/**
 * @author Nott
 * @date 2024-5-16
 */
public class DataSourceConstant {

    public interface TYPE {
        public static final String DBCP2 = "org.apache.commons.dbcp2.BasicDataSource";
        public static final String HIKARI = "com.zaxxer.hikari.HikariDataSource";
        public static final String TOMCAT_JDBC = "org.apache.tomcat.jdbc.pool.DataSource";
        public static final String DRUID = "com.alibaba.druid.pool.DruidDataSource";
        public static final String JAVAX = "javax.sql.DataSource";
    }
}
