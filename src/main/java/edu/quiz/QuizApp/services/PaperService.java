package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.paper.PaperDTO;
import edu.quiz.QuizApp.dtos.paper.PaperRequestDTO;
import edu.quiz.QuizApp.dtos.paper.PaperResponseDTO;

import java.util.List;

public interface PaperService {
    PaperResponseDTO createPaper(PaperRequestDTO paperRequestDTO);
    PaperResponseDTO getPaperById(Long id);
    List<PaperResponseDTO> getAllPapers();
    List<PaperResponseDTO> getPapersByStudent(Long studentId);
    List<PaperResponseDTO> getPapersByExam(Long examId);
    PaperResponseDTO submitPaper(Long paperId, PaperDTO paperDTO);
    void deletePaper(Long id);
    PaperResponseDTO startExam(Long studentId, Long examId);
    PaperResponseDTO calculateResult(Long paperId);
}
