🚀 Live Demo
  Frontend: <https://task-app-ten-zeta.vercel.app>
  Backend API: <https://taskapp-yo30.onrender.com>

🛠️ Technology Stack
Frontend
  ReactJS + Redux
  Axios (for HTTP requests)
  React Router DOM
  Responsive design (CSS/SCSS or styled-components)
  Form validation (React Hook Form / built-in validation)

Backend
  Java 21
  Spring Boot 3.4.5
  Spring Security + JWT Authentication
  Spring Data JPA + Hibernate
  Flyway for DB migrations
  MySQL as the database
  MapStruct for DTO mapping
  OpenAPI (SpringDoc) for API documentation
  Logging (SLF4J / Logback)

DevOps
  Dockerized backend
  Vercel for frontend deployment
  Render.com for backend deployment
  GitHub Actions for CI/CD (testing + deployment)
  Environment variable-based configuration

📦 Features
  ✅ User registration and login (JWT secured)
  📋 Add / Edit / Delete tasks
  📌 Task categorization via tags
  ⚠️ Task priority levels (Low, Medium, High)
  📅 Due date assignment

🧪 Testing
  Backend includes unit tests using spring-boot-starter-test and spring-security-test
  Test coverage targets 70%+ of critical endpoints

📚 API Documentation
Swagger UI is available at: <https://taskapp-yo30.onrender.com/swagger-ui.html>

⚙️ Setup Instructions

  1. Backend Setup (Spring Boot)
    git clone <https://github.com/vananh0107/taskApp/tree/main/todo>
    cd todo-backend

    Configure MySQL in application.yml or .env
      spring.datasource.url=jdbc:mysql://localhost:3306/todoapp
      spring.datasource.username=root
      spring.datasource.password=yourpassword
      jwt.secret=yourSecretKey
    Run App
      ./mvnw spring-boot:run
  2. Frontend Setup (React)
    git clone <https://github.com/your-repo/todo-frontend.git>
    cd todo-frontend
    npm install

    Configure API URL
      Set API base URL (e.g., in .env): REACT_APP_API_BASE_URL=https://taskapp-yo30.onrender.com
    Run React App
      npm run dev
📤 Deployment Details
  Frontend (Vercel): Deployed via GitHub → Vercel auto-deploy pipeline
  Backend (Render): Deployed via GitHub → Render auto-deploy and environment setup
  CI/CD: GitHub Actions runs unit tests and triggers builds on push
