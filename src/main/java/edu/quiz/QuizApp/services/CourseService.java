package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.course.CreateCourseDto;
import edu.quiz.QuizApp.dtos.course.GetCourseDto;
import edu.quiz.QuizApp.dtos.course.UpdateCourseDto;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Optional<GetCourseDto> createCourse(CreateCourseDto createCourseDto);
    Optional<GetCourseDto>updateCourse(Long id,UpdateCourseDto updateCourseDto);
    Optional<List<GetCourseDto>> getAllCourses();
    Optional<GetCourseDto> getCourseById(long id);
    Optional<List<GetCourseDto>> getAllCoursesByUserId(long userId);
    Long totalCourseCount();
    Boolean deleteCourse(Long courseId);
}
