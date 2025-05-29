package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.question.QuestionCreateDto;
import edu.quiz.QuizApp.dtos.question.QuestionDto;
import edu.quiz.QuizApp.dtos.question.QuestionUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionService {
    QuestionDto createQuestion(QuestionCreateDto questionCreateDto);
    QuestionDto getQuestionById(Long id);
    QuestionDto updateQuestion(Long id, QuestionUpdateDto questionUpdateDto);
    void deleteQuestion(Long id);
    List<QuestionDto> getAllQuestions();
    List<QuestionDto> getQuestionsByExamId(Long examId);
    Page<QuestionDto> getQuestionsByExamId(Long examId, Pageable pageable);
    boolean existsById(Long id);
    long countQuestionsByExamId(Long examId);
}
