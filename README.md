# Parking System Management

A comprehensive parking lot management system built with Spring Boot, demonstrating design patterns, clean architecture, and RESTful API design.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture & Design](#architecture--design)
- [Design Patterns](#design-patterns)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Example API Calls](#example-api-calls)
- [Known Limitations](#known-limitations)
- [Future Enhancements](#future-enhancements)

## 🎯 Overview

This parking system manages vehicle registration, parking slot allocation, and pricing calculations. The system supports multiple vehicle types (Car, Motorcycle, Truck) and various parking slot types (Compact, Large, Motorcycle, Handicapped) with intelligent slot allocation and flexible pricing strategies.

### Key Features

- **Parking management**: Register and manage new parking lots, its levels
- **Checkin/checkout management**: Checkin a vehicle (get your ticket) and checkout (calculate your fee)
- **Slot Management**: Create and manage parking slots with type-specific rules
- **Pricing System**: Flexible pricing strategies (hourly, discount)
- **Authentication**: JWT-based security with role-based access control
- **RESTful API**: Comprehensive API with Swagger documentation
- **Real-time Allocation**: Slot allocation based on vehicle compatibility

### Assumptions

2. **Pricing Model**: Hourly-based pricing with different rates per vehicle type
3. **Slot Allocation**: First-come-first-served within compatible slot types
4. **Authentication**: Admin role required for administrative operations
5. **Data Persistence**: In-memory H2 database for development/testing purposes

## 🏗 Architecture & Design

The system follows a **clean, layered architecture** with clear separation of concerns:

```
src/main/java/com/parkingsystem/
├── config/              # Config classes (globalExceptionHandler, Swagger config, securityConfig)
├── controller/          # REST API endpoints
├── dto/                 # Data transfer objects
├── exceptions/          # Custom exceptions
├── factory/             # Factory pattern implementations
├── mapper/              # Mapping from models to dto (and vice versa)
├── model/               # Database entities
├── repository/          # Data access layer
├── security/            # Authentication & authorization
├── service/             # Business logic layer
├── strategy/            # Strategy pattern implementations
└── util/                # Additional util (init admin and user for authentication)
```

### Domain Models

#### Core Entities

- **Vehicle**: Represents a vehicle with type and license plate
- **ParkingSlot**: Represents a parking space with type and occupancy status
- **Level**: Represents a floor/level containing multiple parking slots
- **ParkingLot**: Represents the main parking facility
- **User**: Represents system users with roles
- **ParkingFee**: Represents a fee, which users get after checkout
- **ParkingTicket**: Represents a ticket, which users get after checkin

#### Relationships

| Entity | Relationship | Target Entity | Description |
|--------|--------------|---------------|-------------|
| `ParkingLot` | 1:N | `Level` | One parking lot contains multiple levels |
| `Level` | 1:N | `ParkingSlot` | One level contains multiple parking slots |
| `Vehicle` | 1:N | `ParkingTicket` | One vehicle can have multiple parking sessions |
| `ParkingTicket` | 1:1 | `ParkingSlot` | Each ticket is for one specific slot |
| `ParkingTicket` | 1:1 | `ParkingFee` | Each ticket has one associated fee |
| `ParkingSlot` | N:1 | `Level` | Multiple slots belong to one level |
| `Level` | N:1 | `ParkingLot` | Multiple levels belong to one parking lot |

#### Composite Keys
- **`LevelId`**: `(parking_lot_id, level_number)`
- **`ParkingSlotId`**: `(parking_lot_id, level_number, slot_number)`

- Composite keys used for `Level` and `ParkingSlot` entities

## 🎨 Design Patterns

### 1. Strategy Pattern (Pricing)

**Purpose**: Enables flexible pricing algorithms that can be changed at runtime.

```java
// Different pricing strategies
- HourlyPricingStrategy (default)
- WeekendDiscountPricingStrategy (promotions)
```

**Benefits**:
- Easy to add new pricing models
- Runtime strategy switching
- Follows Open/Closed Principle

### 2. Factory Pattern (Vehicle & Slot Creation)

**Purpose**: Encapsulates object creation with type-specific validation and business rules.

```java
// Vehicle factories
- CarFactory (6-8 character validation)
- MotorcycleFactory (4-7 character validation)
- TruckFactory (7-10 character validation)

// Slot factories (if using separate factories)
- CompactSlotFactory
- LargeSlotFactory
- MotorcycleSlotFactory
- HandicappedSlotFactory
```

**Benefits**:
- Centralized creation logic
- Type-specific validation
- Easy to extend with new types

### 3. Repository Pattern

**Purpose**: Abstracts data access logic and provides a consistent interface.

**Benefits**:
- Database independence
- Easy testing with mock repositories
- Consistent query interface

## 🛠 Technology Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.5.4
- **Build Tool**: Gradle
- **Database**: H2 (in-memory)
- **Security**: Spring Security with JWT
- **Documentation**: Swagger/OpenAPI 3
- **Testing**: JUnit 5, Spring Boot Test

## 🚀 Getting Started

### Prerequisites

- Java 21 
- Gradle 7.3.3 

### Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/NastiaFasova/parking-system.git
   cd parking-system
   ```

2. **Build the project**
   ```bash
   ./gradlew build
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

4. **Access the application**
    - API Base URL: `http://localhost:8080`
    - Swagger UI: `http://localhost:8080/swagger-ui.html`
    - H2 Console: `http://localhost:8080/h2-console`

### Default Configuration

```yaml
# H2 Database
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (empty)

# Default Admin User
Username: admin
Password: admin123
Role: ADMIN

# Default User
Username: user
Password: user123
Role: USER
```

## 📚 API Documentation

### Authentication

All API endpoints (except `/auth/**`) require JWT authentication.

#### Get JWT Token
```bash
POST /auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

### Main Endpoints

| Method | Endpoint                                          | Description                           | Role Required |
|--------|---------------------------------------------------|---------------------------------------|---------------|
| POST   | `/auth/login`                                     | Authenticate user                     | None          |
| POST   | `/auth/register`                                  | Register new user                     | None          |
| POST   | `/admin/`                                         | Create parking lot                    | ADMIN         |
| GET    | `/admin/{parking-id}/add-level`                   | Add level                             | ADMIN         |
| GET    | `/admin/{parking-id}/{level-id}/add-slot`         | Add slot                              | ADMIN         |
| PATCH  | `/admin/{parking-id}/{level-id}/{slot-id}/status` | Update slot status                    | ADMIN         |
| DELETE | `/admin/{parking-id}`                             | Delete parking lot                    | ADMIN         |
| DELETE | `/admin/{parking-id}/{level-id}`                  | Delete level of parking lot           | ADMIN         |
| DELETE | `/admin/{parking-id}/{level-id}/{slot-id}`        | Delete slot from level of parking lot | ADMIN         |
| GET    | `/api/{parking-id}/available-slots`               | Get available slots                   | ADMIN/USER    |
| POST   | `/api/check-in`                                   | Checking into parking lot             | USER          |
| POST   | `/api/check-out`                                  | Checking out of parking lot           | USER          |

## 🔧 Example API Calls

### 1. Authentication

**Login to get JWT token:**
```bash
curl -X 'POST' \
  'http://localhost:8080/auth/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "admin",
  "password": "admin123"
}'
```

**Response:**
```json
{
   "token": "eyJhbGciOiJIUzM4NCJ9.eyJyb2xlIjoiQURNSU4iLCJpZCI6MSwiZW1...."
}
```

### 2. Parking lot Management

**Add a parking lot:**
```bash
curl -X 'POST' \
  'http://localhost:8080/admin/?name=Parking%20%27Forest%27' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJyb2xlIjoiQURNSU4..."' \
  -d ''
```

**Response:**
```json
{
   "name": "Parking 'Forest'",
   "id": "1"
}
```

**Add a level into parking lot:**
```bash
curl -X 'GET' \
  'http://localhost:8080/admin/1/add-level' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...."'
```

**Response:**
```json
{
   "name": "Parking 'Forest'",
   "levels": 1
}
```

**Add a slot into level of parking lot:**
```bash
curl -X 'GET' \
  'http://localhost:8080/admin/1/1/add-slot?parkingSlotType=LARGE' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...."'
```

**Response:**
```json
{
   "parkingLotId": 1,
   "levelNumber": 1,
   "parkingSlots": [
      {
         "parkingSlotNumber": 1,
         "type": "LARGE",
         "occupied": false
      }
   ]
}
```

**Change a parking slot status availability:**
```bash
curl -X 'PATCH' \
  'http://localhost:8080/admin/1/1/1/status' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJyb..."' \
  -H 'Content-Type: application/json' \
  -d '{
  "occupied": true
}'
```

**Response:**
```json
{
   "parkingSlotNumber": 1,
   "type": "LARGE",
   "occupied": true
}
```

**Remove slot from level of a parking lot:**
```bash
curl -X 'DELETE' \
  'http://localhost:8080/admin/1/1/1' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.e..."'
```

**Response:**
```json
{
   "parkingLotId": 1,
   "levelNumber": 1,
   "parkingSlots": []
}
```

**Remove slot level from parking lot:**
```bash
curl -X 'DELETE' \
  'http://localhost:8080/admin/1/1' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9..."'
```

**Response:**
```json
{
   "name": "Parking 'Forest'",
   "levels": 0
}
```

**Remove a parking lot:**
```bash
curl -X 'DELETE' \
  'http://localhost:8080/admin/1' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...."'
```


**Response:**
```json
{
   "message": "Parking Lot was successfully removed"
}
```


### 3. Checking, checkout, check available slots

Be aware, that for check-in and check-out, you should login as USER (not ADMIN)

**Check-in:**
```bash
curl -X 'POST' \
  'http://localhost:8080/api/check-in' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...' \
  -H 'Content-Type: application/json' \
  -d '{
  "licencePlate": "1839781",
  "vehicleType": "CAR",
  "parkingLotId": 2
}'
```

**Response:**
```json
{
   "entryDate": "2025-08-22T15:02:38.301553",
   "parkingSlotNumber": 1,
   "levelNumber": 1,
   "parkingLotName": "Parking 'Forest'",
   "parkingSlotType": "LARGE",
   "licensePlate": "1839781",
   "vehicleType": "CAR"
}
```

**Response Failure Example:**
```json
{
   "status": 400,
   "error": "VALIDATION_ERROR",
   "message": "Invalid car license plate format",
   "timestamp": "2025-08-22T15:03:37.679242"
}
```

**Check-out:**
```bash
curl -X 'POST' \
  'http://localhost:8080/api/check-out' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.e..' \
  -H 'Content-Type: application/json' \
  -d '{
  "licensePlate": "1839781",
  "parkingLotId": 2
}'
```

**Response:**
```json
{
   "entryDate": "2025-08-22T15:02:38.301553",
   "exitDate": "2025-08-22T15:04:17.618682",
   "duration": "0h 1m 99s",
   "fee": 2
}
```

**Response Failure example:**
```json
{
   "status": 404,
   "error": "ENTITY_NOT_FOUND",
   "message": "Vehicle is not found: 18397d1",
   "timestamp": "2025-08-22T15:05:45.052231"
}
```

**Get list of available slots:**
```bash
curl -X 'GET' \
  'http://localhost:8080/api/2/available-slots' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9....'
```

**Response:**
```json
[
   {
      "levelId": 1,
      "parkingSlotNumber": 2,
      "type": "MOTORCYCLE",
      "occupied": false
   },
   {
      "levelId": 2,
      "parkingSlotNumber": 1,
      "type": "COMPACT",
      "occupied": false
   },
   {
      "levelId": 2,
      "parkingSlotNumber": 2,
      "type": "LARGE",
      "occupied": false
   },
   {
      "levelId": 2,
      "parkingSlotNumber": 3,
      "type": "MOTORCYCLE",
      "occupied": false
   }
]
```

**Response Failure example:**
```json
{
   "status": 404,
   "error": "ENTITY_NOT_FOUND",
   "message": "ParkingLot was not found by id: 3",
   "timestamp": "2025-08-22T15:06:59.082073"
}
```

### Sample Data

The application initializes with:
- 1 Parking Lot with 2 Levels
- Various parking slots on each level
- Admin user for testing

## ⚠ Known Limitations

### Current Limitations

1. **In-Memory Database**: Data is lost on application restart
2. **Basic Slot Allocation**: No advanced algorithms (distance optimization, etc.)
3. **No Real-time Updates**: No WebSocket support for real-time slot availability
4. **Basic Security**: JWT without refresh tokens or advanced security features

### Validation Constraints

- **Car License Plates**: 6-8 alphanumeric characters
- **Motorcycle License Plates**: 4-7 alphanumeric characters
- **Truck License Plates**: 7-10 alphanumeric characters
- **Slot Types**: Fixed set of COMPACT, LARGE, MOTORCYCLE, HANDICAPPED

## 🚧 Future Enhancements

### Short-term TODOs

- [ ] Add slot reservation functionality
- [ ] Enhanced error handling and logging

### Medium-term Enhancements

- [ ] Real-time slot availability with WebSockets
- [ ] Advanced slot allocation algorithms
- [ ] Reporting and analytics dashboard

### Long-term Vision

- [ ] External database integration (PostgreSQL/MySQL)
- [ ] Message queue integration
- [ ] Advanced monitoring and observability

## 🧪 Testing

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests VehicleFactoryTest

# Generate test coverage report
./gradlew jacocoTestReport
```

### Test Categories

- **Unit Tests**: Service layer, factory classes, utilities
- **Integration Tests**: Repository layer, full API workflows
- **Security Tests**: Authentication, authorization
- **Validation Tests**: Input validation, error handling