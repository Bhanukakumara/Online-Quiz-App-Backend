package edu.quiz.QuizApp.repositories;

import edu.quiz.QuizApp.entites.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    int countByStudentIdAndExamId(Long studentId, Long examId);
    Optional<Enrollment> findTopByStudentIdAndExamIdOrderByIdDesc(Long studentId, Long examId);

}
