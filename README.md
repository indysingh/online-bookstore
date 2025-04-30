# 📚 Online Bookstore Backend

A simple backend REST API for managing books, purchases, and loyalty points in an online bookstore.

---

## 🚀 How to Run and Use

### ✅ Prerequisites

- Java 17+
- Gradle
- IDE (IntelliJ IDEA / VS Code recommended)

### 📦 Build & Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/indysingh/bookstore.git
   cd online-bookstore
   ```

2. **Build the project**:
   ```bash
   ./gradlew clean build
   ```

3. **Run the app**:
   ```bash
   ./gradlew bootRun
   ```

### 🌐 API Endpoints

Base URL: `http://localhost:8080/api`

#### 📘 Books
- `GET /books` – Get all available books
- `POST /books` – Add a new book
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

After running the application, you can explore the API using Swagger UI:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

The documentation includes:
- Available endpoints
- Request and response models
- Error codes and examples


### 📊 H2 Console

Access H2 console: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: *(leave empty)*

---

## 💡 Design & Architectural Decisions

### ✅ SOLID Principles

- **S**ingle Responsibility: Services are separated by concern (e.g., `PurchaseService`, `LoyaltyService`, etc.)
- **O**pen/Closed: Pricing logic uses the **Strategy Pattern** to allow extension without modifying existing code.
- **L**iskov Substitution: Pricing strategies follow interface contracts.
- **I**nterface Segregation: Only relevant interfaces exposed in services.
- **D**ependency Inversion: Controllers depend on service interfaces, injected via Spring.

### ✅ Design Patterns Used

- **Strategy Pattern**: For flexible book pricing based on type and quantity
- **Factory Pattern**: To retrieve appropriate pricing strategy at runtime
- **DTOs**: Used for request/response decoupling
- **Repository Pattern**: Clean separation of persistence logic via Spring Data JPA

### 🧪 Testing

- **JUnit 5** & **Mockito** for service and controller tests
- Edge case tests (e.g., no customer, no books, loyalty discounts)
- `@WebMvcTest` used for REST endpoint validation
- `@SpringBootTest` used for integration scenarios

---

## 🔍 Sample Data

On app startup, some data is auto-loaded into H2 for demo purposes:

- 5 Books (`New Release`, `Regular`, `Old Edition`)
- 2 Customers with different loyalty points

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

- Focused on clean code, modular structure, and testability.
- Kept tech stack lightweight: Spring Boot + H2 + Gradle.
- Left out authentication, caching, and advanced validation to prioritize core business logic.
- Designed system for easy future extension (e.g., promo codes, user roles, wishlist, etc.)
- Swagger/OpenAPI documentation
---

## 🛠 Future Improvements

- Authentication & user management
- Caching book listings for performance
- Dockerization for production-ready deployment

---

## 📬 Contact

Feel free to connect via [GitHub](https://github.com/indysingh) or [indy.arora@gmail.com].