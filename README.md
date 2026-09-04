# Sunrise Dental Clinic Management System

## Project Overview

The Sunrise Dental Clinic Management System is a Java web application developed for the CIS6003 Advanced Programming module. The system was created to replace the clinic's paper-based process for managing patients, appointments, treatments and billing.

The application allows authorised staff to manage the main day-to-day activities of the clinic through a web interface. It also includes additional features such as reports, role-based access, email notifications, automated testing and a CI workflow.

## Main Features

- User login and logout
- Admin and Receptionist user roles
- Patient registration and patient management
- Dentist management
- Treatment type management
- Appointment creation and search
- Automatic daily appointment number generation
- Multiple treatments for one appointment
- Appointment cancellation
- Appointment confirmation emails
- Appointment cancellation emails
- Bill calculation with treatment charges and consultation fee
- Printable patient invoice
- Appointment and billing reports
- Dashboard statistics and weekly appointment chart
- Help and staff guide
- Session-based authentication and protected routes

## Technologies Used

- Java 17
- JSP and Servlets
- Maven
- Apache Tomcat 9
- MySQL
- JDBC
- JUnit 5
- Mockito
- Git and GitHub
- GitHub Actions

## Application Structure

The project follows a layered structure:

```text
Presentation Layer
    JSP pages / browser interface

Controller Layer
    Servlet controllers

Service Layer
    Business logic

DAO Layer
    Database access through JDBC

Database Layer
    MySQL
```

This structure was used to keep the user interface, business logic and database operations separate.

## Design Patterns Used

The project uses several design approaches and patterns:

- DAO Pattern for database access
- Strategy Pattern for bill calculation
- Singleton-style database connection factory
- Factory / generator structure for report generation

## Database

The main database tables are:

- `users`
- `patients`
- `dentists`
- `appointments`
- `treatments`
- `treatment_types`
- `bills`

The database also includes the stored procedure:

```sql
CALL get_daily_appointments('2026-09-02');
```

This procedure is used to retrieve the appointments for a selected date.

## Testing

JUnit 5 and Mockito were used for automated testing.

The project includes TDD evidence for controllers, services, DAOs, model classes, report generation and bill calculation.

The main TDD process followed was:

```text
RED -> GREEN -> REFACTOR
```

Tests can be run with:

```bash
mvn clean test
```

## CI Workflow

GitHub Actions is used to build and test the project automatically when code is pushed to the repository.

The workflow:

1. Checks out the project
2. Sets up Java 17
3. Runs the Maven tests
4. Builds the WAR package
5. Uploads the build artifact

## Running the Project Locally

### Requirements

Make sure the following are installed:

- Java 17
- Maven
- Apache Tomcat 9
- MySQL

### Database

Create the MySQL database:

```text
sunrise_dental_clinic
```

Configure the required database settings and set the database password using the environment variable used by the project:

```text
SUNRISE_DB_PASSWORD
```

### Email Configuration

Email credentials are not stored in the source code. The following environment variables are used:

```text
SUNRISE_EMAIL_USERNAME
SUNRISE_EMAIL_PASSWORD
```

### Build

Run:

```bash
mvn clean package
```

Then deploy the generated WAR file to Apache Tomcat 9.

## Version Control

The project was developed incrementally using Git feature branches. Examples include:

- `feature/frontend-ui`
- `feature/consultation-fee-tdd`
- `feature/appointment-email`
- `feature/manage-patients`
- `feature/help-section`
- `feature/daily-appointments-procedure`
- `feature/cancellation-email`

Completed feature branches were tested and merged into the main development branch before the final version was moved to `main`.


## Academic Purpose

This project was developed as part of the CIS6003 Advanced Programming assessment for academic purposes.
