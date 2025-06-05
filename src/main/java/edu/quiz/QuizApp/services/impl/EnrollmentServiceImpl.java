package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.dtos.enrollment.GetEnrollmentDTO;
import edu.quiz.QuizApp.dtos.enrollment.CreateEnrollmentDTO;
import edu.quiz.QuizApp.entites.*;
import edu.quiz.QuizApp.exceptions.ResourceNotFoundException;
import edu.quiz.QuizApp.repositories.*;
import edu.quiz.QuizApp.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;
    private final PaperRepository paperRepository;
    private final ModelMapper modelMapper;

    public GetEnrollmentDTO enrollmentToGetEnrollmentDto(Enrollment enrollment) {
        return new GetEnrollmentDTO(
                enrollment.getId(),
                enrollment.getStudent().getId(),
                enrollment.getExam().getId(),
                enrollment.getEnrollmentDate()
        );
    }

    @Override
    public Optional<GetEnrollmentDTO> createEnrollment(CreateEnrollmentDTO createEnrollmentDTO) {
        Exam exam = examRepository.findById(createEnrollmentDTO.getExamId()).orElseThrow(() -> new ResourceNotFoundException("Exam not found"));
        User user = userRepository.findById(createEnrollmentDTO.getStudentId()).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        int attemptCount = enrollmentRepository.countByStudentIdAndExamId(user.getId(), exam.getId());
        if(attemptCount >= exam.getMaxAttempts()){
            return Optional.empty();
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setExam(exam);
        enrollment.setStudent(user);
        return Optional.of(enrollmentToGetEnrollmentDto(enrollmentRepository.save(enrollment)));
    }

    @Override
    public Enrollment getLastEnrollmentID(Long studentId, Long examId) {
            return enrollmentRepository.findTopByStudentIdAndExamIdOrderByIdDesc(studentId, examId).orElse(null);
    }
}
