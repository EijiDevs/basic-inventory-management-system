package com.eiji.dev.inventorymanagementsystem.utils;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Esteban Padilla
 */
public class DatabaseConnection {
    
    private static DatabaseConnection instance;
    private Connection connection;
    // Load .env file
    Dotenv dotenv = Dotenv.load();
    // Read .env variables
    String dbUrl = dotenv.get("DB_URL");
    String dbUser = dotenv.get("DB_USER");
    String dbPassword = dotenv.get("DB_PASSWORD");
 
    // Private constructor to prevent instantiation
    private DatabaseConnection() {
        try {
            // Initialize the database connection
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    // Public method to provide access to the single instance
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Method to get the connection object
    public Connection getConnection() {
        return connection;
    }

    // Optionally, you can add a method to close the connection
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
