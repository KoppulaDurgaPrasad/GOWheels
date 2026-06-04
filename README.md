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

<p align="center">
  <img src="GoWheels_Frontend/public/Logo.png" width="220" alt="GoWheels Logo"/>
</p>

<h1 align="center">🚗 GoWheels - Car Rental System</h1>

<p align="center">
  A Full Stack Car Rental Management Platform built using React, Spring Boot, MySQL, Redis Cloud, Docker, and JWT Authentication.
</p>

---

# 🚀 Live Demo

* 🌐 **Frontend:** YOUR_VERCEL_URL
* ⚙️ **Backend:** YOUR_RENDER_URL

---

# 📖 Project Overview

GoWheels is a modern Car Rental Management System that enables customers to browse available vehicles, rent cars online, and manage their bookings seamlessly.

The platform provides secure authentication, role-based access control, vehicle management, rental tracking, Redis-powered caching, and cloud deployment for high performance and scalability.

---

# 🛠️ Tech Stack

## 🎨 Frontend

<p>
  <img src="https://skillicons.dev/icons?i=html" height="60"/>
  <img src="https://skillicons.dev/icons?i=css" height="60"/>
  <img src="https://skillicons.dev/icons?i=bootstrap" height="60"/>
  <img src="https://skillicons.dev/icons?i=js" height="60"/>
  <img src="https://skillicons.dev/icons?i=react" height="60"/>
</p>

* HTML5
* CSS3
* Bootstrap 5
* JavaScript (ES6+)
* React.js
* Axios

**Deployment:** Vercel

---

## ⚙️ Backend

<p>
  <img src="https://skillicons.dev/icons?i=java" height="60"/>
  <img src="https://skillicons.dev/icons?i=spring" height="60"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/hibernate/hibernate-original.svg" height="60"/>
</p>

* Java 21
* Spring Boot
* Spring Security
* JWT Authentication
* Google OAuth 2.0
* Spring Data JPA
* Hibernate ORM
* REST APIs
* Maven

**Deployment:** Render

---

## 🗄️ Database

<p>
  <img src="https://skillicons.dev/icons?i=mysql" height="60"/>
</p>

* TiDB Cloud
* MySQL Compatible Database

---

## ⚡ Caching

<p>
  <img src="https://skillicons.dev/icons?i=redis" height="60"/>
</p>

* Redis Cloud
* Distributed Caching
* Performance Optimization

---

## 🐳 DevOps & Tools

<p>
  <img src="https://skillicons.dev/icons?i=docker" height="60"/>
  <img src="https://skillicons.dev/icons?i=git" height="60"/>
  <img src="https://skillicons.dev/icons?i=github" height="60"/>
</p>

* Docker
* Docker Compose
* Git
* GitHub

---

## 🧪 API Testing

<p>
  <img src="https://www.vectorlogo.zone/logos/getpostman/getpostman-icon.svg" height="60"/>
</p>

* Postman

---

# 🏗️ System Architecture

<p align="center">
  <img src="GoWheels_Frontend/public/architecture.png" width="1000"/>
</p>

### 🔄 Architecture Overview

The application follows a multi-layer architecture that ensures scalability, maintainability, and security.

### 🖥️ Client Layer

* React Single Page Application (SPA)
* Responsive UI
* Axios API Communication
* JWT Token Handling

### ⚙️ Application Layer

* Spring Boot REST APIs
* Controller Layer
* Service Layer
* Repository Layer
* DTO Layer
* Business Logic Layer
* Redis Cache Layer

### 🗄️ Data Layer

* TiDB Cloud Database
* MySQL Compatible Storage
* Hibernate ORM
* Spring Data JPA

### ☁️ Infrastructure Layer

* Docker Containers
* Render Deployment
* Vercel Deployment
* Redis Cloud

---

# ✨ Features

## 👤 Customer Features

### 🔐 Authentication & Authorization

* User Registration
* User Login
* JWT Authentication
* Google OAuth 2.0 Login
* Secure Password Encryption
* Role-Based Access Control

### 🚗 Car Browsing

* Browse Available Cars
* Search Cars
* Filter Cars by Category
* View Car Details
* Check Vehicle Availability

### 📅 Car Rental Management

* Rent Cars Online
* Select Rental Duration
* Track Active Rentals
* View Rental History
* Manage Current Bookings

### 👤 Profile Management

* View Profile Information
* Update Personal Details
* Manage Account Information

---

## 🛡️ Admin Features

### 🚘 Car Management

* Add New Cars
* Update Car Details
* Delete Cars
* Manage Vehicle Availability
* Upload and Manage Car Images

### 📋 Rental Management

* View Customer Rentals
* Monitor Active Rentals
* Track Rental History
* View Booking Details

### 👤 Profile Management

* View Admin Profile
* Update Profile Information

---

# ⚡ Performance Features

### Redis Cloud Integration

* Faster Data Retrieval
* Reduced Database Load
* Frequently Accessed Data Caching
* Improved Response Time
* Better Scalability

### Backend Optimization

* Efficient JPA Queries
* Hibernate ORM
* DTO-Based Data Transfer
* RESTful API Design
* Optimized Database Access

---

# 🔐 Security Features

* Spring Security Integration
* JWT Token Authentication
* Google OAuth 2.0 Authentication
* BCrypt Password Encryption
* Role-Based Authorization
* Protected REST Endpoints
* Secure CORS Configuration
* Input Validation
* Global Exception Handling

---

# 📂 Project Structure

```text
GoWheels
│
├── GoWheels_Backend
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   ├── config
│   ├── security
│   └── exception
│
├── GoWheels_Frontend
│   ├── src
│   │   ├── components
│   │   ├── pages
│   │   ├── services
│   │   ├── context
│   │   └── assets
│   │
│   └── public
│
├── docker-compose.yml
└── README.md
```

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

Backend runs at:

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

Frontend runs at:

```bash
http://localhost:3000
```

---

## 4️⃣ Docker Setup

```bash
docker-compose up --build
```

---

# 👨‍💻 Developer

### Durga Prasad Koppula

🔗 GitHub:

https://github.com/KoppulaDurgaPrasad

---

# ⭐ Support

If you found this project helpful, please consider giving it a ⭐ on GitHub.

Your support and feedback are greatly appreciated!
