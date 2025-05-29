package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.paper.PaperDTO;
import edu.quiz.QuizApp.dtos.paper.PaperRequestDTO;
import edu.quiz.QuizApp.dtos.paper.PaperResponseDTO;
import edu.quiz.QuizApp.services.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/paper")
@RequiredArgsConstructor
public class PaperController {
    private final PaperService paperService;

    @PostMapping("/create")
    public ResponseEntity<PaperResponseDTO> createPaper(@RequestBody PaperRequestDTO paperRequestDTO) {
        PaperResponseDTO responseDTO = paperService.createPaper(paperRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<PaperResponseDTO> getPaperById(@PathVariable Long id) {
        PaperResponseDTO responseDTO = paperService.getPaperById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PaperResponseDTO>> getAllPapers() {
        List<PaperResponseDTO> papers = paperService.getAllPapers();
        return ResponseEntity.ok(papers);
    }

    @GetMapping("/get-by-studentId/{studentId}")
    public ResponseEntity<List<PaperResponseDTO>> getPapersByStudent(@PathVariable Long studentId) {
        List<PaperResponseDTO> papers = paperService.getPapersByStudent(studentId);
        return ResponseEntity.ok(papers);
    }

    @GetMapping("/get-by-examId/{examId}")
    public ResponseEntity<List<PaperResponseDTO>> getPapersByExam(@PathVariable Long examId) {
        List<PaperResponseDTO> papers = paperService.getPapersByExam(examId);
        return ResponseEntity.ok(papers);
    }

    @PutMapping("/{id}/submit")
    public ResponseEntity<PaperResponseDTO> submitPaper(
            @PathVariable Long id,
            @RequestBody PaperDTO paperDTO) {
        PaperResponseDTO responseDTO = paperService.submitPaper(id, paperDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePaper(@PathVariable Long id) {
        paperService.deletePaper(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/start-exam")
    public ResponseEntity<PaperResponseDTO> startExam(
            @RequestParam Long studentId,
            @RequestParam Long examId) {
        PaperResponseDTO responseDTO = paperService.startExam(studentId, examId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}/result")
    public ResponseEntity<PaperResponseDTO> calculateResult(@PathVariable Long id) {
        PaperResponseDTO responseDTO = paperService.calculateResult(id);
        return ResponseEntity.ok(responseDTO);
    }
}
