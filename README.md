
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

---

## 📘 Postman Collection

You can test the full API using the Postman collection located at:

```
src/main/resources/online-bookstore.postman_collection.json
```

Import it into Postman and follow the scenario steps below.

---

## 🔄 Example Usage Scenario

1. **Add Books**
  - Use `POST /api/books` with sample JSON (`resources/sample/books.json`)

2. **Add a Customer**
  - Use `POST /api/customers` with customer details

3. **Purchase Books**
  - Use `POST /api/purchase` with book IDs and customer ID (`resources/sample/purchase.json`)

4. **Check Loyalty Points**
  - Use `GET /api/loyalty/{customerId}` to verify points

---

## 🌐 API Endpoints

Base URL: `http://localhost:8080/api`

### Books

- `GET /books` – Get paginated list of books
- `POST /books` – Add new books

### Customer

- `POST /customers` – Add a customer
- `GET /customers` – Get all customers
- `GET /customers/{id}` – Get customer by ID
- `PATCH /customers/{id}` – Update customer
- `DELETE /customers/{id}` – Delete customer

### Purchase

- `POST /purchase` – Purchase books and calculate total price

### Loyalty

- `GET /loyalty/{customerId}` – Get customer loyalty points

### API Docs

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON Spec: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 📁 Project Structure

```
com.sportygroup.bookstore
├── book (book CRUD & pricing)
├── customer (customer CRUD)
├── loyalty (loyalty logic)
├── purchase (purchase processing)
├── service (purchase service logic)
└── BookstoreApplication.java
resources/
├── application.properties
├── sample/
│   ├── books.json
│   └── purchase.json
└── online-bookstore.postman_collection.json
```

---

## ✍️ Author Notes

- Designed with extensibility in mind.
- Modular services and DTOs.
- Can be extended to include user auth, promo codes, etc.

---

## 📬 Contact

- GitHub: [https://github.com/indysingh](https://github.com/indysingh)
- Email: [indy.arora@gmail.com](mailto:indy.arora@gmail.com)
