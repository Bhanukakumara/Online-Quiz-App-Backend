package edu.quiz.QuizApp.dtos.question;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionUpdateDto {
    @Size(min = 10, max = 1000, message = "Question text must be between 10 and 1000 characters")
    private String text;

    @Size(max = 255, message = "Option 1 must not exceed 255 characters")
    private String option1;

    @Size(max = 255, message = "Option 2 must not exceed 255 characters")
    private String option2;

    @Size(max = 255, message = "Option 3 must not exceed 255 characters")
    private String option3;

    @Size(max = 255, message = "Option 4 must not exceed 255 characters")
    private String option4;

    @Min(value = 1, message = "Correct option must be between 1 and 4")
    @Max(value = 4, message = "Correct option must be between 1 and 4")
    private Integer correctOption;

    @Min(value = 10, message = "Time to answer must be at least 10 seconds")
    @Max(value = 600, message = "Time to answer must not exceed 600 seconds")
    private Integer timeToAnswer;
}
