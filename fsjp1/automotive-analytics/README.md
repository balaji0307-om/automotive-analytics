# Automotive Data Analytics Platform

> A Spring Boot web application for monitoring and analyzing automotive vehicle data.  
> Built as a college internship project.

---

## Project Overview

The **Automotive Data Analytics Platform** is a full-stack web application that allows users to manage vehicle records and analyze data related to fuel consumption, engine behaviour, and environmental impact.

---

## Features

- **Login System** – Spring Security with BCrypt password encoding, two roles: ADMIN and USER
- **Dashboard** – Summary cards for total vehicles, averages, reports, CO₂ emissions
- **Vehicle Management** – Full CRUD (Add, Edit, View, Delete) with validation
- **Analytics** – Fuel consumption, engine behaviour, and environmental impact analysis
- **Search** – Filter vehicles by keyword, manufacturer, fuel type, or year
- **Reports** – Generate FUEL, ENGINE, and ENVIRONMENTAL reports per vehicle
- **REST API** – JSON endpoints for all CRUD operations
- **Role-Based Access** – ADMIN can add/edit/delete; USER can view only
- **Global Exception Handling** – Friendly error pages (403, 404, 500)

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.2.5 |
| MVC | Spring MVC + Thymeleaf |
| ORM | Spring Data JPA + Hibernate |
| Security | Spring Security 6 |
| Database | PostgreSQL |
| Frontend | HTML, CSS, Vanilla JavaScript |
| Build Tool | Maven |
| IDE | Eclipse (Maven project) |

---

## Prerequisites

- Java 21 or later
- Maven 3.8+
- PostgreSQL 14+ installed and running
- Eclipse IDE (with Maven support)

---

## Database Setup

1. Open pgAdmin or psql and create the database:

```sql
CREATE DATABASE automotive_db;
```

2. Default PostgreSQL credentials in `application.properties`:
   - Host: `localhost`
   - Port: `5432`
   - Database: `automotive_db`
   - Username: `postgres`
   - Password: `postgres`

   > Change these in `src/main/resources/application.properties` if your setup is different.

3. Hibernate will **automatically create all tables** on first startup (`ddl-auto=update`).

4. Sample data (10 vehicles, 2 users, 2 roles) is **automatically seeded** on first startup via `DataInitializer.java`.

---

## How to Run

### Option 1: Eclipse IDE

1. Open Eclipse → `File → Import → Existing Maven Projects`
2. Browse to the `automotive-analytics` folder
3. Click `Finish` to import
4. Right-click `AutomotiveAnalyticsApplication.java` → `Run As → Spring Boot App`
5. Open browser: `http://localhost:8080`

### Option 2: Command Line (Maven)

```bash
cd automotive-analytics
mvn clean install
mvn spring-boot:run
```

---

## Default Login Credentials

| Role | Username | Password |
|------|----------|----------|
| Admin | `admin` | `admin123` |
| User | `user` | `user123` |

---

## Application URLs

| URL | Description |
|-----|-------------|
| `/login` | Login page |
| `/dashboard` | Main dashboard |
| `/vehicles` | Vehicle list |
| `/vehicles/new` | Add vehicle (ADMIN) |
| `/vehicles/edit/{id}` | Edit vehicle (ADMIN) |
| `/vehicles/view/{id}` | View vehicle details |
| `/vehicles/delete/{id}` | Delete vehicle (ADMIN) |
| `/analytics/fuel` | Fuel consumption analysis |
| `/analytics/engine` | Engine behaviour analysis |
| `/analytics/environmental` | Environmental impact analysis |
| `/reports` | Reports list |
| `/reports/generate` | Generate a report |
| `/reports/view/{id}` | View report details |
| `/search` | Search vehicles |
| `/logout` | Logout |

---

## REST API Endpoints

| Method | URL | Description | Role |
|--------|-----|-------------|------|
| GET | `/api/vehicles` | List all vehicles | Any |
| GET | `/api/vehicles/{id}` | Get vehicle by ID | Any |
| GET | `/api/vehicles/search?keyword=xxx` | Search vehicles | Any |
| POST | `/api/vehicles/add` | Add a vehicle | ADMIN |
| PUT | `/api/vehicles/update/{id}` | Update a vehicle | ADMIN |
| DELETE | `/api/vehicles/delete/{id}` | Delete a vehicle | ADMIN |

All API endpoints return/accept **JSON**.  
Authentication required for all endpoints (HTTP Basic or session cookie).

---

## Project Folder Structure

```
automotive-analytics/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/automotive/
    │   │   ├── AutomotiveAnalyticsApplication.java
    │   │   ├── config/
    │   │   │   ├── DataInitializer.java
    │   │   │   └── SecurityConfig.java
    │   │   ├── controller/
    │   │   │   ├── AuthController.java
    │   │   │   ├── AnalyticsController.java
    │   │   │   ├── DashboardController.java
    │   │   │   ├── ReportController.java
    │   │   │   ├── SearchController.java
    │   │   │   ├── VehicleController.java
    │   │   │   └── VehicleRestController.java
    │   │   ├── dto/
    │   │   │   ├── AnalyticsDto.java
    │   │   │   ├── DashboardStatsDto.java
    │   │   │   ├── ReportDto.java
    │   │   │   └── VehicleDto.java
    │   │   ├── entity/
    │   │   │   ├── Report.java
    │   │   │   ├── Role.java
    │   │   │   ├── User.java
    │   │   │   └── Vehicle.java
    │   │   ├── exception/
    │   │   │   ├── DuplicateResourceException.java
    │   │   │   ├── GlobalExceptionHandler.java
    │   │   │   └── ResourceNotFoundException.java
    │   │   ├── repository/
    │   │   │   ├── ReportRepository.java
    │   │   │   ├── RoleRepository.java
    │   │   │   ├── UserRepository.java
    │   │   │   └── VehicleRepository.java
    │   │   ├── security/
    │   │   │   └── CustomUserDetailsService.java
    │   │   ├── service/
    │   │   │   ├── AnalyticsService.java
    │   │   │   ├── AnalyticsServiceImpl.java
    │   │   │   ├── DashboardService.java
    │   │   │   ├── DashboardServiceImpl.java
    │   │   │   ├── ReportService.java
    │   │   │   ├── ReportServiceImpl.java
    │   │   │   ├── VehicleService.java
    │   │   │   └── VehicleServiceImpl.java
    │   │   └── util/
    │   │       └── VehicleMapper.java
    │   └── resources/
    │       ├── application.properties
    │       ├── data.sql
    │       ├── static/
    │       │   ├── css/style.css
    │       │   └── js/
    │       │       ├── main.js
    │       │       └── validation.js
    │       └── templates/
    │           ├── login.html
    │           ├── dashboard.html
    │           ├── search.html
    │           ├── analytics/
    │           │   ├── engine.html
    │           │   ├── environmental.html
    │           │   └── fuel.html
    │           ├── error/
    │           │   ├── 403.html
    │           │   ├── 404.html
    │           │   ├── 500.html
    │           │   └── error.html
    │           ├── reports/
    │           │   ├── generate.html
    │           │   ├── list.html
    │           │   └── view.html
    │           └── vehicles/
    │               ├── form.html
    │               ├── list.html
    │               └── view.html
    └── test/
        └── java/com/automotive/
            └── AutomotiveAnalyticsApplicationTests.java
```

---

## Future Improvements

1. **Charts and Graphs** – Integrate Chart.js for visual analytics
2. **Export Reports** – Export as PDF or Excel using Apache POI or iText
3. **Pagination** – Add pagination for large vehicle datasets
4. **User Registration** – Allow admin to register new users from UI
5. **Email Alerts** – Notify owner when engine temperature is critical
6. **Scheduled Reports** – Auto-generate weekly reports using Spring Scheduler
7. **Audit Logging** – Track who made changes and when
8. **REST API Security** – Add JWT token-based authentication for API endpoints
9. **Unit Tests** – Add JUnit tests for service layer
10. **Docker Support** – Containerize the application with Docker Compose

---

## Author

Developed as a college internship project.  
Technology: Java 21, Spring Boot 3.2.5, PostgreSQL, Thymeleaf.
