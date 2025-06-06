package edu.quiz.QuizApp.repositories;

import edu.quiz.QuizApp.entites.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByExamId(Long examId);

    List<Question> findAllByUserId(long userId);
}
