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

## 🧠 Hour 3 — AI Integration & Semantic Analysis

### AI Engineering
- Integrated **Spring AI (OpenAI)** for core intelligence
- Designed a **Senior Technical Recruiter prompt** for semantic resume analysis
- Enforced **strict JSON output** using LLM system instructions
- Mapped LLM responses directly to existing Java DTOs

### Prompt Engineering
- Instructed AI to compare meaning (semantic), not just keywords
- Defined a structured JSON schema for consistent parsing
- Added clear boundaries to prevent AI from returning markdown or conversational text

### Key Learning
- **Semantic Matching**: LLMs provide deeper insights than simple keyword scanners
- **JSON-First Design**: Forcing structured output from LLMs is critical for stable backends
- **Context Injection**: Passing JD and Resume as distinct context blocks improves accuracy

## 🎨 Hour 4 — Premium Dashboard UI

### Frontend Development
- Built a **premium dashboard** using React and Tailwind CSS
- Implemented **GSAP entrance animations** for a high-end feel
- Added **Light/Dark mode support** with smooth transitions
- Designed an **intuitive results visualization** (Score, Skills, Feedback) 
- Integrated with Backend API using asynchronous fetch calls

### UI/UX Design
- **Glassmorphism**: Used backdrop blur and semi-transparent borders for a modern look
- **Dynamic Scorebar**: Animated progress indicator reflects AI analysis results
- **Responsive Layout**: Clean split-screen input area for seamless data entry

### Key Learning
- **Aesthetic Matters**: A premium UI builds trust in AI-driven results
- **Motion Design**: Subtle GSAP animations guide user focus without being distracting
- **Contextual Visualization**: Using color-coded tags (Red for missing, Amber for weak) makes analysis scannable
