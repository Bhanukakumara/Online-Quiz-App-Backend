package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.paper.AiRequestDto;
import edu.quiz.QuizApp.dtos.paper.CreatePaperDto;
import edu.quiz.QuizApp.dtos.paper.GetPaperDto;
import edu.quiz.QuizApp.services.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/paper")
@RequiredArgsConstructor
public class PaperController {
    private final PaperService paperService;

    @PostMapping("/save")
    public void savePaper(@RequestBody CreatePaperDto createPaperDto) {
        paperService.savePaper(createPaperDto);
    }

    @GetMapping("/submissions-by-minute")
    public ResponseEntity<List<Map<String, Object>>> getSubmissionsByMinute(
            @RequestParam(defaultValue = "60") int minutes) {
        List<Map<String, Object>> data = paperService.getSubmissionsByMinute(minutes);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/live-submissions")
    public ResponseEntity<Map<String, Object>> getLiveSubmissions() {
        Map<String, Object> data = paperService.getLiveSubmissionData();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/get-all-answer")
    public ResponseEntity<List<GetPaperDto>> getAllAnswer() {
        return ResponseEntity.ok(paperService.getAllPapers());
    }

    @GetMapping("/get-all-answer-by-student-id/{studentId}")
    public ResponseEntity<List<GetPaperDto>> getAllAnswerByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(paperService.getAllPaperByStudentId(studentId));
    }

    @GetMapping("/get-ai-request/{studentId}")
    public ResponseEntity<AiRequestDto> getAiRequest(@PathVariable Long studentId) {
        return ResponseEntity.ok(paperService.getAiRequest(studentId));
    }
}
