package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.ResourceNotFoundException;
import edu.quiz.QuizApp.dtos.exam.ExamCreateDTO;
import edu.quiz.QuizApp.dtos.exam.ExamResponseDTO;
import edu.quiz.QuizApp.dtos.exam.ExamUpdateDTO;
import edu.quiz.QuizApp.entites.Course;
import edu.quiz.QuizApp.entites.Exam;
import edu.quiz.QuizApp.entites.User;
import edu.quiz.QuizApp.repositories.CourseRepository;
import edu.quiz.QuizApp.repositories.ExamRepository;
import edu.quiz.QuizApp.repositories.UserRepository;
import edu.quiz.QuizApp.services.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public ExamResponseDTO createExam(ExamCreateDTO examCreateDTO) {
        // Validate course exists
        Course course = courseRepository.findById(examCreateDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + examCreateDTO.getCourseId()));

        // Validate teacher exists
        User teacher = userRepository.findById(examCreateDTO.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + examCreateDTO.getTeacherId()));

        Exam exam = new Exam();
        exam.setTitle(examCreateDTO.getTitle());
        exam.setDescription(examCreateDTO.getDescription());
        exam.setQuestionCount(examCreateDTO.getQuestionCount());
        exam.setMaxAttempts(examCreateDTO.getMaxAttempts());
        exam.setTotalTimeMinutes(examCreateDTO.getTotalTimeMinutes());
        exam.setCourse(course);
        exam.setTeacher(teacher);

        Exam savedExam = examRepository.save(exam);
        return mapToResponseDTO(savedExam);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamResponseDTO getExamById(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));
        return mapToResponseDTO(exam);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResponseDTO> getAllExams() {
        return examRepository.findAll().stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResponseDTO> getExamsByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with id: " + courseId);
        }

        List<Exam> exams = examRepository.findByCourseId(courseId);
        return exams.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResponseDTO> getExamsByTeacherId(Long teacherId) {
        if (!userRepository.existsById(teacherId)) {
            throw new ResourceNotFoundException("Teacher not found with id: " + teacherId);
        }

        List<Exam> exams = examRepository.findByTeacherId(teacherId);
        return exams.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExamResponseDTO updateExam(Long id, ExamUpdateDTO examUpdateDTO) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));

        // Update only non-null fields
        if (examUpdateDTO.getTitle() != null && !examUpdateDTO.getTitle().trim().isEmpty()) {
            exam.setTitle(examUpdateDTO.getTitle());
        }

        if (examUpdateDTO.getDescription() != null && !examUpdateDTO.getDescription().trim().isEmpty()) {
            exam.setDescription(examUpdateDTO.getDescription());
        }

        if (examUpdateDTO.getQuestionCount() != null) {
            exam.setQuestionCount(examUpdateDTO.getQuestionCount());
        }

        if (examUpdateDTO.getMaxAttempts() != null) {
            exam.setMaxAttempts(examUpdateDTO.getMaxAttempts());
        }

        if (examUpdateDTO.getTotalTimeMinutes() != null) {
            exam.setTotalTimeMinutes(examUpdateDTO.getTotalTimeMinutes());
        }

        Exam updatedExam = examRepository.save(exam);
        return mapToResponseDTO(updatedExam);
    }

    @Override
    public void deleteExam(Long id) {
        if (!examRepository.existsById(id)) {
            throw new ResourceNotFoundException("Exam not found with id: " + id);
        }
        examRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return examRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long getExamCountByCourseId(Long courseId) {
        return examRepository.countByCourseId(courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getExamCountByTeacherId(Long teacherId) {
        return examRepository.countByTeacherId(teacherId);
    }

    private ExamResponseDTO mapToResponseDTO(Exam exam) {
        ExamResponseDTO dto = new ExamResponseDTO();
        dto.setId(exam.getId());
        dto.setTitle(exam.getTitle());
        dto.setDescription(exam.getDescription());
        dto.setQuestionCount(exam.getQuestionCount());
        dto.setMaxAttempts(exam.getMaxAttempts());
        dto.setTotalTimeMinutes(exam.getTotalTimeMinutes());

        if (exam.getCourse() != null) {
            dto.setCourseId(exam.getCourse().getId());
            dto.setCourseName(exam.getCourse().getName()); // Assuming Course has getName() method
        }

        if (exam.getTeacher() != null) {
            dto.setTeacherId(exam.getTeacher().getId());
            dto.setTeacherName(exam.getTeacher().getName()); // Assuming User has getFullName() method
        }

        dto.setTotalQuestions(exam.getQuestions() != null ? exam.getQuestions().size() : 0);
        dto.setTotalEnrollments(exam.getExamEnrollments() != null ? exam.getExamEnrollments().size() : 0);
        dto.setTotalPapers(exam.getPapers() != null ? exam.getPapers().size() : 0);

        return dto;
    }
}
