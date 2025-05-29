package edu.quiz.QuizApp.dtos.exam;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamUpdateDTO {
    private String title;

    private String description;

    @Min(value = 1, message = "Question count must be at least 1")
    private Integer questionCount;

    @Min(value = 1, message = "Max attempts must be at least 1")
    private Integer maxAttempts;

    @Min(value = 1, message = "Total time must be at least 1 minute")
    private Integer totalTimeMinutes;
}
