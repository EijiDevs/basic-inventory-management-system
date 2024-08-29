# Project Description
Develop a system for managing the inventory of products in a store. This system should allow users to add products to the inventory, search for products by name, list all available products, update the stock of products, and remove products that are no longer for sale.

# Business Rules
## Products:
- Each product in the store has a name, a unique code, a quantity in stock, and a price.
- The product code must be unique. No two products can have the same code in the inventory.
- The stock quantity of a product must be a non-negative integer.
## Inventory Management:
- New products can be added to the inventory.
- Products can be searched by name to view their details.
- All available products in the store can be listed.
- The stock quantity of an existing product can be updated. If a product is no longer available, it can be removed from the inventory.
- Products cannot be removed if they do not exist in the inventory.

## Entity-Relationship Diagram (ERD)

To provide a clear understanding of the database structure used in this project, below is the Entity-Relationship Diagram (ERD) that reflects the "Product" table. The ERD illustrates the relationships and attributes associated with the `Product` entity.

### ERD Overview
- **Product Table**: Contains fields for `id`, `name`, `price`, and `stock`.
- The `id` field serves as the primary key and must be unique across all products.
- Relationships between entities and other potential tables are visualized here.

<p align="center">
  <img src="https://github.com/EijiDevs/basic-inventory-management-system/blob/main/docs/ER%20Basic%20Inventory%20Management%20System.png" alt="ER Diagram">
</p>
