package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.course.CreateCourseDto;
import edu.quiz.QuizApp.dtos.course.GetCourseDto;
import edu.quiz.QuizApp.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
@CrossOrigin
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<GetCourseDto> createCourse(@RequestBody CreateCourseDto createCourseDto) {
        Optional<GetCourseDto> course = courseService.createCourse(createCourseDto);
        return course.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<GetCourseDto>> getAllCourses() {
        Optional<List<GetCourseDto>> courses = courseService.getAllCourses();
        return courses.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<GetCourseDto> getCourseById(@PathVariable long id) {
        Optional<GetCourseDto> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-by-userId/{userId}")
    public ResponseEntity<List<GetCourseDto>> getAllCoursesByUserId(@PathVariable long userId) {
        Optional<List<GetCourseDto>> courses = courseService.getAllCoursesByUserId(userId);
        return courses.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
