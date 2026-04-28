# 🚀 JobMate AI

AI-powered job application assistant that analyzes job postings and generates tailored application materials.

## 📌 Overview

JobMate AI helps candidates streamline their job application process by:

* Analyzing job descriptions
* Comparing them with a user profile
* Calculating a match score
* Generating personalized application materials
* Suggesting a preparation plan

---

## 🧠 Features

* 🔐 JWT Authentication (in progress)
* 📄 Job posting analysis (AI-powered)
* 📊 Match score calculation
* 🧾 Cover letter generation
* ❓ Interview question suggestions
* 📅 7-day preparation plan
* 📁 Application tracking system

---

## 🛠 Tech Stack

**Backend:**

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL

**Frontend:**

* React (planned)

**AI:**

* OpenAI API / Gemini API (planned)

**Other:**

* Docker (planned)
* Swagger/OpenAPI (planned)

---

## ⚙️ Getting Started

### 1. Clone repository

```bash
git clone https://github.com/your-username/jobmate-ai.git
cd jobmate-ai/backend
```

---

### 2. Configure database

Create PostgreSQL database:

```sql
CREATE DATABASE jobmate_ai_db;
```

Update `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/jobmate_ai_db
    username: postgres
    password: your_password
```

---

### 3. Run application

```bash
./mvnw spring-boot:run
```

---

### 4. Test API

```http
GET /api/hello
```

---

## 🗂 Project Structure

```
backend/
  controller/
  service/
  repository/
  entity/
  dto/
  config/
```

---

## 📈 Roadmap

* [x] Project setup
* [x] Database configuration
* [x] User entity
* [ ] Authentication (JWT)
* [ ] Profile management
* [ ] Job analysis with AI
* [ ] Frontend (React)
* [ ] Deployment

---

## 🎯 Goal

This project is designed to demonstrate production-ready backend skills including:

* REST API design
* Authentication & security
* Database modeling
* AI integration
* Deployment & DevOps

---

## 👤 Author

Artem Rozov
Aspiring Java Backend Developer
