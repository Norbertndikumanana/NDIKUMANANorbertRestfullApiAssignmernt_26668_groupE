# Spring Boot RESTful API - Practical Assignment

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)

A comprehensive collection of Spring Boot RESTful APIs demonstrating CRUD operations, search functionality, and proper HTTP status code implementation.

## 📋 Table of Contents
- [Overview](#overview)
- [Projects](#projects)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [API Documentation](#api-documentation)
- [Postman Collections](#postman-collections)
- [Project Structure](#project-structure)
- [Running the Applications](#running-the-applications)
- [Testing](#testing)
- [Author](#author)

## 🎯 Overview

This repository contains 6 independent Spring Boot projects implementing RESTful APIs for different domains:
1. **Library Book Management API** - Manage books in a library system
2. **Student Registration API** - Handle student registration and information
3. **Restaurant Menu API** - Manage restaurant menu items
4. **E-Commerce Product API** - Product catalog management
5. **Task Management API** - To-do list and task tracking
6. **User Profile API (Bonus)** - Comprehensive user profile management

Each project demonstrates:
- ✅ RESTful API design principles
- ✅ Proper HTTP methods (GET, POST, PUT, PATCH, DELETE)
- ✅ Appropriate HTTP status codes (200, 201, 204, 404)
- ✅ Request/Response handling
- ✅ Query parameters and path variables
- ✅ Search and filter functionality

## 📁 Projects

### 1. Library Book Management API
**Port:** 9090  
**Base URL:** `http://localhost:9090/api/books`

Manages a collection of library books with CRUD operations and search functionality.

**Features:**
- Get all books
- Get book by ID
- Search books by title
- Add new book
- Delete book

### 2. Student Registration API
**Directory:** `question2-student-api`  
**Base URL:** `http://localhost:8080/api/students`

Handles student registration and information management with filtering capabilities.

**Features:**
- Get all students
- Get student by ID
- Filter students by major
- Filter students by minimum GPA
- Register new student
- Update student information

### 3. Restaurant Menu API
**Directory:** `question3_restaurant_menu`  
**Base URL:** `http://localhost:8080/api/menu`

Manages restaurant menu items with category-based organization.

**Features:**
- Get all menu items
- Get menu item by ID
- Filter by category (Appetizer, Main Course, Dessert, Beverage)
- Filter by availability
- Search by name
- Add new menu item
- Toggle item availability
- Remove menu item

### 4. E-Commerce Product API
**Directory:** `question4_E-CommerceProductapi`  
**Base URL:** `http://localhost:8080/api/products`

Comprehensive product catalog management with advanced filtering.

**Features:**
- Get all products (with pagination)
- Get product by ID
- Filter by category
- Filter by brand
- Search by keyword
- Filter by price range
- Get in-stock products
- Add new product
- Update product details
- Update stock quantity
- Delete product

### 5. Task Management API
**Directory:** `question5_Task-Managementapi`  
**Base URL:** `http://localhost:8080/api/tasks`

Simple task/to-do list management system.

**Features:**
- Get all tasks
- Get task by ID
- Filter by completion status
- Filter by priority (LOW, MEDIUM, HIGH)
- Create new task
- Update task
- Mark task as completed
- Delete task

### 6. User Profile API (Bonus)
**Directory:** `question_Bonus`  
**Base URL:** `http://localhost:8080/api/users`

Comprehensive user profile management with custom response wrapper.

**Features:**
- Complete CRUD operations
- Search by username
- Filter by country
- Filter by age range
- Activate/deactivate profiles
- Custom ApiResponse wrapper for all responses

## 🛠 Technologies Used

- **Java 21** - Programming language
- **Spring Boot 3.2.2** - Application framework
- **Spring Web** - RESTful web services
- **Maven** - Dependency management and build tool
- **Postman** - API testing and documentation

## 📋 Prerequisites

Before running these applications, ensure you have:

- Java Development Kit (JDK) 21 or higher
- Maven 3.8 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)
- Postman (for API testing)

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/Norbertndikumanana/NDIKUMANANorbertRestfullApiAssignmernt_26668_groupE.git
cd NDIKUMANANorbertRestfullApiAssignmernt_26668_groupE
```

### 2. Build Each Project

Navigate to each project directory and build:

```bash
# Example for Student API
cd question2-student-api
mvn clean install

# Repeat for other projects
cd ../question3_restaurant_menu
mvn clean install

cd ../question4_E-CommerceProductapi
mvn clean install

cd ../question5_Task-Managementapi
mvn clean install

cd ../question_Bonus
mvn clean install
```

## 📖 API Documentation

### Common HTTP Status Codes

| Status Code | Description |
|------------|-------------|
| 200 OK | Successful GET request |
| 201 Created | Successful POST request (resource created) |
| 204 No Content | Successful DELETE request |
| 404 Not Found | Resource not found |

### Example API Endpoints

#### Library Book Management API

```http
GET    /api/books                    # Get all books
GET    /api/books/{id}               # Get book by ID
GET    /api/books/search?title={title}  # Search by title
POST   /api/books                    # Add new book
DELETE /api/books/{id}               # Delete book
```

**Sample Request (POST):**
```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert Martin",
  "isbn": "978-0132350884",
  "publicationYear": 2008
}
```

#### Student Registration API

```http
GET    /api/students                 # Get all students
GET    /api/students/{studentId}     # Get student by ID
GET    /api/students/major/{major}   # Filter by major
GET    /api/students/filter?gpa={minGpa}  # Filter by GPA
POST   /api/students                 # Register new student
PUT    /api/students/{studentId}     # Update student
```

#### Restaurant Menu API

```http
GET    /api/menu                     # Get all menu items
GET    /api/menu/{id}                # Get menu item by ID
GET    /api/menu/category/{category} # Filter by category
GET    /api/menu/available?available=true  # Get available items
GET    /api/menu/search?name={name}  # Search by name
POST   /api/menu                     # Add menu item
PUT    /api/menu/{id}/availability   # Toggle availability
DELETE /api/menu/{id}                # Remove menu item
```

#### E-Commerce Product API

```http
GET    /api/products                 # Get all products
GET    /api/products?page={page}&limit={limit}  # Paginated results
GET    /api/products/{productId}     # Get product by ID
GET    /api/products/category/{category}  # Filter by category
GET    /api/products/brand/{brand}   # Filter by brand
GET    /api/products/search?keyword={keyword}  # Search products
GET    /api/products/price-range?min={min}&max={max}  # Price range
GET    /api/products/in-stock        # Get in-stock products
POST   /api/products                 # Add product
PUT    /api/products/{productId}     # Update product
PATCH  /api/products/{productId}/stock?quantity={quantity}  # Update stock
DELETE /api/products/{productId}     # Delete product
```

#### Task Management API

```http
GET    /api/tasks                    # Get all tasks
GET    /api/tasks/{taskId}           # Get task by ID
GET    /api/tasks/status?completed={true/false}  # Filter by status
GET    /api/tasks/priority/{priority}  # Filter by priority
POST   /api/tasks                    # Create task
PUT    /api/tasks/{taskId}           # Update task
PATCH  /api/tasks/{taskId}/complete  # Mark as completed
DELETE /api/tasks/{taskId}           # Delete task
```

#### User Profile API (Bonus)

```http
GET    /api/users                    # Get all users
GET    /api/users/{userId}           # Get user by ID
GET    /api/users/search?username={username}  # Search by username
GET    /api/users/country/{country}  # Filter by country
GET    /api/users/age-range?min={min}&max={max}  # Filter by age
POST   /api/users                    # Create user profile
PUT    /api/users/{userId}           # Update user profile
PATCH  /api/users/{userId}/activate  # Activate/deactivate user
DELETE /api/users/{userId}           # Delete user profile
```

**Sample Response (with ApiResponse wrapper):**
```json
{
  "success": true,
  "message": "User profile created successfully",
  "data": {
    "userId": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "age": 25,
    "country": "USA",
    "bio": "Software Developer",
    "active": true
  }
}
```

## 📮 Postman Collections

All API endpoints have been tested and documented in Postman. The collections are available in the `Postman/` directory:

1. **library api.postman_collection.json** - Library Book Management API tests
2. **Student api.postman_collection.json** - Student Registration API tests
3. **Restrauntant api.postman_collection.json** - Restaurant Menu API tests
4. **Product api.postman_collection.json** - E-Commerce Product API tests
5. **Task management Api.postman_collection.json** - Task Management API tests

### Importing Postman Collections

1. Open Postman
2. Click **Import** button
3. Select the JSON file from the `Postman/` directory
4. The collection will be imported with all requests and sample responses

Each collection includes:
- ✅ Pre-configured requests
- ✅ Sample request bodies
- ✅ Expected responses
- ✅ HTTP status codes

## 📂 Project Structure

```
ASSIGNMENT/
├── Postman/                          # Postman collection files
│   ├── library api.postman_collection.json
│   ├── Student api.postman_collection.json
│   ├── Restrauntant api.postman_collection.json
│   ├── Product api.postman_collection.json
│   └── Task management Api.postman_collection.json
│
├── question2-student-api/            # Student Registration API
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── question2_student_api/
│   │   │   │       ├── Controller/
│   │   │   │       │   └── StudentController.java
│   │   │   │       ├── Student.java
│   │   │   │       └── Question2StudentApiApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
│
├── question3_restaurant_menu/        # Restaurant Menu API
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── question3_restaurant_menu_api/
│   │   │   │       ├── MenuController.java
│   │   │   │       ├── MenuItem.java
│   │   │   │       └── Question3RestaurantMenuApplication.java
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
│
├── question4_E-CommerceProductapi/   # E-Commerce Product API
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── question4_E_CommerceProductapi/
│   │   │   │       ├── Controller/
│   │   │   │       │   └── ProductController.java
│   │   │   │       ├── Product.java
│   │   │   │       └── Question4ECommerceProductapiApplication.java
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
│
├── question5_Task-Managementapi/     # Task Management API
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── question5_Task_Managementapi/
│   │   │   │       ├── Controller/
│   │   │   │       │   └── TaskController.java
│   │   │   │       ├── Task.java
│   │   │   │       └── Question5TaskManagementapiApplication.java
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
│
├── question_Bonus/                   # User Profile API (Bonus)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── question_Bonus/
│   │   │   │       ├── controller/
│   │   │   │       │   └── UserProfileController.java
│   │   │   │       ├── ApiResponse.java
│   │   │   │       ├── UserProfile.java
│   │   │   │       └── QuestionBonusApplication.java
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
│
└── README.md                         # This file
```

## ▶️ Running the Applications

### Option 1: Using Maven

Navigate to each project directory and run:

```bash
cd question2-student-api
mvn spring-boot:run
```

### Option 2: Using IDE

1. Open the project in your IDE
2. Navigate to the main application class (e.g., `Question2StudentApiApplication.java`)
3. Right-click and select "Run"

### Option 3: Using JAR file

```bash
cd question2-student-api
mvn clean package
java -jar target/question2-student-api-0.0.1-SNAPSHOT.jar
```

### Default Ports

- Most APIs run on port **8080**
- Library API runs on port **9090** (as per Postman collection)

To change the port, edit `application.properties`:

```properties
server.port=8081
```

## 🧪 Testing

### Using Postman

1. Import the relevant Postman collection from the `Postman/` directory
2. Start the Spring Boot application
3. Execute the requests in Postman
4. Verify the responses and status codes

### Using cURL

```bash
# Get all books
curl http://localhost:9090/api/books

# Get book by ID
curl http://localhost:9090/api/books/1

# Add new book
curl -X POST http://localhost:9090/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "id": 4,
    "title": "Design Patterns",
    "author": "Gang of Four",
    "isbn": "978-0201633610",
    "publicationYear": 1994
  }'

# Delete book
curl -X DELETE http://localhost:9090/api/books/2
```

### Using Browser

For GET requests, you can simply use your browser:

```
http://localhost:8080/api/students
http://localhost:8080/api/menu
http://localhost:8080/api/products
http://localhost:8080/api/tasks
```

## 📝 Key Features Implemented

### 1. RESTful Design
- Resource-based URLs
- Proper HTTP methods
- Stateless communication

### 2. HTTP Status Codes
- **200 OK** - Successful GET requests
- **201 Created** - Successful POST requests
- **204 No Content** - Successful DELETE requests
- **404 Not Found** - Resource not found

### 3. Request Handling
- Path variables (`@PathVariable`)
- Query parameters (`@RequestParam`)
- Request body (`@RequestBody`)

### 4. Search & Filter
- Title/name search
- Category filtering
- Price range filtering
- Status filtering
- Custom query parameters

### 5. Data Management
- In-memory data storage using Lists
- Auto-incrementing IDs
- Sample data initialization

## 🎓 Learning Outcomes

This project demonstrates:

- ✅ Building RESTful APIs with Spring Boot
- ✅ Creating REST Controllers
- ✅ Handling HTTP methods (GET, POST, PUT, PATCH, DELETE)
- ✅ Using proper HTTP status codes
- ✅ Implementing search and filter functionality
- ✅ Working with path variables and query parameters
- ✅ Request/Response body handling
- ✅ API testing with Postman
- ✅ Maven project structure
- ✅ Spring Boot application configuration

## 👨‍💻 Author

**NDIKUMANA Norbert**  
Student ID: 26668  
Group: E

GitHub: [https://github.com/Norbertndikumanana/NDIKUMANANorbertRestfullApiAssignmernt_26668_groupE](https://github.com/Norbertndikumanana/NDIKUMANANorbertRestfullApiAssignmernt_26668_groupE)

## 📄 License

This project is created for educational purposes as part of Spring Boot RESTful API practical assignment.

## 🤝 Contributing

This is an academic project. For any questions or suggestions, please contact the author.

## 📞 Support

For issues or questions:
1. Check the Postman collections for API examples
2. Review the code in each project's Controller class
3. Ensure all dependencies are properly installed
4. Verify the correct port is being used

---

**Note:** All APIs use in-memory data storage. Data will be reset when the application restarts.

**Happy Coding! 🚀**
