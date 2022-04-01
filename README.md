# Development Book Kata API

This is a proposed implementation of the Development Books API kata using Test Driven Development (TDD) with Spring Boot
and JUnit.

Complete Description of this kata can be found
here: https://github.com/stephane-genicot/katas/blob/master/DevelopmentBooks.md

# Stack

- Java 11
- Spring Boot 2.5.11
- Spring Data JPA
- H2 in-memory database
- JUnit 5
- Mockito
- Lombok
- Swagger/OpenApi 3.0
- Maven

# How to Run It

This application will be packaged as a jar which have Tomcat embedded, you can follow these steps to run it:

1. Clone this repository using HTTPS:

        git clone https://github.com/2022-DEV1-017/DevelopmentBooks.git

   Or using SSH :

        git clone git@github.com:2022-DEV1-017/DevelopmentBooks.git


2. Make sure you have JDK 11 and maven installed
3. Build and package the application using the following maven command:

        mvn clean install

   Or you can build and package it without running tests using the following command:

        mvn clean install -DskipTests

4. Run the application using maven with the following command:

        mvn spring-boot:run

   Alternatively, you can move to ` ./target ` and run the app if it's already packaged using:

        java -jar development-books-0.0.1-SNAPSHOT.jar

   Or you can run it with your preferred IDE executing the main method of **DevelopmentBooksApplication.class**.

The app will start running at http://localhost:8080.

# Explore Application APIs

All APIs are "self-documented" by OpenApi 3.0 using annotations. To view the API docs, run the application and browse
to: http://localhost:8080/swagger-ui.html . You can test them directly from this Swagger UI or using any other rest
client.

This application defines the following endpoints:

- Calculate the cart's total price :

        POST /api/books-cart/calculate-price
        
        {
            "1": 1,
            "2": 1,
            "3": 1
        }

**Note:** The detail of the bill will be found in the response of the api call and in the console/Terminal or IDE
console.

# Run Tests

You can run tests using one of the following options:

- Using the maven command:

        mvn test
- Using your preferred IDE: running all tests or one by one.
