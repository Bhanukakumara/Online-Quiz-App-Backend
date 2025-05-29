package edu.quiz.QuizApp.repositories;

import edu.quiz.QuizApp.entites.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByCourseId(Long courseId);
    List<Exam> findByTeacherId(Long teacherId);
    long countByCourseId(Long courseId);
    long countByTeacherId(Long teacherId);

    @Query("SELECT e FROM Exam e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Exam> findByTitleContainingIgnoreCase(@Param("title") String title);

    List<Exam> findByCourseIdAndTeacherId(Long courseId, Long teacherId);
    List<Exam> findByQuestionCountGreaterThan(int questionCount);
    List<Exam> findByTotalTimeMinutesBetween(int minTime, int maxTime);
}
