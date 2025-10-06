# 🧠 Online Quiz Spring Boot API

A simple RESTful API for managing and taking quizzes built with **Spring Boot 3** and **Java 21**.

## 🚀 Features
- Create a quiz
- Add questions with options and correct answers
- Fetch quiz questions (without revealing answers)
- Submit answers and get a score
- Unit tests for scoring and validation

## 🧰 Tech Stack
- Java 21
- Spring Boot 3.3
- Gradle 8.5
- JUnit 5

## 🧪 Run Tests
```bash
./gradlew test
🖥️ Run the Server
./gradlew bootRun
📬 API Examples

POST /api/quizzes → Create quiz

POST /api/quizzes/{id}/questions → Add question

GET /api/quizzes/{id}/questions → Fetch questions

POST /api/quizzes/{id}/submit → Submit answers
