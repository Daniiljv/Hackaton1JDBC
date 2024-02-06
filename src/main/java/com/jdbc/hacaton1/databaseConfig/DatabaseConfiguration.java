package com.jdbc.hacaton1.databaseConfig;

import org.springframework.context.annotation.Configuration;

import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConfiguration {

    private final String URL = "jdbc:postgresql://localhost:5432/Hackaton#1";
    private final String USER = "postgres";
    private final String PASSWORD = "postgres";

    public java.sql.Connection connection() {
        java.sql.Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return connection;
    }
}
