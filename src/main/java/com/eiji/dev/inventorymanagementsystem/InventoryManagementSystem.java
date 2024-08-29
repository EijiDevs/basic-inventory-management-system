package com.eiji.dev.inventorymanagementsystem;

import com.eiji.dev.inventorymanagementsystem.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * @author Esteban Padilla
 */

public class InventoryManagementSystem {

    public static void main(String[] args) {
        // Create a connection to the database
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            System.out.println("Connected to the database successfully.");
        } catch (SQLException e) {
            System.out.println("Error connecting with the database.");
            e.printStackTrace();
        }
    }
}