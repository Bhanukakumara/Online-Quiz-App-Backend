package edu.quiz.QuizApp.repositories;

import edu.quiz.QuizApp.entites.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {
    int countByStudentIdAndExamId(Long studentId, Long examId);
    Integer countByEnrollmentId(Long id);
    List<Paper> findByStudentId(Long studentId);
    List<Paper> findByExamId(Long examId);
}
