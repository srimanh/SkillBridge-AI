# 📘 Learning Log — SkillBridge AI

This document tracks hourly learning and setup progress during development.
Each entry records **what was done**, **tools used**, and **key takeaways**.

---

## ⏱️ Hour 1 — Project Setup & Environment Initialization

### Backend Setup (Spring Boot)
- Generated Spring Boot project using **Spring Initializr**
- Configuration used:
  - Java 17
  - Maven
  - Spring Web dependency
- Verified backend startup using embedded Tomcat
- Understood basic Spring Boot project structure:
  - `controller/`
  - `service/`
  - `config/`
  - `application.yml`

**Key Learning:**
- Spring Boot auto-configures web servers and dependency injection
- Minimal dependencies help avoid early complexity

---

### Frontend Setup (React + Tailwind CSS)
- Created React project using **Vite**
- Installed and configured **Tailwind CSS**
- Verified Tailwind integration with utility-based styling
- Confirmed hot reload and development server behavior

**Key Learning:**
- Vite provides faster startup and build times than traditional setups
- Tailwind enables rapid UI prototyping without custom CSS files

---

### Project Structure & Version Control
- Organized repository into `backend/` and `frontend/`
- Created initial feature branch for setup
- Committed base project structure with clean commit message

**Key Learning:**
- Early project structure decisions impact scalability
- Clear git history improves collaboration and maintainability

---

## ✅ Status
- Backend and frontend environments successfully initialized
- Project ready for API and UI feature development


## ⏱️ Hour 2 — Backend API Skeleton & Contracts

### Backend Development
- Created REST controllers using Spring Boot
- Implemented health check endpoint (`/api/health`)
- Designed resume analysis endpoint (`/api/analyze-resume`)
- Defined request and response DTOs for API communication
- Returned hardcoded JSON responses to validate API contracts

### Architecture & Structure
- Followed Controller → Service → DTO layering
- Organized code into clear packages for scalability
- Ensured controllers contain no business logic

### Key Learning
- Designing API contracts early simplifies frontend integration
- DTO-based communication enforces structured, predictable responses
- Separating concerns improves maintainability and testing
