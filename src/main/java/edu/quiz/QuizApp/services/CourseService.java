package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.course.CreateCourseDto;
import edu.quiz.QuizApp.dtos.course.GetCourseDto;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Optional<GetCourseDto> createCourse(CreateCourseDto createCourseDto);
    Optional<List<GetCourseDto>> getAllCourses();
    Optional<GetCourseDto> getCourseById(long id);
    Optional<List<GetCourseDto>> getAllCoursesByUserId(long userId);
}
