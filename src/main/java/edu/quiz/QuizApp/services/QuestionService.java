package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.question.CreateQuestionDto;
import edu.quiz.QuizApp.dtos.question.GetQuestionDto;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Optional<GetQuestionDto> createQuestion(CreateQuestionDto createQuestionDto);
    Optional<List<GetQuestionDto>> getAllQuestions();
    Optional<GetQuestionDto> getQuestionById(long id);
    Optional<GetQuestionDto> updateQuestionById(long id, CreateQuestionDto createQuestionDto);
    Boolean deleteQuestion(long id);
    Optional<List<GetQuestionDto>> getAllQuestionByExamId(long examId);
    Optional<List<GetQuestionDto>> getAllQuestionsByUserId(long userId);
    Optional<List<GetQuestionDto>> getQuestionPaperByExamId(long examId);
    Long totalQuestionCount();
}
