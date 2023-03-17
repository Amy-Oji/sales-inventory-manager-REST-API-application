# sales-inventory-manager-REST-API-application
REST-API application for managing sales/inventory built with SpringBoot

The application supports the following operations:

* Create a new product

* Update an existing product.

* Get a list of all available products.

* Place an Order based on availability of product.

* Save customer details


The app also publishes the basic detail of the craeted Orders to Kafka for reporting. Thsi resport is consumed my the [Sales Report Application](https://github.com/Amy-Oji/kafka-reporder)


