package com.sunrisedental.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactory {

    private static final DBConnectionFactory INSTANCE =
            new DBConnectionFactory();

    private static final String URL =
            "jdbc:mysql://localhost:3306/sunrise_dental_clinic";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            System.getenv("Password");

    private DBConnectionFactory() {
    }

    public static DBConnectionFactory getInstance() {
        return INSTANCE;
    }

    public Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD);
    }
}