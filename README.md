# 🎯 SkillBridge AI — Resume Intelligence & Mock Interview Platform

SkillBridge AI is an intelligent career-assistance platform that helps job seekers understand **why their resume fails for a specific role** and actively trains them to fix those gaps through **targeted mock interviews**.

Unlike traditional resume scanners that only match keywords, SkillBridge AI focuses on **semantic understanding, explainable feedback, and skill-based improvement**.

---

## 🚀 Project Overview

Modern hiring systems rely heavily on automated screening (ATS), leaving candidates unaware of why they get rejected—even when they have relevant skills.

SkillBridge AI solves this “black box” problem by:
- Analyzing resumes **against a specific job description**
- Explaining missing or weak skill areas
- Generating **role-specific interview questions**
- Providing structured feedback using the **STAR framework**

This creates a continuous feedback loop where users don’t just receive scores—but **learn how to improve**.

---

## 🧠 Solution Architecture

### 1. Resume & Job Description Analysis
- Users upload a resume (PDF) and paste a job description
- The backend extracts and cleans resume text
- An LLM compares resume content with job requirements
- Output is returned as **strictly structured JSON** with:
  - Match score
  - Missing skills
  - Weak resume sections
  - Section-wise feedback

### 2. Explainable Feedback Dashboard
- The frontend visualizes analysis results
- Missing skills are clearly highlighted
- Users understand *what* is missing and *why*

### 3. Targeted Mock Interview Engine
- Interview questions are generated based on detected skill gaps
- Users answer questions in a chat-style interface
- Each answer is evaluated using the **STAR (Situation, Task, Action, Result)** framework
- Feedback is actionable and structured, not generic

---

## 🛠️ Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3.4**
- **Google Gemini 1.5 Flash** (via Direct REST API)
- **Backup Mode**: Automatic mock data fallback for reliable demos

### Frontend
- **React + Vite**
- **Tailwind CSS**
- **GSAP Animations**
- **Lucide Icons**

---

## 🏃 How to Run

### 1. Prerequisites
- Java 17+ installed
- Node.js 18+ installed

### 2. Backend Setup
1. Navigate to the backend directory:
   ```bash
   cd Backend
   ```
2. Configure your API Key in `src/main/resources/application.properties`:
   ```properties
   spring.ai.google.ai.api-key=YOUR_API_KEY
   ```
3. Run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```
The backend will be available at `http://localhost:8080`.

### 3. Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd Frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Run the development server:
   ```bash
   npm run dev
   ```
The application will be available at `http://localhost:5173`.

---

## 🤝 Contributing
...

Contributions are welcome and encouraged.

If you’d like to contribute:
1. Fork the repository
2. Create a new feature branch  
   ```bash
   git checkout -b feature/your-feature-name
3. Commit your changes with clear messages
4. Push the branch and open a Pull Request