# sales-inventory-manager-REST-API-application
REST-API application for managing sales/inventory built with SpringBoot.

I consider app to be a single-vender mini-ecommerce plaitform.

The application supports the following operations:

* Create a new product

* Update an existing product.

* Get a list of all available products.

* Place an Order based on availability of product.

* Save customer details


The app also publishes the basic detail of the created Order to Kafka for reporting.

The Kafka published resport is later consumed my this other project - [Sales Report Application](https://github.com/Amy-Oji/kafka-reporder)

___
#### Running/Testing The App

*What you need:

1. Postgres Database
2. Kafka Installed (For details on how to install Kafka MacOS, [READ THIS](https://medium.com/@taapasagrawal/installing-and-running-apache-kafka-on-macos-with-m1-processor-5238dda81d51)
3. IntelliJ IDE
4. Postman

*Running the Application

1. Clone the repo both this repo as well as the [Sales Report Application](https://github.com/Amy-Oji/kafka-reporder) 
repo and open both with IntelliJ IDE on seperate windows.

2. Create two databases on your Postgres

3. update [application.properies](https://github.com/Amy-Oji/sales-inventory-manager-REST-API-application/blob/master/src/main/resources/application.properties) file on both projects with your database details accordingly.

4. Open a terminal window and run this Kafka this command to start the ZooKeeper server process that Kafka uses for coordination and synchronization:

     ```zookeeper-server-start /opt/homebrew/etc/kafka/zookeeper.properties```

5. Open another teminal window and run this command to start the Kafka broker server process which allows producers and consumers to publish and consume messages to and from Kafka topics:

      ```kafka-server-start /opt/homebrew/etc/kafka/server.properties```

6. Run both projects. Run this one first.  

7. Use Postman to test the enpoints with this base URL for admin user http://localhost:8080/api/v1/admin/ 
*the endpoints include:*
*create-product,*
*update-product/{product-id},*
*get-all-available-products*

and this base URL for customer user http://localhost:8080/api/v1/customer/ with this enpoint 
order-products

For now, the application has only those end points. 

### Sample Request and Response For Each Enpoint

__create-product:__

Request Type: POST

URL => http://localhost:8080/api/v1/admin/create-product

RequestBody => {

    "name": "product 1",
    "price": 100.0,
    "description": "description for product one",
    "amountInStock": 100
    
}

*Returns the created product*

Response => {

    "id": 3,
    "name": "product 1",
    "price": 100.0,
    "description": "description",
    "amountInStock": 100,
    "available": true
    
}
__update-product:__

Request Type: POST

URL => http://localhost:8080/api/v1/admin/update-product/{product-id}

Requeest Body => {

    "id": 3,
    "name": "product 1",
    "price": 100.0,
    "description": "description",
    "amountInStock": 100,
    "available": true
    
}

*Returns the updated product*

Response => {

    "id": 1,
    "name": "product 4",
    "price": 100.0,
    "description": "description",
    "amountInStock": 200,
    "available": true
    
}

__get-all-available-products:__

Request Type: GET

URL => http://localhost:8080/api/v1/admin/get-all-available-products

*Returns an array of available roducts*

Response =>  [

    {
        "id": 1,
        "name": "product 124",
        "price": 100.0,
        "description": "description",
        "amountInStock": 100,
        "available": true
    },
    {
        "id": 2,
        "name": "product 12",
        "price": 100.0,
        "description": "description",
        "amountInStock": 100,
        "available": true
    },
    {
        "id": 3,
        "name": "product 1",
        "price": 100.0,
        "description": "description",
        "amountInStock": 100,
        "available": true
    }
    
]

__order-products__

Request Type: POST

URL => http://localhost:8080/api/v1/admin/get-all-available-products

*"products" is a map of products IDs as key, and requested quanytity as values.*

Requeest Body => {

    "customerName": "John Doe",
    "customerPhoneNum": "673ezff672a67r8",
    "products": {
        "1": 2,
        "2": 2
    }
}

*Returns details of the order which includes the sustomerOrderDTO that was used to make the request, 
as well as a productDetails map with product IDs as keys and unit price of the product as values, the sum of the order and the date.*

Response => {

    "customerOrderDTO": {
        "customerName": "John Doe",
        "customerPhoneNum": "673ezff672a67r8",
        "products": {
            "1": 2,
            "2": 2
        }
    },
    "productDetails": {
        "1": 100.0,
        "2": 100.0
    },
    "sum": 400.0,
    "order_date": "2023-03-17T07:28:25.317888"
    
}

__NOTE__ The place-order method stack plublishes the above response for ecah order to Kafka.


____
#### Controllers

The App currently make provision for only 2 types of users: admin and customer. 
Authentication has not been implemented yet so there is no logging in. 

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
I had no access to certain features like 'already-made JPA' queries.
I had to whip out my native SQL writing skills as a result. It was quite refreshing nonetheless.

