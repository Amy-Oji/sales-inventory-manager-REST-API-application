# sales-inventory-manager-REST-API-application
REST-API application for managing sales/inventory built with SpringBoot

The application supports the following operations:

* Create a new product

* Update an existing product.

* Get a list of all available products.

* Place an Order based on availability of product.

* Save customer details


The app also publishes the basic detail of the created Order to Kafka for reporting.

This resport is later consumed my the [Sales Report Application](https://github.com/Amy-Oji/kafka-reporder)

____
The App currently make provision for only 2 types of users: admin and customer. 
Authentication has not been implemented yet so there is no logging in. 
The admin endpoint include:

create-product

update-product/{product-id}

get-all-available-products

while the customers can only 

order-products

The app is more or less a single-vendor mini-e-commence app.



### Enitities:

The currently has four tables:

##### Customers 
Hold customer-users data 

##### Orders
Hold data of purchased items and its related details 
##### Products
Product data such as product name, amount in stock, price, etc.
##### Order-details
This table has a many to one relationship with both the orders and products table. 
Hold data about every single product in an order.
Has fields like price-per-unit and quantity-ordered for each ordered product. 
The difference between Orders and Order-details is that the latter hold the summary of each order
the former has the data of each product in an order. 
And in comparison to the Products table, 
the Order-details hold order-related data while the Products hold stock-related data. 


_____
The app was developed with the IntelliJ IDE community version. As a result, 
I have no access to certain features like 'already-JPA' queries.
I had to whip out my native SQL writing skills as a result. It was quite refreshing tho.

