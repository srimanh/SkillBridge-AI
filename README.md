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
- **Java**
- **Spring Boot**
- RESTful APIs
- PDF text extraction
- LLM integration (JSON-first responses)

### Frontend
- **React**
- **Tailwind CSS**
- Component-based UI
- Responsive design

### AI & Prompting
- Prompt chaining
- Role-based system prompts
- Structured JSON outputs
- Semantic skill matching
- STAR-based evaluation logic

---

## 🤝 Contributing

Contributions are welcome and encouraged.

If you’d like to contribute:
1. Fork the repository
2. Create a new feature branch  
   ```bash
   git checkout -b feature/your-feature-name
3. Commit your changes with clear messages
4. Push the branch and open a Pull Request