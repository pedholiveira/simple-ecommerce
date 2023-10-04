# Simple Ecommerce Application

## Overview

This is a simple e-commerce application that allows users to manage products, create orders, and track order items. It provides a set of REST APIs for Product, Order, and Order Item entities, making it easy to build and extend an e-commerce platform.

## Pre-requisites
- Java 17
- Docker
- Gradle

## Getting started

- Navigate to project directory
- Run `docker-compose up` to create the docker image and start the application containers.
- During the application startup the database migrations will be applied automatically.
- The application will be available on port 8081.

### API Endpoints

#### Product APIs:
- Create a new product: `POST: /api/products`
- Retrieve product details: `GET: /api/products/{productId}`
- Update product information: `PUT: /api/products/{productId}`
- Delete a product: `DELETE: /api/products/{productId}`
- List all products: `GET: /api/products`

#### Order APIs:
- Create a new order: `POST: /api/orders`
- Retrieve order details: `GET: /api/orders/{orderId}`
- Update order information: `PUT: /api/orders/{orderId}`
- Delete an order: `DELETE: /api/orders/{orderId}`
- List all orders: `GET: /api/orders`

#### Order Item APIs:
- Create a new order item: `POST: /api/order-items`
- Retrieve order item details: `GET: /api/order-items/{orderItemId}`
- Update order item information: `PUT: /api/order-items/{orderItemId}`
- Delete an order item: `DELETE: /api/order-items/{orderItemId}`
- List all order items for a specific order: `GET: /api/order-items`

> More details and examples can be found on API documentation page:
>
> http://localhost:8081/swagger-ui/index.html

## Dependencies

This application relies on several key dependencies to function effectively, such as:

- __Lombok__: [Lombok](https://projectlombok.org/) is used to reduce boilerplate code by automatically generating getters, setters, constructors, and other common Java code. It simplifies code development and improves readability.
- __MapStruct__: [MapStruct](https://mapstruct.org/) is a code generation library that simplifies the mapping between different Java bean types. It is particularly useful for transforming data between DTOs (Data Transfer Objects) and entity objects.
- __Flyway__: [Flyway](https://documentation.red-gate.com/fd/quickstart-how-flyway-works-184127223.html) is a database migration tool that simplifies database schema versioning and migration. It ensures that the database schema stays up-to-date with the application code, making it easier to manage database changes across different environments.
- __Spring Data__: [Spring Data](https://spring.io/projects/spring-data) is a part of the Spring Framework that provides data access abstractions and simplifies database operations. It allows for easy interaction with databases using Java objects and provides support for various data sources.

## TODO

Here are some items for future improvements:

- __Pagination__: Implement pagination for list results to improve performance when dealing with a large number of items.
- __Add Customer Information__: Enhance order details by including customer information such as name, shipping address, and contact details.