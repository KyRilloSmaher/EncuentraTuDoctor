# Encuentra Tu Doctor

Encuentra Tu Doctor is a Java-based desktop application designed to simplify and enhance the process of finding and booking appointments with medical practitioners. Built using Java Swing for the graphical user interface (GUI), this project implements the MVC (Model-View-Controller) architecture and incorporates the Repository Pattern to manage database operations cleanly. The application stores and retrieves data using Microsoft SQL Server, offering patients a seamless and efficient user experience. This project is intentionally built without using any external frameworks (like Spring or Hibernate) to demonstrate core Java programming concepts and database management skills.

## Project Objective

The goal of this project is to:

- Provide a user-friendly interface that enables patients to browse and filter available practitioners
- Streamline the process of booking medical appointments
- Enhance accessibility to healthcare services through smart search and filtering mechanisms
- Implement comprehensive rating and review system for both practitioners and clinics
- Apply software engineering principles including code organization, modularity, and separation of concerns
- Implement Java concepts learned throughout the course in a practical and useful application
- Demonstrate proficiency in core Java programming without relying on external frameworks
- Showcase direct database management skills using JDBC and SQL

## Project Structure

- **Programming Language**: Java (core Java only, no external frameworks like Spring)
- **GUI Library**: Java Swing
- **Database**: Microsoft SQL Server
- **Architecture**: MVC (Model-View-Controller) Design
- **Data Access Pattern**: Repository Pattern
- **Persistence**: Data is stored and retrieved using direct JDBC connections
- **Code Quality**: Clean, organized, and maintainable codebase without relying on frameworks

## Features

### User Management
- Patient registration and profile management
- Practitioner registration and profile management
- Secure login system with role-based access

### Appointment System
- Browse available practitioners and clinics
- View detailed practitioner profiles including:
  - Specialization
  - Experience
  - Ratings
  - Available clinics
  - Schedule information
- Book, modify, and cancel appointments
- View appointment history

### Rating and Review System
- Rate practitioners based on:
  - Overall experience
  - Waiting time
  - Additional comments
- Rate clinics based on:
  - Overall experience
  - Waiting time
  - Additional comments
- View aggregated ratings and reviews

### Search and Filtering
- Search practitioners by:
  - Name
  - Specialty
  - Location
  - Availability
  - Rating
- Search clinics by:
  - Name
  - Location
  - Available practitioners
  - Rating

### Administrative Features
- Comprehensive logging system
- Error tracking and reporting
- System monitoring capabilities

## Tools and Technologies

- Java SE
- Java Swing
- Microsoft SQL Server
- JDBC (Java Database Connectivity)
- MVC Architecture
- Repository Pattern
- DTO Pattern for data transfer
- Service Layer for business logic

## Project Structure

```
EncuentraTuDoctor/
│
├── src/
│   ├── Views/             // Swing GUI components and forms
│   ├── Models/            // Data models (User, Patient, Practitioner, etc.)
│   ├── Controllers/       // Handles UI logic and user interaction
│   ├── DAOs/              // Data Access Objects for database operations
│   ├── DTOs/              // Data Transfer Objects for service layer
│   ├── Services/          // Business logic and service implementations
│   ├── Helpers/           // Utility classes and helper functions
│   └── utils/             // Utility classes (DB connection, validators)
│
├── database/              // SQL scripts for DB schema
├── README.md              // Project documentation
└── .gitignore             // Ignored files
```

## Models Overview

The application includes the following core models:

1. **User (Base Class)**
   - Base class for all users with common attributes
   - Extended by Patient and Practitioner classes

2. **Patient**
   - Medical history tracking
   - Appointment management
   - Rating capabilities

3. **Practitioner**
   - Specialization details
   - Appointment pricing
   - Rating system
   - Clinic associations

4. **Clinic**
   - Location information
   - Practitioner associations
   - Schedule management
   - Rating system

5. **Appointment**
   - Scheduling system
   - Status tracking
   - Notes and documentation

6. **Rating System**
   - Clinic ratings
   - Practitioner ratings
   - Review management

7. **Logging System**
   - Operation tracking
   - Error logging
   - System monitoring

## How to Run

1. Clone the repository to your local machine
2. Import the project into Apache NetBeans
3. Set up MS SQL Server and import the provided database schema
4. Configure the database connection in the project (JDBC URL, username, password)
5. Run the application from the main class in the controller package

## Future Enhancements

- Enhance search capabilities with advanced filtering
- Implement real-time availability updates
- Add practitioner portal for:
  - Managing appointments and schedules
  - Updating availability and clinic information
  - Viewing and responding to patient reviews
  - Managing patient records and visit history
  - Generating reports and analytics
- Add comprehensive reporting system for practitioners
- Implement practitioner dashboard with:
  - Appointment statistics
  - Patient demographics
  - Revenue tracking
  - Performance metrics

---
