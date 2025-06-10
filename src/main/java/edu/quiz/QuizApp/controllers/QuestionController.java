package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.question.CreateQuestionDto;
import edu.quiz.QuizApp.dtos.question.GetQuestionDto;
import edu.quiz.QuizApp.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
@CrossOrigin
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<GetQuestionDto> createQuestion(@RequestBody CreateQuestionDto createQuestionDto) {
        Optional<GetQuestionDto> question = questionService.createQuestion(createQuestionDto);
        return question.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<GetQuestionDto>> getAllQuestions() {
        Optional<List<GetQuestionDto>> questions = questionService.getAllQuestions();
        return questions.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<GetQuestionDto> getQuestionById(@PathVariable long id) {
        Optional<GetQuestionDto> question = questionService.getQuestionById(id);
        return question.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-by-examId/{examId}")
    public ResponseEntity<List<GetQuestionDto>> getQuestionsByExamId(@PathVariable long examId) {
        Optional<List<GetQuestionDto>> questions = questionService.getAllQuestionByExamId(examId);
        return questions.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-by-userId/{userId}")
    public ResponseEntity<List<GetQuestionDto>> getQuestionsByTeacherId(@PathVariable long userId) {
        Optional<List<GetQuestionDto>> questions = questionService.getAllQuestionsByUserId(userId);
        return questions.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-question-paper-by-examId/{examId}")
    public ResponseEntity<List<GetQuestionDto>> getQuestionPaperByExamId(@PathVariable long examId) {
        Optional<List<GetQuestionDto>> questions = questionService.getQuestionPaperByExamId(examId);
        return questions.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/total-count")
    public ResponseEntity<Long> getTotalCount() {
        return ResponseEntity.ok(questionService.totalQuestionCount());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteQuestion(@PathVariable long id) {
        if (questionService.deleteQuestion(id)) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().build();
    }
}
