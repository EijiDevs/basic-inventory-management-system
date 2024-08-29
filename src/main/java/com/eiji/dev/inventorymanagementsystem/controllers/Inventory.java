package com.eiji.dev.inventorymanagementsystem.controllers;

import com.eiji.dev.inventorymanagementsystem.models.Product;
import com.eiji.dev.inventorymanagementsystem.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Esteban Padilla
 */
public class Inventory {
    private Set<Product> products = new HashSet<Product>();
    private Connection connection;
    
    public Inventory(){
        // Create a connection to the database
        connection = DatabaseConnection.getInstance().getConnection();
        System.out.println("Connected to the database successfully.");
    }
    
    /**
     * Add a new product to the inventory
     * 
     * @param id An unique identifier to the product
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the stock number of the product
     * 
     * @return A String that indicates the state of the operation
     */
    public String addProduct(int id, String name, double price, int stock){
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("id", id);
            data.put("price", price);
            data.put("stock", stock);

            // Validate data to an product
            String validateMessage = validateProductData(data);
            if(!validateMessage.equals("Valid")){
                return validateMessage;
            }
            
            Product newProduct = new Product(id, name, price, stock);
                
            products.add(newProduct);
        } catch(Exception e){
            return "No se ha podido agregar el producto.";
        }
        return "Se ha agregado el producto correctamente.";
    }
    
    //TODO: Remove an product of the list
    /**
     *Remove an product of the list by product id
     * 
     *@param id Product Unique Identifier
     * 
     *@return A String that indicates the state of the operation
     */
    public String deleteProductById(int id){
        try {
            for(Product product : products){
                // Product must exist
                if(product.getId() != id) {continue;}
                
                products.remove(product);
                
                return "Se ha eliminado el producto correctamente.";
            }
            
            return "El producto no existe.";
        } catch(Exception e){
            return "No se ha podido eliminar el producto.";
        }
    }
    
    //TODO: List all products
    /**
     * List all products
     * 
     * @return A String that indicate the state of the operation
     */
    public String listAllProducts(){
        try {
            for(Product product : products){
                System.out.println(product.toString());
            }
            return "Se han listado los productos correctamente.";
        } catch(Exception e){
            return "No se ha podido listar los productos.";
        }
    }
    
    //TODO: Search an product by name
    /**
     * Get a product by name
     * 
     * @param name the name of the product in upper or lower case
     * 
     * @return A String that indicates the state of the operation
     */
    public String getProductByName(String name){
        try{
            for(Product product : products){
                // Validate that product with that name exists
                if(product.getName().equalsIgnoreCase(name)){
                    return "El producto se ha recuperado correctamente. " + product.toString();
                }
            }
            return "El producto no existe.";
        }catch(Exception e){
            return "El producto no existe.";
        }
    }
    
    //TODO: Update product stock 
    /**
     * Update the stock of the product by id
     * 
     * @param id Product Unique Identifier
     * @param stock the new product stock to be updated
     * 
     * @return A String that indicates the state of the operation
     */
    public String updateProductStock(int id, int stock){
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("id", id);
            data.put("stock", stock);
            
            String validateMessage = validateProductData(data);
            if(!validateMessage.equals("Valid")){
                return validateMessage;
            }
            
            for(Product product : products){
                if(product.getId() != id) {continue;}
                
                product.setStock(stock);
                
                return "El stock del producto se ha actualizado correctamente.";
            }
            return "El producto no existe.";
        } catch (Exception e) {
            return "No se ha podido actualizar el stock del producto.";
        }
    }
    
    /**
     * Validate if the data is valid to an product
     * 
     * @param data an Map that contains the product data to validate
     * 
     * @return A String that indicates if the data is valid to an product
     */
    public String validateProductData(Map<String, Object> data) {
        if (data.containsKey("price")) {
            double price = (double) data.get("price");
            if (price <= 0) {
                return "El precio debe ser un valor mayor a 0.";
            }
        }

        if (data.containsKey("stock")) {
            int stock = (int) data.get("stock");
            if (stock <= 0) {
                return "El número de stock debe ser un valor mayor a 0.";
            }
        }

        if (data.containsKey("id")) {
            int id = (int) data.get("id");
            for (Product product : products) {
                if (product.getId() == id) {
                    return "El producto debe tener un identificador único.";
                }
            }
        }

        return "Valid";
    }
}
