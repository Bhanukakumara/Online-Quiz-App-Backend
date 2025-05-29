package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.enrollment.EnrollmentDTO;
import edu.quiz.QuizApp.dtos.enrollment.EnrollmentRequestDTO;
import edu.quiz.QuizApp.dtos.enrollment.EnrollmentResponseDTO;
import edu.quiz.QuizApp.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("/create")
    public ResponseEntity<EnrollmentResponseDTO> createEnrollment(@RequestBody EnrollmentRequestDTO requestDTO) {
        EnrollmentResponseDTO responseDTO = enrollmentService.createEnrollment(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<EnrollmentResponseDTO> getEnrollmentById(@PathVariable Long id) {
        EnrollmentResponseDTO responseDTO = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<EnrollmentResponseDTO>> getAllEnrollments() {
        List<EnrollmentResponseDTO> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/get-by-studentId/{studentId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByStudent(@PathVariable Long studentId) {
        List<EnrollmentResponseDTO> enrollments = enrollmentService.getEnrollmentsByStudent(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/get-by-courseId/{courseId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByCourse(@PathVariable Long courseId) {
        List<EnrollmentResponseDTO> enrollments = enrollmentService.getEnrollmentsByCourse(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/get-by-examId/{examId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByExam(@PathVariable Long examId) {
        List<EnrollmentResponseDTO> enrollments = enrollmentService.getEnrollmentsByExam(examId);
        return ResponseEntity.ok(enrollments);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EnrollmentResponseDTO> updateEnrollment(
            @PathVariable Long id,
            @RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentResponseDTO responseDTO = enrollmentService.updateEnrollment(id, enrollmentDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-enrollment")
    public ResponseEntity<Boolean> isStudentEnrolled(
            @RequestParam Long studentId,
            @RequestParam Long examId) {
        boolean isEnrolled = enrollmentService.isStudentEnrolled(studentId, examId);
        return ResponseEntity.ok(isEnrolled);
    }

    @GetMapping("/remaining-attempts")
    public ResponseEntity<Integer> getRemainingAttempts(
            @RequestParam Long studentId,
            @RequestParam Long examId) {
        Integer attempts = enrollmentService.getRemainingAttempts(studentId, examId);
        return ResponseEntity.ok(attempts);
    }
}
