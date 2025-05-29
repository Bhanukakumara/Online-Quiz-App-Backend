package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.course.CreateCourseDto;
import edu.quiz.QuizApp.dtos.course.RequestCourseDto;
import edu.quiz.QuizApp.dtos.course.UpdateCourseDto;
import edu.quiz.QuizApp.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/course")
@RequiredArgsConstructor
@CrossOrigin
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<CreateCourseDto> createCourse(@RequestBody CreateCourseDto createCourseDto) {
        courseService.createCourse(createCourseDto);
        return ResponseEntity.ok(createCourseDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<RequestCourseDto>> getAllCourses() {
        List<RequestCourseDto> allCourses = courseService.getAllCourses();
        return ResponseEntity.ok(allCourses);
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<RequestCourseDto> getCourseById(@PathVariable Long id) {
        Optional<RequestCourseDto> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update-by-id/{id}")
    public ResponseEntity<RequestCourseDto> updateCourse(@PathVariable Long id, @RequestBody UpdateCourseDto updateCourseDto) {
        try {
            return ResponseEntity.ok(courseService.updateCourse(id, updateCourseDto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (courseService.existsById(id)) {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<RequestCourseDto>> searchCourses(@RequestParam String name) {
        return ResponseEntity.ok(courseService.searchCoursesByName(name));
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> courseExists(@PathVariable Long id) {
        boolean exists = courseService.existsById(id);
        return ResponseEntity.ok(exists);
    }
}
