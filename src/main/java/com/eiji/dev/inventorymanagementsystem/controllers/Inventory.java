package com.eiji.dev.inventorymanagementsystem.controllers;

import com.eiji.dev.inventorymanagementsystem.models.Product;
import com.eiji.dev.inventorymanagementsystem.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Esteban Padilla
 */
public class Inventory {
    
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
           ps.setString(0, name);
           ps.setFloat(1, price);
           ps.setInt(2, stock);

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
            
            ps.setInt(0, id);
            
            int rowsAffected = ps.executeUpdate();
            
            if(rowsAffected == 0){
                return "El producto no existe.";
            }

        } catch(SQLException e){
            e.printStackTrace();
            return "Error al eliminar el producto: " + e.getMessage();
        }
        
        return "Se ha eliminado el producto correctamente.";
    }
    
    /**
     * Get all products
     * 
     * @return A list that contains all the products
     */
    public List<Product> getAllProducts(){
        
        List<Product> products = null;
        
        String SQLQuery = "SELECT * FROM products";
        
        try (PreparedStatement ps = connection.prepareStatement(SQLQuery)) {

            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), rs.getInt("stock"));
                
                products.add(product);
                
            }
            
        } catch(SQLException e){
            e.printStackTrace();
            System.out.println("Error al recuperar los productos: " + e.getMessage());
        }
        
        return products;
    }
    
    /**
     * Get a product by name
     * 
     * @param name the name of the product in upper or lower case
     * 
     * @return The object of the recovered product
     */
    public Product getProductByName(String name){
        
        Product product = null;
        
        String SQLQuery = "SELECT * FROM products WHERE name = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(SQLQuery)){
            
            ps.setString(0, name);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                product = new Product(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), rs.getInt("stock"));
            }
            
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Error al recuperar el producto: " + e.getMessage());
        }
        
        return product;
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
        
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("stock", stock);
            
        String validateMessage = validateProductData(data);
        if(!validateMessage.equals("Valid")){
            return validateMessage;
        }
            
        String SQLQuery = "UPDATE products SET stock = ? WHERE id = ?;";
        
        
        try (PreparedStatement ps = connection.prepareStatement(SQLQuery)){
            
            ps.setInt(0, stock);
            ps.setInt(1, id);
            
            int rowsAffected = ps.executeUpdate();
            
            if(rowsAffected == 0){
                return "El producto no existe.";
            }
                
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el producto: " + e.getMessage());
        }
        
        return "El stock del producto se ha actualizado correctamente.";
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
