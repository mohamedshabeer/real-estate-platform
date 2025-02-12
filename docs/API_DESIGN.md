 
# API Design Document

## Overview
The **Real Estate Platform API** provides endpoints to manage real estate properties. 
Follows **RESTful principles** and supports simple **CRUD operations**.

---

## Base URL
```
http://localhost:8080/api
```
(For production, replace with the your own cloud-hosted URL.)

---

## Authentication
- TODO perhaps using **JWT tokens**.

---

## Endpoints

### 1️⃣ **Get All Properties**
#### Request:
```http
GET /properties
```
#### Response:
```json
[
  {
    "id": 1,
    "title": "Luxury Villa",
    "location": "Berlin, Germany",
    "price": 1500000,
    "available": true
  }
]
```

---

### 2️⃣ **Get Property by ID**
#### Request:
```http
GET /properties/{id}
```
#### Response:
```json
{
  "id": 1,
  "title": "Luxury Villa",
  "location": "Berlin, Germany",
  "price": 1500000,
  "available": true
}
```

---

### 3️⃣ **Create Property**
#### Request:
```http
POST /properties
Content-Type: application/json
```
#### Body:
```json
{
  "title": "Luxury Apartment",
  "location": "Munich, Germany",
  "price": 1200000,
  "available": true
}
```
#### Response:
```json
{
  "id": 2,
  "message": "Property created successfully"
}
```

---

### 4️⃣ **Update Property**
#### Request:
```http
PUT /properties/{id}
Content-Type: application/json
```
#### Body:
```json
{
  "title": "Updated Villa",
  "price": 1400000
}
```
#### Response:
```json
{
  "message": "Property updated successfully"
}
```

---

### 5️⃣ **Delete Property**
#### Request:
```http
DELETE /properties/{id}
```
#### Response:
```json
{
  "message": "Property deleted successfully"
}
```

---

## Error Handling
Responses follow a standard error structure:
```json
{
  "timestamp": "2025-02-12T12:34:56Z",
  "status": 404,
  "error": "Not Found",
  "message": "Property not found",
  "path": "/properties/999"
}
```

---

## Rate Limiting
- **50 requests per minute** per user.
- Exceeding the limit results in:
```json
{
  "message": "Too many requests, please try again later."
}
```

---

## API Security
- TODO **JWT Authentication**.
- TODO All sensitive data must be **encrypted**.
- TODO Implements **role-based access control** (RBAC).

---

## Future Enhancements
- Add **GraphQL Support**.
- Implement **WebSockets for real-time updates**.


