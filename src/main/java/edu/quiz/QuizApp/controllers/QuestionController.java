package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.question.QuestionCreateDto;
import edu.quiz.QuizApp.dtos.question.QuestionDto;
import edu.quiz.QuizApp.dtos.question.QuestionUpdateDto;
import edu.quiz.QuizApp.services.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/question")
@RequiredArgsConstructor
@CrossOrigin
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<QuestionDto> createQuestion(@Valid @RequestBody QuestionCreateDto questionCreateDto) {
        QuestionDto createdQuestion = questionService.createQuestion(questionCreateDto);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long id) {
        QuestionDto question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody QuestionUpdateDto questionUpdateDto) {
        QuestionDto updatedQuestion = questionService.updateQuestion(id, questionUpdateDto);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<QuestionDto>> getAllQuestions() {
        List<QuestionDto> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByExamId(@PathVariable Long examId) {
        List<QuestionDto> questions = questionService.getQuestionsByExamId(examId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> questionExists(@PathVariable Long id) {
        boolean exists = questionService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exam/{examId}/count")
    public ResponseEntity<Long> countQuestionsByExamId(@PathVariable Long examId) {
        long count = questionService.countQuestionsByExamId(examId);
        return ResponseEntity.ok(count);
    }
}
