# FINIO Online Banking System

The FINIO Online Banking System is a Java-based desktop application developed using Swing for the user interface and JDBC for database connectivity with Oracle Database. The application simulates real-world banking operations, allowing users to manage their accounts and perform financial transactions efficiently.

---

## Features

- User Registration and Login
- Deposit and Withdrawal Operations
- Real-time Balance Updates
- Transaction History (Bank Statement)
- UPI Payment Simulation
- Dashboard with Debit and Credit Card Interface
- Search, Update, and Delete User Functionality

---

## Technologies Used

- Java (Swing)
- JDBC
- Oracle Database
- IntelliJ IDEA

---

## System Architecture

The application follows a layered architecture:

- Presentation Layer: Swing-based user interface
- Business Logic Layer: Service classes handling application logic
- Data Access Layer: DAO classes using JDBC
- Database Layer: Oracle relational database

---

## Object-Oriented Concepts Used

- Encapsulation: Implemented using private fields and public getters/setters
- Inheritance: Person class extended by User class
- Polymorphism: DAO interfaces and method overriding
- Abstraction: Use of interfaces for database operations

---

## Database Schema

### Users Table
- id (Primary Key)
- name
- email
- password
- balance

### Transactions Table
- id (Primary Key)
- user_id (Foreign Key)
- type
- amount
- date_time

---

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/sharavisshinde03/FINIO.git
   ```

2. Open the project in IntelliJ IDEA.

3. Set up Oracle Database and run the SQL scripts provided in the `database` folder.

4. Update database connection details in the DBConnection class.

5. Run the application using LoginUI.

---

## Project Structure

```
com.bank
│
├── model
├── dao
├── service
├── main
```

---

## Conclusion

The FINIO Online Banking System demonstrates the implementation of a real-world application using Java, JDBC, and Oracle Database. It effectively applies object-oriented programming principles and follows a modular architecture for better maintainability and scalability.