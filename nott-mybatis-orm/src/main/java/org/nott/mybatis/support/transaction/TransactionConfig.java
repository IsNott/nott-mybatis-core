package org.nott.mybatis.support.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

//@Configuration
//@EnableTransactionManagement
//public class TransactionConfig implements TransactionManagementConfigurer {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Override
//    public TransactionManager annotationDrivenTransactionManager() {
//        return transactionManager(dataSource);
//    }
//}
