package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.ResourceNotFoundException;
import edu.quiz.QuizApp.dtos.course.CreateCourseDto;
import edu.quiz.QuizApp.dtos.course.GetCourseDto;
import edu.quiz.QuizApp.dtos.course.UpdateCourseDto;
import edu.quiz.QuizApp.entites.Course;
import edu.quiz.QuizApp.entites.User;
import edu.quiz.QuizApp.repositories.CourseRepository;
import edu.quiz.QuizApp.repositories.UserRepository;
import edu.quiz.QuizApp.services.CourseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public GetCourseDto courseToGetCourseDto(Course course) {
        return new GetCourseDto(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getUser().getName());
    }

    @Override
    public Optional<GetCourseDto> createCourse(CreateCourseDto createCourseDto) {
        User admin = userRepository.findById(createCourseDto.getAdminId()).orElseThrow(() -> new RuntimeException("Admin user not found"));
        Course course = new Course();
        course.setName(createCourseDto.getName());
        course.setDescription(createCourseDto.getDescription());
        course.setUser(admin);
        return Optional.of(courseToGetCourseDto(courseRepository.save(course)));
    }

    @Override
    public Optional<List<GetCourseDto>> getAllCourses() {
        List<GetCourseDto> courses = new ArrayList<>();
        courseRepository.findAll().forEach(course -> courses.add(courseToGetCourseDto(course)));
        return Optional.of(courses);
    }

    @Override
    public Optional<GetCourseDto> getCourseById(long id) {
        return courseRepository.findById(id).map(this::courseToGetCourseDto);
    }

    @Override
    public Optional<List<GetCourseDto>> getAllCoursesByUserId(long userId) {
        List<GetCourseDto> courses = new ArrayList<>();
        courseRepository.findByUserId(userId).forEach(course -> courses.add(courseToGetCourseDto(course)));
        return Optional.of(courses);
    }

    @Override
    public Long totalCourseCount() {
        return courseRepository.count();
    }

    @Override
    public Optional<GetCourseDto> updateCourse(Long id,UpdateCourseDto updateCourseDto) {
         Course course = courseRepository.findById(updateCourseDto.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + updateCourseDto.getId()));

    User admin = userRepository.findById(updateCourseDto.getAdminId())
            .orElseThrow(() -> new ResourceNotFoundException("Admin user not found with id: " + updateCourseDto.getAdminId()));

    course.setName(updateCourseDto.getName());
    course.setDescription(updateCourseDto.getDescription());
    course.setUser(admin);

    courseRepository.save(course);

    return getCourseById(id);

    }

    @Override
    public Boolean deleteCourse(Long courseId) {
         Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

    courseRepository.delete(course);
    return true;
    }

}
