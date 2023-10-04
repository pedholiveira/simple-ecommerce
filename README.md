# Simple Ecommerce Application

## Overview

This is a simple e-commerce application that allows users to manage products, create orders, and track order items. It provides a set of REST APIs for Product, Order, and Order Item entities, making it easy to build and extend an e-commerce platform.

## Pre-requisites
- Java 17
- Docker
- Gradle

## Getting started

- Navigate to project directory
- Run `./gradlew bootJar` to build the application and generate the jar file.
- Run `docker-compose up --build` to start the application containers.
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


## TODO

Here are some items for future improvements:

- __Pagination__: Implement pagination for list results to improve performance when dealing with a large number of items.
- __Add Customer Information__: Enhance order details by including customer information such as name, shipping address, and contact details.