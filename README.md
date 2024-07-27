# Attractions Service Application

This is a REST service application for managing data about attractions, built with Spring Boot, Hibernate, PostgreSQL, and Liquibase. The application supports operations for managing cities, attractions, and services.

## Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher
- Docker and Docker Compose
- PostgreSQL

## Setup Instructions

### 1. Clone the repository
`https://github.com/pp8a/attractions-service.git cd attractions-service` 
### 2. Build the project
`mvn clean install` 
### 3. Configure the database
Update the src/main/resources/application.yml file with your PostgreSQL database credentials:</br>
`spring:`</br>
`  datasource:`</br>
`    url: jdbc:postgresql://localhost:5432/your_database`</br>
`    username: your_username`</br>
`    password: your_password`</br>
`  jpa:`</br>
`    hibernate:`</br>
`      ddl-auto: update`</br>
`    show-sql: true`</br>
`    database-platform: org.hibernate.dialect.PostgreSQLDialect`
### 4. Run the application with Docker Compose
Ensure Docker and Docker Compose are installed and running. From the project root directory, run: 
`docker-compose up --build`</br>
This will start the PostgreSQL container and the Spring Boot application container.
### 5. Access the application
The application will be available at `http://localhost:8080`. 
### API Endpoints
#### City Endpoints

*   POST /cities: Create a new city
*   GET /cities: Retrieve all cities
*   GET /cities/{cityId}: Retrieve a city by ID
*   PUT /cities/{cityId}: Update city population and metro status
*   DELETE /cities/{cityId}: Delete a city

#### Attraction Endpoints

*   POST /attractions: Create a new attraction
*   GET /attractions: Retrieve all attractions
*   GET /attractions/{attractionId}: Retrieve an attraction by ID
*   GET /attractions/city/{cityId}: Retrieve all attractions by city ID
*   PUT /attractions/{attractionId}: Update attraction description
*   DELETE /attractions/{attractionId}: Delete an attraction

### Service Endpoints

*   POST /services: Create a new service
*   GET /services: Retrieve all services
*   GET /services/{serviceId}: Retrieve a service by ID
*   DELETE /services/{serviceId}: Delete a service

### Running Tests
#### Unit and Integration Tests 
This will run all unit and integration tests `mvn test`.
#### Additional Information

*   Liquibase: The application uses Liquibase for database migrations. Migrations are defined in the `src/main/resources/db/changelog directory`.
*   Logging: The application uses SLF4J with Logback for logging. Configuration can be found in `src/main/resources/logback.xml`.

### Authors
Aleksandr Mikhalchuk - Initial work - [https://github.com/pp8a/](pp8a)