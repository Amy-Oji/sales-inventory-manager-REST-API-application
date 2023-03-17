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

This resport is later consumed my the [Sales Report Application](https://github.com/Amy-Oji/kafka-reporder)

____
#### Controllers

The App currently make provision for only 2 types of users: admin and customer. 
Authentication has not been implemented yet so there is no logging in. 
The admin endpoint include:

create-product

update-product/{product-id}

get-all-available-products

while the customers are meant to use only. 

order-products.

To create an order, pass in a json pasyload like:

  "customerName": "John Doe",
    "customerPhoneNum": "673ezff672a67r8",
    "products": {
        "1": 2,
        "2": 2
    }
The products field is a map of order ids and quantity ordered.
When you pass in the pay payload on the place order endpoint:
http://localhost:8080/api/v1/customer/order-products

If there are no errors, it returns this payload:

{
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

The returns back the customerOrderDTO payload and in addition, productDetails map of product Ids as keys and thier unit prices as values. 
Then it reurns the sum of the order and the date of the order. 

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

