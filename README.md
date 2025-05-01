# 📚 Online Bookstore Backend

A simple backend REST API for managing books, purchases, and loyalty points in an online bookstore.

---

## 📄 Home Assignment: Backend Engineer

### Overview

The purpose of the assignment is to assess object-oriented analysis and modelling skills, Java coding skills, code structuring, and API design. Take your time on the task, but don’t get too carried away. If you submit a solution that is in any way incomplete, the parts that you decided to focus on are relevant.

You are free to use any tools, libraries, or frameworks. Please include a README covering the decisions made, what was prioritized, what wasn't, and how to run and use the program.

### Time Limit

**One week**, starting from acknowledgment of the email or 1 day after the email was sent without acknowledgment.

### Assignment Scope: Online Bookstore

We want a system for managing:

1. **Inventory of books**
2. **Purchase and book pricing**
3. **Customer loyalty points**

#### Inventory

Supports adding, updating, and deleting books.

#### Pricing Rules

Books are grouped in three types:

- **New Releases**: 100% price always.
- **Regular**: 100%, minus 10% if 3+ are bought.
- **Old Editions**: 20% off base price; additional 5% off if 3+ are bought.

#### Loyalty Points

- 1 point per purchased book.
- 10 points = 1 regular or old edition book free (points reset to 0).

#### Requirements

Build a **REST HTTP API** supporting:

- View available books
- Purchase one or more books and calculate price
- View customer loyalty points

Include unit tests (bonus).

### Delivery

- Fully executable solution
- Public GitHub repository
- README file with documentation and instructions

---

## 🚀 How to Run and Use

### ✅ Prerequisites

- Java 17+
- Gradle
- IDE (IntelliJ IDEA / VS Code recommended)

### 📦 Build & Run

```bash
git clone https://github.com/indysingh/online-bookstore.git
cd online-bookstore
./gradlew clean build
./gradlew bootRun
```

### 🌐 API Endpoints

Base URL: `http://localhost:8080/api`

#### 📘 Books

- `GET /books` – Get all available books
- `POST /books` – Add a new book
  ```json
  [
    {
      "title": "Spring Boot Essentials",
      "type": "REGULAR",
      "basePrice": 40.0
    }
  ]
  ```
- `PUT /books/{id}` – Update a book
- `DELETE /books/{id}` – Delete a book

#### 🛒 Purchase

- `POST /purchase` – Purchase books and calculate total price

  ```json
  {
    "bookIds": [1, 2, 3],
    "customerId": 1
  }
  ```

#### 🎁 Loyalty

- `GET /loyalty/{customerId}` – Get customer loyalty points

### 📑 API Documentation (Swagger/OpenAPI)

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON Spec: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 📊 H2 Console

Access: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: *(leave empty)*

---

## 💡 Design & Architectural Decisions

### ✅ SOLID Principles

- **Single Responsibility**: Services separated by concern
- **Open/Closed**: Strategy pattern allows pricing extensions
- **Liskov Substitution**: Strategy interfaces respected
- **Interface Segregation**: Interfaces expose only what's necessary
- **Dependency Inversion**: Services injected via Spring

### ✅ Patterns Used

- **Strategy Pattern**: For dynamic pricing
- **Factory Pattern**: For pricing strategy selection
- **DTOs**: Request/response decoupling
- **Repository Pattern**: Clean persistence with Spring Data JPA

### 🧪 Testing

- **JUnit 5** & **Mockito** for unit tests
- Edge case validation (e.g., loyalty discounts)
- `@WebMvcTest` for controllers
- `@SpringBootTest` for integration

---

## 📁 Project Structure

```
com.sportygroup.bookstore
├── controller
├── service
├── model
├── dto
├── pricing       ← Strategy pattern here
├── repository
└── BookstoreApplication.java
```

---

## ✍️ Author Notes

- Focused on clean, testable architecture.
- Lightweight stack: Spring Boot + H2 + Gradle.
- Left out auth, caching, advanced validation to focus on core logic.
- Designed for future extensibility (e.g., promo codes, roles).

---

## 🛠 Future Improvements

- Add authentication & user roles
- Cache book data for performance
- Docker support for containerized deployment

---

## 📬 Contact

- GitHub: [https://github.com/indysingh](https://github.com/indysingh)
- Email: [indy.arora@gmail.com](mailto:indy.arora@gmail.com)