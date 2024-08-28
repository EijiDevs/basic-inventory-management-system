/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.eiji.dev.inventorymanagementsystem;

import com.eiji.dev.inventorymanagementsystem.controllers.Inventory;

/**
 *
 * @author Esteban Padilla
 */
public class InventoryManagementSystem {

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        
        // Add 20 products
        for (int i = 1; i <= 20; i++) {
            String name = "Product" + i;
            double price = 10.0 + i; // Example prices
            int stock = 100 - i; // Example stock numbers
            System.out.println(inventory.addProduct(i, name, price, stock));
        }

        // List all products
        System.out.println("\nListing all products:");
        System.out.println(inventory.listAllProducts());

        // Search for some products
        System.out.println("\nSearching for Product5:");
        System.out.println(inventory.getProductByName("Product5"));

        System.out.println("\nSearching for Product20:");
        System.out.println(inventory.getProductByName("Product20"));

        // Update product stock
        System.out.println("\nUpdating stock for Product10:");
        System.out.println(inventory.updateProductStock(10, 50));

        // Delete a product
        System.out.println("\nDeleting Product15:");
        System.out.println(inventory.deleteProductById(15));

        // List all products again
        System.out.println("\nListing all products after updates:");
        System.out.println(inventory.listAllProducts());
    }
}
