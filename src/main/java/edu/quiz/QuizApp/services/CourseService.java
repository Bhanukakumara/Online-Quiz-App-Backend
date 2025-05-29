package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.course.CreateCourseDto;
import edu.quiz.QuizApp.dtos.course.RequestCourseDto;
import edu.quiz.QuizApp.dtos.course.UpdateCourseDto;
import edu.quiz.QuizApp.entites.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course createCourse(CreateCourseDto createCourseDto);
    Optional<RequestCourseDto> getCourseById(Long id);
    List<RequestCourseDto> getAllCourses();
    RequestCourseDto updateCourse(Long id, UpdateCourseDto updateCourseDto);
    void deleteCourse(Long id);
    boolean existsById(Long id);
    List<RequestCourseDto> searchCoursesByName(String name);
}
