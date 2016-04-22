package com.becomejavasenior.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class CommonDao {
    private DataSource dataSource;

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public CommonDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
