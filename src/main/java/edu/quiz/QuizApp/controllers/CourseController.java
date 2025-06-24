package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.ResourceNotFoundException;
import edu.quiz.QuizApp.dtos.course.CreateCourseDto;
import edu.quiz.QuizApp.dtos.course.GetCourseDto;
import edu.quiz.QuizApp.dtos.course.UpdateCourseDto;
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

    @GetMapping("/total-count")
    public ResponseEntity<Long> getTotalCount() {
        return ResponseEntity.ok(courseService.totalCourseCount());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GetCourseDto>updateCourse(@PathVariable Long id,@RequestBody UpdateCourseDto updateCourseDto){
        Optional<GetCourseDto>updatedCourse =courseService.updateCourse(id,updateCourseDto);
        return updatedCourse.map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void>deleteCourse(@PathVariable  Long id){
            try{
                  boolean deleted =  courseService.deleteCourse(id);
                  return deleted ? ResponseEntity.ok().build():ResponseEntity.badRequest().build();
            }catch(ResourceNotFoundException ex){
                return ResponseEntity.notFound().build();
            }
    }
}
