package edu.quiz.QuizApp.dtos.exam;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateExamDTO {
    private String title;
    private String description;
    private int questionCount;
    private int maxAttempts;
    private int totalTimeMinutes;
    private Long courseId;
    private Long teacherId;
}
