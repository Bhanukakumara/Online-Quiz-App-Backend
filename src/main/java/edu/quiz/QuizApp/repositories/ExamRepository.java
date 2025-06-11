package edu.quiz.QuizApp.repositories;

import edu.quiz.QuizApp.entites.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findAllByCourseId(Long courseId);
    List<Exam> findAllByUserId(Long teacherId);
}
