<table align="center">
  <tr>
    <td valign="middle">
      <img src="GoWheels_Frontend/public/Logo.png" height="90"/>
    </td>
    <td valign="middle">
      <h1 style="margin: 0 0 0 12px;">
        GoWheels - Car Rental System
      </h1>
    </td>
  </tr>
</table>

<p align="center">
  A Full Stack Car Rental Management Platform built using Spring Boot, React, MySQL, Docker, and Redis.
</p>

---

# 👨‍💻 Developer

This project was designed, developed, tested, and deployed independently by:

### **Durga Prasad Koppula**

Responsibilities:

* Frontend Development (React, HTML, CSS, JavaScript)
* Backend Development (Spring Boot, REST APIs)
* Database Design (MySQL)
* Authentication & Authorization (Spring Security + JWT)
* Caching Implementation (Redis)
* Docker Containerization
* API Testing using Postman
* Deployment & DevOps
* System Architecture Design

---

# 🚀 Live Demo

* 🌐 **Frontend:** YOUR_FRONTEND_URL
* ⚙️ **Backend:** YOUR_BACKEND_URL

---

# 📖 Project Overview

GoWheels is a modern Car Rental Management System that enables users to browse, book, rent, and manage vehicles online.

The platform provides:

* User Registration & Authentication
* Vehicle Management
* Vehicle Booking
* Rental Tracking
* Role-Based Access Control
* Real-Time Availability Tracking
* Redis-Based Performance Optimization

---

# 🛠️ Tech Stack

## 🔹 Frontend

<p>
  <img src="https://skillicons.dev/icons?i=html" height="60"/>
  <img src="https://skillicons.dev/icons?i=css" height="60"/>
  <img src="https://skillicons.dev/icons?i=js" height="60"/>
  <img src="https://skillicons.dev/icons?i=react" height="60"/>
</p>

* HTML5
* CSS3
* JavaScript
* React.js
* Axios

---

## 🔹 Backend

<p>
  <img src="https://skillicons.dev/icons?i=java" height="60"/>
  <img src="https://skillicons.dev/icons?i=spring" height="60"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/hibernate/hibernate-original.svg" height="60"/>
</p>

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* REST APIs
* Maven

---

## 🔹 Database

<p>
  <img src="https://skillicons.dev/icons?i=mysql" height="60"/>
</p>

* MySQL

---

## 🔹 Caching

<p>
  <img src="https://skillicons.dev/icons?i=redis" height="60"/>
</p>

* Redis

---

## 🔹 DevOps & Deployment

<p>
  <img src="https://skillicons.dev/icons?i=docker" height="60"/>
  <img src="https://skillicons.dev/icons?i=git" height="60"/>
  <img src="https://skillicons.dev/icons?i=github" height="60"/>
</p>

* Docker
* Git
* GitHub
* Render / Railway / VPS

---

## 🔹 API Testing

<p>
  <img src="https://www.vectorlogo.zone/logos/getpostman/getpostman-icon.svg" height="60"/>
</p>

* Postman

---

# 🏗️ System Architecture

<p align="center">
  <img src="Frontend/public/architecture.png" width="950"/>
</p>

### 🔄 Architecture Overview

The application follows a modern multi-layer architecture.

### 🖥️ Client Layer

* React Single Page Application
* Responsive User Interface
* Axios API Communication
* JWT Authentication

### ⚙️ Application Layer

* Spring Boot REST APIs
* Service Layer
* Repository Layer
* DTO Layer
* Business Logic Layer
* Redis Cache Layer

### 🗄️ Data Layer

* MySQL Database
* Hibernate ORM
* Spring Data JPA

### 🐳 Infrastructure Layer

* Docker Containers
* Containerized Deployment
* Cloud Hosting Support

> The architecture ensures scalability, maintainability, security, and high performance.

---

# ✨ Features

## 👤 User Features

### 🔐 Authentication

* User Registration
* User Login
* JWT Authentication
* Secure Password Encryption

### 🚗 Car Browsing

* View Available Cars
* Search Cars
* Filter Cars by Category
* View Car Details

### 📅 Booking System

* Rent Cars
* Select Rental Duration
* Track Booking Status
* View Booking History

### 👤 Profile Management

* Update Profile
* Manage Personal Information
* View Rental Records

---

## 🛡️ Admin Features

### 🚘 Car Management

* Add New Cars
* Update Car Details
* Delete Cars
* Manage Availability

### 👥 User Management

* View Registered Users
* Manage User Accounts

### 📊 Dashboard

* Total Cars
* Total Users
* Active Rentals
* Revenue Statistics

### 📋 Booking Management

* Approve Rentals
* Reject Requests
* Monitor Active Bookings

---

# 🔐 Security

* Spring Security
* JWT Authentication
* Password Encryption
* Role-Based Authorization
* CORS Configuration
* Protected REST Endpoints

---

# ⚡ Performance Optimization

* Redis Caching
* Optimized Database Queries
* Pagination Support
* Lazy Loading
* Efficient API Responses

---

# ⚙️ Run Locally

## 1️⃣ Clone Repository

```bash
git clone https://github.com/KoppulaDurgaPrasad/GoWheels.git
cd GoWheels
```

---

## 2️⃣ Backend Setup

```bash
cd GoWheels_Backend

mvn clean install

mvn spring-boot:run
```

Backend runs on:

```bash
http://localhost:8080
```

---

## 3️⃣ Frontend Setup

```bash
cd GoWheels_Frontend

npm install

npm start
```

Frontend runs on:

```bash
http://localhost:3000
```

---

## 4️⃣ Redis Setup

```bash
docker run -d --name redis \
-p 6379:6379 \
redis
```

---

## 5️⃣ Docker Setup

```bash
docker-compose up --build
```

---

# 🚀 Deployment

* 🌐 Frontend Deployment
* ⚙️ Backend Deployment
* 🗄️ MySQL Database
* ⚡ Redis Cache
* 🐳 Docker Containers

---

# 📂 Project Structure

```text
GoWheels
│
├── GoWheels_Backend
│   ├── Controller
│   ├── Service
│   ├── Repository
│   ├── Entity
│   ├── DTO
│   ├── Config
│   └── Security
│
├── GoWheels_Frontend
│   ├── Components
│   ├── Pages
│   ├── Services
│   ├── Context
│   └── Assets
│
└── docker-compose.yml
```

---

# 👨‍💻 Developer

### Durga Prasad Koppula

GitHub:
https://github.com/KoppulaDurgaPrasad

---

# ⭐ Support

If you found this project helpful, please consider giving it a ⭐ on GitHub.
