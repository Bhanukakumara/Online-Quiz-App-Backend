package edu.quiz.QuizApp.repositories;

import edu.quiz.QuizApp.entites.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentIdAndExamId(Long id, Long id1);
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findByCourseId(Long courseId);
    List<Enrollment> findByExamId(Long examId);
    Enrollment findByStudentIdAndExamId(Long studentId, Long examId);
}
