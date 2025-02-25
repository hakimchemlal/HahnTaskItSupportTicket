# IT Support Ticket System - Backend

This repository contains the backend implementation of an IT Support Ticket Management System. The frontend implementation can be found [here](URL_OF_YOUR_FRONTEND_REPO).

## Overview

This application provides a RESTful API for managing IT support tickets, allowing employees to report technical issues and IT support staff to track and resolve them.

## Features

- **Ticket Management:**
  - Create new tickets
  - Update ticket status
  - Add comments to tickets
  - View ticket details and history

- **Ticket Properties:**
  - Title
  - Description
  - Priority (Low, Medium, High)
  - Category (Network, Hardware, Software, Other)
  - Status (New, In Progress, Resolved)
  - Creation Date
  - Comments

- **User Roles:**
  - Employees: Create and view their tickets
  - IT Support: Full access to all tickets

## Technical Stack

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- RESTful API
- OpenAPI/Swagger documentation

## API Endpoints

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Installation

1. Clone the repository:
```bash
git clone https://github.com/your-username/it-support-backend.git
```

2. Navigate to the project directory:
```bash
cd it-support-backend
```

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Database

The application uses H2 in-memory database. The console is available at:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:ticketdb`
- Username: `sa`
- Password: `password`

## API Documentation

Once the application is running, you can access the API documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Frontend Application

The frontend implementation of this system is built with Java Swing and can be found in [this repository]([https://github.com/hakimchemlal/HahnTaskTicketSwing]).
