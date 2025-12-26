# SkillBridge AI Sample Data

Use this data to test the platform and show high-quality results to your mentor.

## 1. Job Role & Job Description
**Job Role:** Senior Full Stack Developer (React & Spring Boot)

**Job Description:**
```text
We are looking for a Senior Full Stack Developer to join our team. 
Key Responsibilities:
- Build scalable web applications using React and Spring Boot.
- Implement Microservices architecture and handle inter-service communication.
- Optimize database performance (PostgreSQL/Redis).
- Set up CI/CD pipelines and deploy using Docker and Kubernetes.
- Lead code reviews and mentor junior developers.

Requirements:
- 5+ years of experience with Java and Spring Boot.
- Proficiency in React.js and modern frontend state management.
- Strong knowledge of AWS (EC2, S3, RDS).
- Experience with Kafka or RabbitMQ is a plus.
```

## 2. Sample Resume (Weak - For Analysis)
**Resume Text:**
```text
Sriman Kumar
Full Stack Developer

Experience:
- Worked at ABC Corp as a developer.
- Created websites using React and Java.
- Fixed bugs and added new features.

Projects:
- Portfolio Website: A simple personal website using HTML/CSS.
- Task Manager: A basic app to manage daily tasks.

Skills:
- Java, Spring Boot, React, SQL, Git.
```

## 3. Sample Interview Q&A (For Evaluation)

### Question 1 (Technical - Microservices):
"How do you handle data consistency across different services in a Microservices architecture?"

**Sample "Good" Answer (Copy this to test):**
"In a microservices architecture, I handle data consistency using the Saga pattern. Instead of a single transaction across multiple services, I implement a sequence of local transactions. Each transaction is followed by an event that triggers the next one. If a step fails, I use compensating transactions to rollback the previous successful steps. This ensures 'eventual consistency' across the system."

**Sample "Bad" Answer (To see the critique):**
"I just use one database for all services so that everything is always consistent and I don't have to worry about complex patterns."

---

## 4. Sample Bullet Point (For AI Rewriter)
**Original Bullet:**
"Fixed bugs in the backend system."

**Target Role:** Senior Java Developer
**Focus Skill:** Performance Optimization
