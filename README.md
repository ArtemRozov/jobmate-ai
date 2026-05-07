# JobMate AI

JobMate AI is a full-stack AI-powered job application assistant that helps users manage job applications, analyze vacancies against their profile, and generate tailored application materials using OpenAI.

The project is built with Spring Boot, React, PostgreSQL and LangChain4j/OpenAI.

---

# Features

## Authentication

* JWT-based authentication
* User registration and login
* Protected API endpoints
* Persistent authentication via localStorage

## User Profile

* Candidate profile management
* Skills and experience tracking
* GitHub, LinkedIn and portfolio links

## Job Tracker

* Save job postings
* Track application statuses
* Filter jobs by status
* Delete and manage saved vacancies

## AI Analysis

* AI-powered job analysis
* Match score calculation
* Missing skills detection
* Tailored CV summary generation
* Cover letter generation
* Interview question suggestions
* 7-day preparation plan generation
* Persistent analysis result storage

## Backend

* REST API architecture
* Global exception handling
* Swagger/OpenAPI documentation
* Basic service tests

---

# Tech Stack

## Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* PostgreSQL
* JWT Authentication
* LangChain4j
* OpenAI API
* Swagger/OpenAPI
* JUnit 5
* Mockito

## Frontend

* React
* Vite
* React Router
* Axios
* CSS

---

# Project Structure

```text
jobmate-ai/
│
├── backend/
│   ├── src/main/java/com/jobmate/ai
│   ├── src/main/resources
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   ├── public/
│   └── package.json
│
└── README.md
```

---

# Application Architecture

## Backend Modules

### Authentication

Handles:

* registration
* login
* JWT generation and validation
* authorization

### Profile Module

Handles:

* user profile CRUD
* candidate information storage

### Job Module

Handles:

* job posting management
* status updates
* filtering
* deletion

### AI Analysis Module

Handles:

* OpenAI integration
* prompt generation
* structured AI responses
* analysis result persistence

---

# API Overview

## Authentication

```http
POST /api/auth/register
POST /api/auth/login
```

## Profile

```http
GET /api/profile/me
PUT /api/profile/me
```

## Jobs

```http
POST /api/jobs
GET /api/jobs
GET /api/jobs/{id}
GET /api/jobs?status=SAVED
PATCH /api/jobs/{id}/status
DELETE /api/jobs/{id}
```

## AI Analysis

```http
POST /api/jobs/{id}/analyze
GET /api/jobs/{id}/analysis
```

---

# Swagger Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Environment Variables

## Backend

Create environment variable:

```env
OPENAI_API_KEY=your_openai_api_key
```

## Frontend

Create `.env` inside `frontend/`:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

---

# Local Development Setup

## 1. Clone Repository

```bash
git clone https://github.com/your-username/jobmate-ai.git
cd jobmate-ai
```

---

## 2. Backend Setup

### Navigate to backend

```bash
cd backend
```

### Configure PostgreSQL

Create database:

```sql
CREATE DATABASE jobmate_ai_db;
```

Update database credentials in:

```text
src/main/resources/application.yml
```

Example:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/jobmate_ai_db
    username: postgres
    password: your_password
```

### Set OpenAI API key

PowerShell:

```powershell
$env:OPENAI_API_KEY="your_api_key"
```

### Run backend

```powershell
.\mvnw spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

---

## 3. Frontend Setup

### Navigate to frontend

```bash
cd frontend
```

### Install dependencies

```bash
npm install
```

### Run frontend

```bash
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

---

# Running Tests

## Backend tests

```powershell
cd backend
.\mvnw test
```

---

# Security

The application uses:

* JWT Bearer authentication
* BCrypt password hashing
* Spring Security filter chain
* Stateless authentication
* Protected REST endpoints

---

# AI Workflow

1. User creates a profile
2. User saves a job posting
3. User requests AI analysis
4. Backend builds a structured prompt
5. OpenAI generates analysis
6. Result is validated and saved
7. Frontend displays structured analysis data

---

# Example AI Output

The AI analysis includes:

* match score
* missing skills
* tailored CV summary
* cover letter
* interview questions
* preparation plan

---

# Current Status

## Completed

* Backend architecture
* JWT authentication
* User profile system
* Job tracker
* AI integration
* Persistent AI analysis
* Global error handling
* Swagger documentation
* Frontend MVP
* Protected frontend routes
* Responsive layout
* Basic tests

## Planned

* Docker support
* Production deployment
* AI response streaming
* Rich text editor
* CV upload and parsing
* Email integration
* Multi-model AI support

---

# Author

Artem Rozov

GitHub:

```text
https://github.com/ArtemRozov
```

---

# License

This project is created for educational and portfolio purposes.
