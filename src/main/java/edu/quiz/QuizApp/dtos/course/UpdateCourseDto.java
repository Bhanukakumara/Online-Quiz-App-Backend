package edu.quiz.QuizApp.dtos.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourseDto {
    private Long id; 
    private String name;
    private String description;
    private Long adminId;
    
}
