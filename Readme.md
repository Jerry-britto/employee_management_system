# 📘 Employee Management System – Documentation

## 📝 Introduction

The **Employee Management System** is a full-stack CRUD web application designed to manage employee records efficiently. Users can:

* 👁️ View all employees
* ➕ Add a new employee
* ✏️ Edit existing employee information
* ❌ Delete employee records

The application is built with a **React.js** frontend, a **Spring Boot** backend, and **MongoDB** for data storage.

---

## 🌟 Features

* 🔐 Token-based authentication using JWT
* 📋 View all employee records
* 🔍 Search and view specific employee details
* ➕ Add new employees
* ✏️ Edit employee information
* ❌ Delete employee records
* ⚙️ RESTful API integration

---

## ⚙️ Prerequisites

Make sure you have the following installed before setting up the project:

* Node.js
* npm or Yarn
* Java JDK (17 or above)
* Maven
* MongoDB (Compass or Atlas)

---

## 🚀 Project Setup Instructions

### 🔁 Clone the Repository

```bash
git clone https://github.com/Jerry-britto/employee_management_system.git
```

---

### 🛠️ Backend Setup (Spring Boot + MongoDB)

1. **Navigate to the backend directory:**

```bash
cd backend
```

2. **Configure MongoDB and JWT settings** in `application.properties` or `application.yml`:

```properties
spring.data.mongodb.uri=<MONGODB_URI>
jwt.expiration=<expiration_duration>
jwt.secret=<secret_key> # optional
```

3. **Run the Spring Boot application:**

```bash
mvn spring-boot:run
```

4. **Backend runs at:**
   `http://localhost:8000`

---

### 🌐 Frontend Setup (React)

1. **Navigate to the frontend directory:**

```bash
cd frontend
```

2. **Install dependencies:**

```bash
npm install
```

3. **Start the development server:**

```bash
npm run dev
```

4. **Frontend runs at:**
   `http://localhost:8080`

---

## 🤝 Contribution

Contributions are welcome!
If you discover bugs or have ideas for improvements or new features, here’s how to contribute:

1. **Fork the repository**
2. **Create a new branch:**

```bash
git checkout -b feature/YourFeature
```

3. **Commit your changes:**

```bash
git commit -m "Add YourFeature"
```

4. **Push to your branch:**

```bash
git push origin feature/YourFeature
```

5. **Create a Pull Request**

---

## 🎯 Outcome

Alongside building a full-fledged CRUD application, this project deepened my understanding of token-based authentication using Spring Security and enhanced my skills in full-stack development.

---

## 🙏 Acknowledgement

I would like to sincerely thank the **IT Department of St. Xavier's College**, especially **Prof. Subhash Kumar**, for their invaluable guidance, encouragement, and support. The concepts and practical knowledge imparted during the course greatly contributed to the successful completion of this project


