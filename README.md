# Spring JWT Authentication App 🔐

This project is a demo Spring Boot application that implements JWT-based authentication and authorization. It uses Spring Security, JPA (Hibernate), and MySQL, and provides endpoints for user registration, authentication, token refresh, and role-based access control.

## Features ✅

- User registration and authentication with JWT
- Access and refresh token management
- Role-based authorization (USER, ADMIN)
- Secure password storage with BCrypt
- CORS configuration for frontend integration
- RESTful API endpoints

## Project Structure 📂

- `src/main/java/dev/jhon0206/spring_jwt/`  
  Main Java source code (entities, services, controllers, config)
- `src/main/resources/`  
  Application properties and static resources (empty for now)
- `src/test/java/dev/jhon0206/spring_jwt/`  
  Test classes (empty for now)

## Requirements 💻

- Java 17+
- Maven
- MySQL

## Getting Started

### 1. Clone the repository

```sh
git clone https://github.com/your-username/spring-jwt.git
cd spring-jwt
```

### 2. Configure the Database

Edit [`src/main/resources/application.properties`](src/main/resources/application.properties) to match your MySQL setup:

```
spring.datasource.url=jdbc:mysql://localhost:3306/bd_jwt?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=admin
```

### 3. Build and Run

```sh
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080/api/v1`.

# API Endpoints 🌐
Authentication

``POST /api/v1/auth/register``
- Register a new user
- **Body**:

```json
{
  "name": "John",
  "lastname": "Doe",
  "email": "john@example.com",
  "password": "password"
}
```

``POST /api/v1/auth/authenticate``
- Authenticate and receive JWT token
- **Body**:

```json
{
  "email": "john@example.com",
  "password": "password"
}
```

``POST /api/v1/auth/refresh-token``
- Refresh access token using a refresh token
- **Body**:

```json
{
  "refreshToken": "<refresh_token>"
}
```

### Demo Endpoints
``GET /api/v1/demo/user``
Accessible by users with USER or ADMIN role

``GET /api/v1/demo/admin``
Accessible by users with ADMIN role

``GET /api/v1/demo/admin/get-tokens/{id}``
Get tokens for a user (ADMIN only)

### Info
``GET /api/v1/info``
Public endpoint for application info (preview)

## Default Admin User

On first run, a default admin user is created:

- **Email:** `admin@example.com`
- **Password:** `password`

# Configuration 🛠️

JWT and other settings are configured in [`application.properties`](src/main/resources/application.properties):

- `application.jwt.secret-key`
- `application.jwt.token-prefix`
- `application.jwt.token-expiration-after-days`
- `application.jwt.refresh-token-expiration-after-days`