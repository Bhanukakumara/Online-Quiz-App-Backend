package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.enrollment.GetEnrollmentDTO;
import edu.quiz.QuizApp.dtos.enrollment.CreateEnrollmentDTO;
import edu.quiz.QuizApp.entites.Enrollment;
import edu.quiz.QuizApp.entites.Exam;
import edu.quiz.QuizApp.entites.User;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    Optional<GetEnrollmentDTO> createEnrollment(CreateEnrollmentDTO createEnrollmentDTO);
    Enrollment getLastEnrollmentID(Long studentId, Long examId);
}
