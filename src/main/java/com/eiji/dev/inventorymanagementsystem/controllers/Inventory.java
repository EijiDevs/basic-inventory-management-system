package com.eiji.dev.inventorymanagementsystem.controllers;

import com.eiji.dev.inventorymanagementsystem.models.Product;
import com.eiji.dev.inventorymanagementsystem.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public String addProduct(int id, String name, float price, int stock) {
       // Valida los datos del producto
       Map<String, Object> data = new HashMap<>();
       data.put("id", id);
       data.put("name", name);
       data.put("price", price);
       data.put("stock", stock);

       String validateMessage = validateProductData(data);
       if (!validateMessage.equals("Valid")) {
           return validateMessage;
       }

       String SQLQuery = "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)";

       try (PreparedStatement ps = connection.prepareStatement(SQLQuery)) {
           ps.setString(1, name);
           ps.setFloat(2, price);
           ps.setInt(3, stock);

           int rowsAffected = ps.executeUpdate();

           // Validate that the query affects minimum one row
           if (rowsAffected == 0) {
               return "No se ha podido agregar el producto.";
           }

       } catch (SQLException e) {
           e.printStackTrace();
           return "Error al agregar el producto: " + e.getMessage();
       }

       return "Se ha agregado el producto correctamente.";
    }
    
    /**
     *Remove an product of the list by product id
     * 
     *@param id Product Unique Identifier
     * 
     *@return A String that indicates the state of the operation
     */
    public String deleteProductById(int id){
        
        String SQLQuery = "DELETE FROM products WHERE id = ?;";
        
        try(PreparedStatement ps = connection.prepareStatement(SQLQuery)) {
            
            ps.setInt(1, id);
            
            int rowsAffected = ps.executeUpdate();
            
            if(rowsAffected == 0){
                return "El producto no existe.";
            }
            
            return "Se ha eliminado el producto correctamente.";

        } catch(Exception e){
            return "No se ha podido eliminar el producto.";
        }
    }
    
    /**
     * List all products
     * 
     * @return A String that indicate the state of the operation
     */
    public String listAllProducts(){
        try {
            //TODO: Migrate internal data access in listAllProducts to use database instead of internal memory
            for(Product product : products){
                System.out.println(product.toString());
            }
            return "Se han listado los productos correctamente.";
        } catch(Exception e){
            return "No se ha podido listar los productos.";
        }
    }
    
    /**
     * Get a product by name
     * 
     * @param name the name of the product in upper or lower case
     * 
     * @return A String that indicates the state of the operation
     */
    public String getProductByName(String name){
        try{
            //TODO: Migrate internal data access in getProductByName to use database instead of internal memory
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
            
            //TODO: Migrate internal data access in updateProductStock to use database instead of internal memory
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
            float price = (float) data.get("price");
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
