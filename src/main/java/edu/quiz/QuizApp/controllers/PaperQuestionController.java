package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.paper_question.PaperQuestionDTO;
import edu.quiz.QuizApp.dtos.paper_question.PaperQuestionRequestDTO;
import edu.quiz.QuizApp.dtos.paper_question.PaperQuestionResponseDTO;
import edu.quiz.QuizApp.services.PaperQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/paper-question")
@RequiredArgsConstructor
public class PaperQuestionController {
    private final PaperQuestionService paperQuestionService;

    @PostMapping("/create")
    public ResponseEntity<PaperQuestionResponseDTO> createPaperQuestion(
            @RequestBody PaperQuestionRequestDTO requestDTO) {
        PaperQuestionResponseDTO responseDTO = paperQuestionService.createPaperQuestion(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<PaperQuestionResponseDTO>> bulkCreatePaperQuestions(
            @RequestBody List<PaperQuestionRequestDTO> requestDTOs) {
        List<PaperQuestionResponseDTO> responseDTOs = paperQuestionService.bulkCreatePaperQuestions(requestDTOs);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<PaperQuestionResponseDTO> getPaperQuestionById(@PathVariable Long id) {
        PaperQuestionResponseDTO responseDTO = paperQuestionService.getPaperQuestionById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/get-by-paperId/{paperId}")
    public ResponseEntity<List<PaperQuestionResponseDTO>> getQuestionsByPaper(@PathVariable Long paperId) {
        List<PaperQuestionResponseDTO> responseDTOs = paperQuestionService.getQuestionsByPaper(paperId);
        return ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PaperQuestionResponseDTO> updatePaperQuestion(
            @PathVariable Long id,
            @RequestBody PaperQuestionDTO paperQuestionDTO) {
        PaperQuestionResponseDTO responseDTO = paperQuestionService.updatePaperQuestion(id, paperQuestionDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}/submit-answer")
    public ResponseEntity<PaperQuestionResponseDTO> submitAnswer(
            @PathVariable Long id,
            @RequestParam Integer selectedOption) {
        PaperQuestionResponseDTO responseDTO = paperQuestionService.submitAnswer(id, selectedOption);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePaperQuestion(@PathVariable Long id) {
        paperQuestionService.deletePaperQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paper/{paperId}/score")
    public ResponseEntity<Integer> calculateScore(@PathVariable Long paperId) {
        int score = paperQuestionService.calculateScore(paperId);
        return ResponseEntity.ok(score);
    }
}
