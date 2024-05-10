package org.nott.mybatis.support;

import com.mysql.cj.Messages;
import com.mysql.cj.jdbc.NonRegisteringDriver;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.SQLException;

/**
 * @author Nott
 * @date 2024-5-10
 */

//@Component
public class NottDataSource extends AbstractDataSource {


    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return super.createConnectionBuilder();
    }
}
