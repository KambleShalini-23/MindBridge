# MindBridge

Mental Health Companion Platform built using Spring Boot, PostgreSQL, JWT Authentication, Refresh Tokens, and Role-Based Access Control (RBAC).

---

# Tech Stack

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* Docker
* JWT
* Lombok

---

# Local Setup

## 1. Start PostgreSQL

docker compose up -d

---

## 2. Run Backend

Using IntelliJ
OR
./mvnw spring-boot:run

---

## 3. Health Check

GET [http://localhost:8080/health](http://localhost:8080/health)

Expected Response:

MindBridge backend is running

---

# Project Structure

src/main/java/com/project/mindbridge

common
config
  user
    ├── controller
    ├── dto
    ├── entity
    ├── repository
    └── service

---

# Authentication Architecture

## Register Flow

Client
→ UserController
→ UserService
→ Password hashing
→ UserRepository
→ PostgreSQL

---

## Login Flow

Client login request
→ Validate email/password
→ Generate access token
→ Generate refresh token
→ Save refresh token in DB
→ Return tokens

---

## Protected Request Flow

Request
→ JwtAuthFilter
→ Extract token
→ Validate token
→ Create Authentication object
→ SecurityContext
→ Role check
→ Controller

---

## Refresh Token Flow

Expired access token
→ /users/refresh
→ Validate refresh token
→ Generate new access token
→ Return new access token

---

## Logout Flow

/users/logout
→ Delete refresh token from DB
→ Session revoked

---

# API Endpoints

## Register User

POST /users/createUser

Request Body:

{
"emailId": "[test@gmail.com](mailto:test@gmail.com)",
"password": "1234"
}

---

## Login

POST /users/login

Request Body:

{
"emailId": "[test@gmail.com](mailto:test@gmail.com)",
"password": "1234"
}

Response:

{
"token": "access-token",
"refreshToken": "refresh-token"
}

---

## Protected Endpoint

GET /patient

Authorization Header:

Bearer <access-token>

---

## Refresh Token

POST /users/refresh

Request Body:

{
"refreshToken": "refresh-token"
}

Response:

{
"token": "new-access-token",
"refreshToken": "same-refresh-token"
}

---

## Logout

POST /users/logout

Request Body:

{
"refreshToken": "refresh-token"
}

---

## Health Check

GET /health

---

# Security Features Implemented

* Password hashing using PasswordEncoder
* JWT-based stateless authentication
* Refresh token session management
* Role-Based Access Control (RBAC)
* JWT Authentication Filter
* SecurityContext authentication handling
* Protected endpoints
* Access token expiry handling
* Refresh token expiry validation
* Logout/session revocation

---

# RBAC Rules

.requestMatchers("/admin").hasRole("ADMIN")

.requestMatchers("/patient").hasRole("PATIENT")

Roles:

* PATIENT
* ADMIN
* THERAPIST

---

# Current Completed Features

* Spring Boot backend setup
* PostgreSQL with Docker
* User registration
* Login system
* Password hashing
* JWT access token generation
* JWT validation
* JwtAuthFilter
* SecurityContext integration
* RBAC implementation
* Refresh token storage
* Refresh endpoint
* Logout endpoint
* Health endpoint
* Global exception handling

---

# Pending Features

* React frontend
* OAuth2 Google Login
* PostgreSQL Row-Level Security (RLS)
* Integration tests
* Request logging
* Mood logging module
* Therapist dashboard
* Journal entries
* Session scheduling

---

# Important Concepts Learned

## Stateless Authentication

Server does not remember user sessions.

Every request must send:

Authorization: Bearer <token>

---

## Authentication vs Authorization

Authentication:
Who are you?

Authorization:
What are you allowed to access?

---

## Access Token vs Refresh Token

Access Token:

* Short-lived
* Used for protected APIs

Refresh Token:

* Long-lived
* Stored in DB
* Used to generate new access tokens

---

# Future Improvements

* Access token blacklist
* Multi-device session management
* Redis token storage
* OAuth2 social login
* API rate limiting
* AWS deployment
* CI/CD pipeline
