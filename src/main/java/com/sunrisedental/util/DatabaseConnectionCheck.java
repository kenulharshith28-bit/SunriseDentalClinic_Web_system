package com.sunrisedental.util;

import java.sql.Connection;

public class DatabaseConnectionCheck {

    public static void main(String[] args)
            throws Exception {

        try (Connection connection =
                     DBConnectionFactory
                             .getInstance()
                             .getConnection()) {

            System.out.println(
                    "Connected to MySQL successfully");

            System.out.println(
                    "Database: "
                            + connection.getCatalog());
        }
    }
}