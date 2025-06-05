package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.enrollment.GetEnrollmentDTO;
import edu.quiz.QuizApp.dtos.enrollment.CreateEnrollmentDTO;
import edu.quiz.QuizApp.dtos.exam.GetExamDTO;
import edu.quiz.QuizApp.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enrollment")
@RequiredArgsConstructor
@CrossOrigin
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("/create")
    public ResponseEntity<GetEnrollmentDTO> createEnrollment(@RequestBody CreateEnrollmentDTO createEnrollmentDTO) {
        Optional<GetEnrollmentDTO> exam = enrollmentService.createEnrollment(createEnrollmentDTO);
        return exam.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
