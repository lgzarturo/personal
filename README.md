# REST API for Professional Projects in Spring Boot 3

This is a REST API for Professional Project in Spring Boot 3.

At this moment, the API has the following endpoints:

- It is a simple API that allows you to log in with user credentials.
- The example user is generated in the database when the application starts.
- Endpoint `/api/v1/auth/login` to log in with user credentials.
- Endpoint `/api/v1/web/ping` to check if the API is up and running.
- Ticket endpoint `/api/v1/tickets` to create a ticket.
- The Application is secured with JWT authentication.
- The API is secured with Spring Security.
- Application profiles for development, stage, test and production.
- The API is documented in `docs` folder with `*.http` files.

> **Objective**: Practicing the skills that I've learned about Spring Boot 3 and REST API for Portfolio Project.

## Methodology

- Test Driven Development (TDD)
- Clean Architecture (Hexagonal Architecture)
- Domain Driven Design (DDD)
- SOLID Principles
- Design Patterns
- Clean Code

## Project Structure

The project is structured in the following way:
```
├── com.lgzarturo.api.personal
│   │   # API layer (REST controllers and services)
│   ├── api
│   │   ├── auth
│   │   │   ├── dto
│   │   │   │   ├── LoginRequest.java
│   │   │   │   └── LoginResponse.java
│   │   │   ├── AuthController.java
│   │   │   └── AuthService.java
│   │   ├── user
│   │   │   ├── dto
│   │   │   │   ├── CreateUserRequest.java
│   │   │   │   ├── CreateUserResponse.java
│   │   │   │   ├── GetUserResponse.java
│   │   │   │   ├── UpdateUserRequest.java
│   │   │   │   └── UpdateUserResponse.java
│   │   │   ├── mapper
│   │   │   │   └── UserMapper.java
│   │   │   ├── UserController.java
│   │   │   └── UserService.java
│   │   │   # Generic layer (Generic classes, interfaces, etc.)
│   │   ├── generic
│   │   │   └── CrudService.java
│   │   │   # Web layer (REST public controllers) 
│   │   └── web
│   │       ├── PingController.java
│   │       └── PingService.java
│   │   # Configuration layer (Spring configuration)
│   ├── config
│   │   ├── AdminUser.java
│   │   ├── AppConfigProperties.java
│   │   └── Bootstrap.java
│   │   # Exception layer (Custom exceptions) 
│   ├── exceptions                          
│   │   ├── ApiError.java
│   │   ├── AuthExceptionHandler.java
│   │   ├── ExceptionHandlerAdvice.java
│   │   ├── ResourceDuplicatedException.java                    # 409 - Conflict
│   │   ├── ResourceNotFoundException.java                      # 404 - Not Found
│   │   └── ResourceValidationException.java                    # 400 - Bad Request
│   │   # Infrastructure layer (Database, external services, etc.)
│   ├── infrastructure
│   │   │   # Client layer (External services) REST clients
│   │   ├── client
│   │   │   └── jsonplaceholder
│   │   │       ├── JsonPlaceholderClient.java
│   │   │       └── Post.java
│   │   │   # Security layer (Authentication, authorization, etc.)
│   │   ├── security
│   │   │   ├── jwt
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── JwtGenerator.java
│   │   │   ├── CorsFilter.java
│   │   │   ├── SecurityChainFilter.java
│   │   │   └── SecurityConfig.java
│   │   │   # Storage layer (Database, file system, etc.)
│   │   └── storage
│   │       └── s3
│   │   # Shared layer (Utilities, helpers functions, constants, etc.)
│   ├── utils
│   │   ├── Helpers.java
│   │   └── Constants.java
```

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
- Java 17
- IntelliJ IDEA
- Docker
- Postman
- Git
- Spring Boot 3
- PostgreSQL
- Maven
```

### Installing

A step by step series of examples that tell you how to get a development env running

Download the project from GitHub and open it in IntelliJ IDEA or your favorite IDE.

## Running the application locally

You need to follow these steps to run the application locally:

- Edit the `.sample-env` file to change the database connection settings and rename it to `.env`, docker uses this file-compose to set the environment variables for the database container.
- Create file `secrets.properties` in `src/main/resources` with the database credentials, the application uses this file to connect to the database. The file should look like this:

```
spring.datasource.username=[username]
spring.datasource.password=[password]
spring.datasource.url=[url to database]
```

> Note: The `secrets.properties` and `.env` files are ignored by git, so you don't have to worry about accidentally committing your credentials.

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the `com.lgzarturo.api.personal.PersonalApplication` class from your IDE.

Alternatively, you can use the Spring Boot Maven plugin like so:

```
mvn spring-boot:run
```

## Docker

Execute the following commands to run the application in a Docker container:

Build the image and run the container:

```bash
docker-compose up -d
```

Show the containers that are running:

```bash
docker compose ps
```

Show the logs of the container:

```bash
docker logs postgres -f
```

Connect to a container with bash:

```bash
 docker exec -it postgres bash
```

Inside docker, you need to connect to postgresql

```bash
 psql -U personal_user
```

If you want to create the database, you can do it like this:

```postgresql
CREATE DATABASE personal_db;
```

## Running the tests

Explain how to run the automated tests for this system

### Automated tests

For running the automated tests, you need to execute the following command:

```bash
mvn test
```

### Manual tests

For running the manual tests, you need to create a test environment in the `docs` folder.

- Create a file called `http-client.env.json` in the `docs` folder with the following content:
  
```json
{
  "dev": {
    "username": "[email from admin user]",
    "ticketId": "[ticket id]"
  }
}
```

- Create a file called `http-client.private.env.json` in `docs` folder with the following content:

```json
{
  "dev": {
    "token": "[token from login request]"
  }
}
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - Used to persist data in a relational database
* [Spring Data REST](https://spring.io/projects/spring-data-rest) - Used to expose JPA repositories as REST endpoints
* [Spring Security](https://spring.io/projects/spring-security) - Used to secure the REST API
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html) - Used to speed up development
* [Spring Boot Configuration Processor](https://docs.spring.io/spring-boot/docs/current/reference/html/configuration-metadata.html) - Used to generate metadata for IDEs
* [Spring Boot Starter Test](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html) - Used to test the REST API
* [Spring Boot Starter Web](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-developing-web-applications) - Used to create web applications
* [Faker Java](https://github.com/DiUS/java-faker) - Used to generate fake data for testing purposes.
* [Lombok](https://projectlombok.org/) - Used to reduce boilerplate code.
* [MapStruct](https://mapstruct.org/) - Used to generate mapper implementations.
* [PostgreSQL](https://www.postgresql.org/) - Used as a relational database.
* [Docker](https://www.docker.com/) - Used to containerize the application.
* [Docker Compose](https://docs.docker.com/compose/) - Used to define and run multi-container Docker applications.
