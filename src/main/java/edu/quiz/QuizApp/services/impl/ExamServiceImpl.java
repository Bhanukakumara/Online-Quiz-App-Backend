package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.dtos.exam.CreateExamDTO;
import edu.quiz.QuizApp.dtos.exam.GetExamDTO;
import edu.quiz.QuizApp.entites.Course;
import edu.quiz.QuizApp.entites.Exam;
import edu.quiz.QuizApp.entites.User;
import edu.quiz.QuizApp.repositories.CourseRepository;
import edu.quiz.QuizApp.repositories.ExamRepository;
import edu.quiz.QuizApp.repositories.UserRepository;
import edu.quiz.QuizApp.services.ExamService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public GetExamDTO examToGetExamDTO(Exam exam) {
        return new GetExamDTO(
                exam.getId(),
                exam.getTitle(),
                exam.getDescription(),
                exam.getQuestionCount(),
                exam.getMaxAttempts(),
                exam.getCourse().getId(),
                exam.getCourse().getName(),
                exam.getUser().getId(),
                exam.getUser().getName()
        );
    }


    @Override
    public Optional<GetExamDTO> createExam(CreateExamDTO createExamDTO) {
        Course course = courseRepository.findById(createExamDTO.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found"));
        User user = userRepository.findById(createExamDTO.getTeacherId()).orElseThrow(() -> new RuntimeException("Teacher not found"));
        Exam exam = new Exam();
        exam.setTitle(createExamDTO.getTitle());
        exam.setDescription(createExamDTO.getDescription());
        exam.setQuestionCount(createExamDTO.getQuestionCount());
        exam.setMaxAttempts(createExamDTO.getMaxAttempts());
        exam.setCourse(course);
        exam.setUser(user);
        return Optional.of(examToGetExamDTO(examRepository.save(exam)));
    }

    @Override
    public Optional<List<GetExamDTO>> getAllExams() {
        List<GetExamDTO> exams = new ArrayList<>();
        examRepository.findAll().forEach(exam -> {
            exams.add(examToGetExamDTO(exam));
        });
        return Optional.of(exams);
    }

    @Override
    public Optional<GetExamDTO> getExamById(Long id) {
        return examRepository.findById(id).map(this::examToGetExamDTO);
    }

    @Override
    public Optional<List<GetExamDTO>> getExamByCourseId(Long courseId) {
        List<GetExamDTO> exams = new ArrayList<>();
        examRepository.findAllByCourseId(courseId).forEach(exam -> exams.add(examToGetExamDTO(exam)));
        return Optional.of(exams);
    }

    @Override
    public Optional<List<GetExamDTO>> getExamByTeacherId(Long teacherId) {
        List<GetExamDTO> exams = new ArrayList<>();
        examRepository.findAllByUserId(teacherId).forEach(exam -> exams.add(examToGetExamDTO(exam)));
        return Optional.of(exams);
    }

    @Override
    public Long totalExamCount() {
        return examRepository.count();
    }
}
