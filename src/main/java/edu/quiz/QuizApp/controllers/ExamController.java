package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.exam.ExamCreateDTO;
import edu.quiz.QuizApp.dtos.exam.ExamResponseDTO;
import edu.quiz.QuizApp.dtos.exam.ExamUpdateDTO;
import edu.quiz.QuizApp.services.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/exam")
@RequiredArgsConstructor
@CrossOrigin
public class ExamController {
    private final ExamService examService;

    @PostMapping("/create")
    public ResponseEntity<ExamResponseDTO> createExam(@Valid @RequestBody ExamCreateDTO examCreateDTO) {
        ExamResponseDTO createdExam = examService.createExam(examCreateDTO);
        return new ResponseEntity<>(createdExam, HttpStatus.CREATED);
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<ExamResponseDTO> getExamById(@PathVariable Long id) {
        ExamResponseDTO exam = examService.getExamById(id);
        return ResponseEntity.ok(exam);
    }

    @GetMapping("/get-all")
    public List<ExamResponseDTO> getAllExams() {
        return examService.getAllExams();
    }

    @GetMapping("/get-by-courseId/{courseId}")
    public ResponseEntity<List<ExamResponseDTO>> getExamsByCourseId(@PathVariable Long courseId) {
        List<ExamResponseDTO> exams = examService.getExamsByCourseId(courseId);
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/get-by-teacherId/{teacherId}")
    public ResponseEntity<List<ExamResponseDTO>> getExamsByTeacherId(@PathVariable Long teacherId) {
        List<ExamResponseDTO> exams = examService.getExamsByTeacherId(teacherId);
        return ResponseEntity.ok(exams);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ExamResponseDTO> updateExam(
            @PathVariable Long id,
            @Valid @RequestBody ExamUpdateDTO examUpdateDTO) {
        ExamResponseDTO updatedExam = examService.updateExam(id, examUpdateDTO);
        return ResponseEntity.ok(updatedExam);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> examExists(@PathVariable Long id) {
        boolean exists = examService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/count-by-course/{courseId}")
    public ResponseEntity<Long> getExamCountByCourse(@PathVariable Long courseId) {
        long count = examService.getExamCountByCourseId(courseId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count-by-teacher/{teacherId}/")
    public ResponseEntity<Long> getExamCountByTeacher(@PathVariable Long teacherId) {
        long count = examService.getExamCountByTeacherId(teacherId);
        return ResponseEntity.ok(count);
    }
}
