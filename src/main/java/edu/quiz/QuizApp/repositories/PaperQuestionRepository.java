package edu.quiz.QuizApp.repositories;

import edu.quiz.QuizApp.entites.PaperQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaperQuestionRepository extends JpaRepository<PaperQuestion, Long> {
    List<PaperQuestion> findByPaperId(Long paperId);
    List<PaperQuestion> findByQuestionId(Long questionId);
    int countByPaperIdAndIsCorrect(Long paperId, Boolean isCorrect);
}
