package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.dtos.enrollment.EnrollmentDTO;
import edu.quiz.QuizApp.dtos.enrollment.EnrollmentRequestDTO;
import edu.quiz.QuizApp.dtos.enrollment.EnrollmentResponseDTO;
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

    @Override
    public EnrollmentResponseDTO createEnrollment(EnrollmentRequestDTO enrollmentRequestDTO) {
        User student = userRepository.findById(enrollmentRequestDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Course course = courseRepository.findById(enrollmentRequestDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Exam exam = examRepository.findById(enrollmentRequestDTO.getExamId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        // Check if exam belongs to course
        if (exam.getCourse().getId().equals(course.getId())) {
            throw new IllegalArgumentException("Exam does not belong to the specified course");
        }

        // Check if student is already enrolled
        if (enrollmentRepository.existsByStudentIdAndExamId(student.getId(), exam.getId())) {
            throw new IllegalArgumentException("Student is already enrolled in this exam");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setExam(exam);
        enrollment.setEnrollmentDate(LocalDate.now());

        return convertToResponseDTO(enrollmentRepository.save(enrollment));
    }

    @Override
    public EnrollmentResponseDTO getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
        return convertToResponseDTO(enrollment);
    }

    @Override
    public List<EnrollmentResponseDTO> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentResponseDTO> getEnrollmentsByStudent(Long studentId) {
        List<Enrollment> byStudentId = enrollmentRepository.findByStudentId(studentId);
        List<EnrollmentResponseDTO> enrollmentResponseDTOS = new ArrayList<>();
        byStudentId.forEach(enrollment -> {
            enrollmentResponseDTOS.add(convertToResponseDTO(enrollment));
        });
        return enrollmentResponseDTOS;
    }

    @Override
    public List<EnrollmentResponseDTO> getEnrollmentsByCourse(Long courseId) {
        List<Enrollment> byCourseId = enrollmentRepository.findByCourseId(courseId);
        List<EnrollmentResponseDTO> enrollmentResponseDTOS = new ArrayList<>();
        byCourseId.forEach(enrollment -> {
            enrollmentResponseDTOS.add(convertToResponseDTO(enrollment));
        });
        return enrollmentResponseDTOS;
    }

    @Override
    public List<EnrollmentResponseDTO> getEnrollmentsByExam(Long examId) {
        List<Enrollment> byExamId = enrollmentRepository.findByExamId(examId);
        List<EnrollmentResponseDTO> enrollmentResponseDTOS = new ArrayList<>();
        byExamId.forEach(enrollment -> {
            enrollmentResponseDTOS.add(convertToResponseDTO(enrollment));
        });
        return enrollmentResponseDTOS;
    }

    @Override
    public EnrollmentResponseDTO updateEnrollment(Long id, EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));

        modelMapper.map(enrollmentDTO, enrollment);
        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        return convertToResponseDTO(updatedEnrollment);
    }

    @Override
    public void deleteEnrollment(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
        enrollmentRepository.delete(enrollment);
    }

    @Override
    public boolean isStudentEnrolled(Long studentId, Long examId) {
        return enrollmentRepository.existsByStudentIdAndExamId(studentId, examId);
    }

    @Override
    public Integer getRemainingAttempts(Long studentId, Long examId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndExamId(studentId, examId);
        Exam exam = enrollment.getExam();
        int maxAttempts = exam.getMaxAttempts();
        int usedAttempts = paperRepository.countByStudentIdAndExamId(studentId, examId);

        return Math.max(0, maxAttempts - usedAttempts);
    }

    private EnrollmentResponseDTO convertToResponseDTO(Enrollment enrollment) {
        EnrollmentResponseDTO responseDTO = modelMapper.map(enrollment, EnrollmentResponseDTO.class);
        responseDTO.setStudentName(enrollment.getStudent().getName());
        responseDTO.setCourseName(enrollment.getCourse().getName());
        responseDTO.setExamTitle(enrollment.getExam().getTitle());
        responseDTO.setEnrollmentDate(enrollment.getEnrollmentDate());
        responseDTO.setActivePapers(paperRepository.countByEnrollmentId(enrollment.getId()));
        return responseDTO;
    }
}
