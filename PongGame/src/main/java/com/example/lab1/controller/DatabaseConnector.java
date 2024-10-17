/**package com.example.lab1.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static Connection connection;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            createConnection();
        }
        return connection;
    }

    private static void createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/assignment";
        String username = "root";
        String password = "root";
        connection = DriverManager.getConnection(url, username, password);
    }
}
**/