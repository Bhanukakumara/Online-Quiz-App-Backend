package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.paper_question.PaperQuestionDTO;
import edu.quiz.QuizApp.dtos.paper_question.PaperQuestionRequestDTO;
import edu.quiz.QuizApp.dtos.paper_question.PaperQuestionResponseDTO;

import java.util.List;

public interface PaperQuestionService {
    PaperQuestionResponseDTO createPaperQuestion(PaperQuestionRequestDTO requestDTO);
    PaperQuestionResponseDTO getPaperQuestionById(Long id);
    List<PaperQuestionResponseDTO> getQuestionsByPaper(Long paperId);
    PaperQuestionResponseDTO updatePaperQuestion(Long id, PaperQuestionDTO paperQuestionDTO);
    void deletePaperQuestion(Long id);
    PaperQuestionResponseDTO submitAnswer(Long id, Integer selectedOption);
    List<PaperQuestionResponseDTO> bulkCreatePaperQuestions(List<PaperQuestionRequestDTO> requestDTOs);
    int calculateScore(Long paperId);
}
