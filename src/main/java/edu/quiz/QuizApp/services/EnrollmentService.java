package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.enrollment.GetEnrollmentDTO;
import edu.quiz.QuizApp.dtos.enrollment.CreateEnrollmentDTO;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    Optional<GetEnrollmentDTO> createEnrollment(CreateEnrollmentDTO createEnrollmentDTO);
}
