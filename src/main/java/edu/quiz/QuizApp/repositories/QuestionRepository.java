package edu.quiz.QuizApp.repositories;

import edu.quiz.QuizApp.entites.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByExamId(Long examId);
    Page<Question> findByExamId(Long examId, Pageable pageable);
    long countByExamId(Long examId);

    @Query("SELECT q FROM Question q WHERE q.exam.id = :examId ORDER BY q.id")
    List<Question> findByExamIdOrderById(@Param("examId") Long examId);

    @Query("SELECT q FROM Question q WHERE q.text LIKE %:searchText%")
    List<Question> findByTextContaining(@Param("searchText") String searchText);

    @Query("SELECT q FROM Question q WHERE q.exam.id = :examId AND q.text LIKE %:searchText%")
    List<Question> findByExamIdAndTextContaining(@Param("examId") Long examId, @Param("searchText") String searchText);

    void deleteByExamId(Long examId);

    @Query(value = "SELECT * FROM questions WHERE exam_id = :examId ORDER BY RAND() LIMIT :count", nativeQuery = true)
    Set<Question> findRandomQuestionsByExamId(@Param("examId") Long examId, @Param("count") int count);
}
