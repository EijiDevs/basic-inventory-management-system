package com.eiji.dev.inventorymanagementsystem;

import com.eiji.dev.inventorymanagementsystem.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Esteban Padilla
 */

public class InventoryManagementSystem {

    public static void main(String[] args) {
        // Create a connection to the database
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            System.out.println("Connected to the database successfully.");

            // Example query to list all products
            String query = "SELECT * FROM products";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("id") +
                                       ", Name: " + resultSet.getString("name") +
                                       ", Price: " + resultSet.getDouble("price") +
                                       ", Stock: " + resultSet.getInt("stock"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}