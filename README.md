# Advanced Object-Oriented Programming Project Part 2 - Bank Management System

This repository contains the code, documentation, and deliverables for **Project Part 2** in **CS 3331 – Advanced Object-Oriented Programming** at **The University of Texas at El Paso**.

## Project Overview

The purpose of this project is to expand upon a foundational bank management system, incorporating advanced object-oriented concepts such as UML diagrams, design patterns, and robust error handling. This system allows for customer management, transaction handling, and bank manager functionalities.

---

### Table of Contents
1. [Project Requirements](#project-requirements)
2. [Features Implemented](#features-implemented)
3. [Getting Started](#getting-started)
4. [Usage](#usage)
5. [Class Structure](#class-structure)
6. [Design Patterns](#design-patterns)
7. [Error Handling](#error-handling)
8. [Testing](#testing)
9. [Contributing](#contributing)
10. [License](#license)

---

## Project Requirements

This project was developed following strict academic integrity guidelines and fulfills the following requirements:

- **Part A**:
  - Refactored **UML Use Case Diagram** with 4 use cases, 3 actors, and extensions and includes.
  - Two additional **Use Case Scenarios**.
  - Refactored **UML Class Diagram** for the entire system.

- **Part B**:
  - Refactored code to include all functionality from Part 1.
  - **Dynamic input file handling** for CSV files with non-standard column orders.
  - Integration of **interfaces** and at least one **design pattern**.
  - **New User Functionality** to create users with Savings, Checking, and Credit Card Accounts.
  - Enhanced **transaction management** and error handling.
  - **Bank Statement generation** for specific users.
  - System exit with updated bank user sheet generation.
  - Comprehensive **JUnit test cases** and **Javadoc documentation**.
  - **Lab Report** and **Code Review** documentation.

---

## Features Implemented

### User Management
- Creation and management of user accounts with multiple account types (Savings, Checking, Credit Card).
- Automatic **User ID** and **Account Number** generation.
- **Credit limit** assignment based on credit score ranges.

### Transactions
- Supports various transaction types:
  - **Pays**: User pays another user.
  - **Transfers**: User transfers between own accounts.
  - **Inquiries**: User inquires about specific account details.
  - **Withdraws/Deposits**: Standard transactions with appropriate handling.
- **Transaction Log**: Outputs transaction details, including account information, start/end balance, and all transactions in the session.

### Bank Management
- Bank Manager functionality to generate **Bank Statements** for individual users.
- **Transaction Processing** from CSV file with error handling for invalid transactions (e.g., insufficient balance).

---

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 11 or above
- Git for version control
- Any Java IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/SamLopC/Project2Adv.git
   ```
2. Open the project in your preferred IDE.
3. Compile and run the code.

---

## Usage

1. **Run the Main Program** to access the bank management system.
2. **User Options**: Add a new user, view account details, perform transactions, and exit the system.
3. **Bank Manager Options**: Generate bank statements, view user transactions, and manage the user data file.

### Exiting
- Typing "EXIT" in the main menu will close the program and update the bank user sheet with current information.

---

## Class Structure

This project utilizes a well-structured class hierarchy as depicted in the UML Class Diagram (see `/docs/UML_ClassDiagram.pdf`). Key classes include:

- **BankManager**: Handles bank-level operations such as transaction processing and statement generation.
- **User**: Represents a bank user with properties and methods for account management.
- **Account (Abstract)**: Parent class for specific account types, including Savings, Checking, and Credit Card.
- **Transaction**: Represents transactions, with methods for validation and execution.

---

## Design Patterns

The project integrates the following design pattern:

- **Factory Pattern**: Used for creating various types of accounts (e.g., Savings, Checking, Credit Card) based on the account type, promoting scalability and flexibility.

---

## Error Handling

This project includes robust error handling to cover scenarios such as:

- **Insufficient Funds**: Prevents withdrawals and transfers that would result in negative balances.
- **Self-Transfer Restriction**: Blocks transfers or payments from a user to themselves.
- **Credit Limit Overreach**: Prevents credit card payments exceeding the outstanding balance.
- **Common Exceptions**: Includes general exception handling for unexpected input and file errors.

Error messages are logged in the console and recorded in the transaction logs for review.

---

## Testing

### JUnit Testing
JUnit tests have been written for key methods within the project:

- Located in `/src/test/java/`.
- A test suite is available to run all test cases at once.
  
### Javadoc Documentation
- Javadoc comments are included for each class and method.
- To generate the full Javadoc documentation, run:
  ```bash
  javadoc -d doc -sourcepath src -subpackages your.package.name
  ```

---

## Contributing

**Academic Integrity**: This project is part of a university course, and collaboration outside of the designated team is prohibited. All contributions should be made by the original team members.

---

## License

This project is for academic purposes only and is not licensed for external use.
