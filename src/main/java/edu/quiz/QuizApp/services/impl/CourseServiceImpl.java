package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.dtos.course.CreateCourseDto;
import edu.quiz.QuizApp.dtos.course.RequestCourseDto;
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

    @Override
    public Course createCourse(CreateCourseDto createCourseDto) {
        User admin = userRepository.findById(createCourseDto.getCreatedBy()).orElseThrow(() -> new RuntimeException("Admin user not found"));
        Course course = new Course();
        course.setName(createCourseDto.getName());
        course.setDescription(createCourseDto.getDescription());
        course.setCreatedBy(admin);
        return courseRepository.save(course);
    }

    @Override
    public Optional<RequestCourseDto> getCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        log.info(course.toString());
        if (course.isPresent()) {
            RequestCourseDto requestCourseDto = new RequestCourseDto();
            requestCourseDto.setId(course.get().getId());
            requestCourseDto.setName(course.get().getName());
            requestCourseDto.setDescription(course.get().getDescription());
            requestCourseDto.setCreatedBy(course.get().getCreatedBy().getName());
            return Optional.of(requestCourseDto);
        }
        return Optional.empty();
    }


    @Override
    public List<RequestCourseDto> getAllCourses() {
        List<RequestCourseDto> courses = new ArrayList<>();
        courseRepository.findAll().forEach(course -> {
            RequestCourseDto requestCourseDto = new RequestCourseDto();
            requestCourseDto.setId(course.getId());
            requestCourseDto.setName(course.getName());
            requestCourseDto.setDescription(course.getDescription());
            requestCourseDto.setCreatedBy(course.getCreatedBy().getName());
            courses.add(requestCourseDto);
        });
        return courses;
    }


    @Override
    public RequestCourseDto updateCourse(Long id, UpdateCourseDto updateCourseDto) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            Optional<User> admin = userRepository.findById(course.get().getCreatedBy().getId());
            if (admin.isPresent()) {
                course.get().setName(updateCourseDto.getName());
                course.get().setDescription(updateCourseDto.getDescription());
                course.get().setCreatedBy(admin.get());
                courseRepository.save(course.get());
                return new RequestCourseDto(
                        id,
                        updateCourseDto.getName(),
                        updateCourseDto.getDescription(),
                        admin.get().getName()
                );
            }
        }
        return null;
    }

    @Override
    public void deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }

    @Override
    public List<RequestCourseDto> searchCoursesByName(String name) {
        List<RequestCourseDto> courses = new ArrayList<>();
        courseRepository.findByNameContainingIgnoreCase(name).forEach(course -> {
            RequestCourseDto requestCourseDto = new RequestCourseDto();
            requestCourseDto.setId(course.getId());
            requestCourseDto.setName(course.getName());
            requestCourseDto.setDescription(course.getDescription());
            requestCourseDto.setCreatedBy(course.getCreatedBy().getName());
            courses.add(requestCourseDto);
        });
        return courses;
    }
}
