package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.enrollment.EnrollmentDTO;
import edu.quiz.QuizApp.dtos.enrollment.EnrollmentRequestDTO;
import edu.quiz.QuizApp.dtos.enrollment.EnrollmentResponseDTO;

import java.util.List;
public interface EnrollmentService {
    EnrollmentResponseDTO createEnrollment(EnrollmentRequestDTO enrollmentRequestDTO);
    EnrollmentResponseDTO getEnrollmentById(Long id);
    List<EnrollmentResponseDTO> getAllEnrollments();
    List<EnrollmentResponseDTO> getEnrollmentsByStudent(Long studentId);
    List<EnrollmentResponseDTO> getEnrollmentsByCourse(Long courseId);
    List<EnrollmentResponseDTO> getEnrollmentsByExam(Long examId);
    EnrollmentResponseDTO updateEnrollment(Long id, EnrollmentDTO enrollmentDTO);
    void deleteEnrollment(Long id);
    boolean isStudentEnrolled(Long studentId, Long examId);
    Integer getRemainingAttempts(Long studentId, Long examId);
}
