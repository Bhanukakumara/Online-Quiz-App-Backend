# Online Quiz Application Backend

This Spring Boot project provides the backend services for an online quiz application. The system supports three user roles—**Admin**, **Teacher**, and **Student**—and manages **Courses**, **Exams**, **Papers**, and **Questions**.

## Features

* **User Management**

    * Admin can register new users (Admin, Teacher, Student).
    * Role-based access control for all endpoints.
* **Course Management**

    * Admin creates and manages courses.
* **Exam Management**

    * Teachers create exams under specific courses.
    * Define number of questions and allowed attempts per student.
* **Paper Generation & Submission**

    * Students enroll in exams.
    * Papers generated with random questions from the question pool.
    * Each question has an individual time limit; total paper duration is the sum of all question times.
    * Submission saves paper details and student marks.
* **Permissions**

    * Admin: Full access to all functions.
    * Teacher: Create exams, view submissions for their exams.
    * Student: Enroll in exams, submit papers, view own results.

## Tech Stack

* **Java 24**
* **Spring Boot**

    * Spring Web
    * Spring Data JPA
    * Spring Security (JWT)
* **Database**: MySQL
* **Build Tool**: Maven

## Prerequisites

* JDK 17+
* Maven 3.6+
* MySQL

## Getting Started

1. **Clone the repository**

   ```bash
   git clone https://github.com/Bhanukakumara/Online-Quiz-App.git
   cd quiz-backend
   ```

2. **Configure application properties**

    * Rename `src/main/resources/application-example.properties` to `application.properties`.
    * Update database URL, username, and password for MySQL or leave defaults for H2.

3. **Build & Run**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access API Docs**

    * Swagger UI: `http://localhost:8080/swagger-ui.html`

## API Endpoints

### Authentication

| Method | Endpoint             | Description                    |
| ------ | -------------------- | ------------------------------ |
| POST   | `/api/auth/register` | Register new user (Admin only) |
| POST   | `/api/auth/login`    | Authenticate and get JWT       |

### Courses (Admin only)

| Method | Endpoint       | Description       |
| ------ | -------------- | ----------------- |
| POST   | `/api/courses` | Create new course |
| GET    | `/api/courses` | List all courses  |

### Exams (Teacher only)

| Method | Endpoint                        | Description              |
| ------ | ------------------------------- | ------------------------ |
| POST   | `/api/courses/{courseId}/exams` | Create exam for a course |
| GET    | `/api/courses/{courseId}/exams` | List exams in a course   |

### Questions (Teacher only)

| Method | Endpoint         | Description        |
| ------ | ---------------- | ------------------ |
| POST   | `/api/questions` | Add new question   |
| GET    | `/api/questions` | List all questions |

### Enroll & Paper (Student only)

| Method | Endpoint                       | Description                              |
| ------ | ------------------------------ | ---------------------------------------- |
| POST   | `/api/exams/{examId}/enroll`   | Enroll in exam and generate paper        |
| GET    | `/api/papers/{paperId}`        | Get paper details                        |
| POST   | `/api/papers/{paperId}/submit` | Submit paper answers and calculate score |

## Data Model

* **User**: `id`, `username`, `password`, `role`
* **Course**: `id`, `title`, `description`
* **Exam**: `id`, `course`, `title`, `questionCount`, `attemptsAllowed`
* **Question**: `id`, `exam`, `text`, `options`, `correctAnswer`, `timeLimit`
* **Paper**: `id`, `exam`, `student`, `questions`, `startTime`, `endTime`, `score`

## Security

* JWT-based authentication for stateless sessions.

## Contributing

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/YourFeature`.
3. Commit your changes: `git commit -m 'Add YourFeature'`.
4. Push to the branch: `git push origin feature/YourFeature`.
5. Open a Pull Request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

Enjoy building your quizzes!
