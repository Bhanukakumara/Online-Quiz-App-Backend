package edu.quiz.QuizApp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.quiz.QuizApp.dtos.exam.CreateExamDTO;
import edu.quiz.QuizApp.dtos.exam.GetExamDTO;
import edu.quiz.QuizApp.services.ExamService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/exam")
@RequiredArgsConstructor
@CrossOrigin
public class ExamController {
    private final ExamService examService;

    @PostMapping("/create")
    public ResponseEntity<GetExamDTO> createExam(@RequestBody CreateExamDTO createExamDTO) {
        Optional<GetExamDTO> exam = examService.createExam(createExamDTO);
        return exam.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<GetExamDTO>> getAllExams() {
        Optional<List<GetExamDTO>> exams = examService.getAllExams();
        return exams.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<GetExamDTO> getExamById(@PathVariable Long id) {
        Optional<GetExamDTO> exam = examService.getExamById(id);
        return exam.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-by-courseId/{courseId}")
    public ResponseEntity<List<GetExamDTO>> getExamsByCourseId(@PathVariable Long courseId) {
        Optional<List<GetExamDTO>> exams = examService.getExamByCourseId(courseId);
        return exams.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-by-teacherId/{teacherId}")
    public ResponseEntity<List<GetExamDTO>> getExamsByTeacherId(@PathVariable Long teacherId) {
        Optional<List<GetExamDTO>> exams = examService.getExamByTeacherId(teacherId);
        return exams.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/total-count")
    public ResponseEntity<Long> getTotalCount() {
        return ResponseEntity.ok(examService.totalExamCount());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GetExamDTO> updateExam(@PathVariable Long id, @RequestBody CreateExamDTO createExamDTO) {
        Optional<GetExamDTO> exam = examService.updateExam(id, createExamDTO);
        return exam.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
}
