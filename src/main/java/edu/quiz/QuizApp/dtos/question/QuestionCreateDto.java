package edu.quiz.QuizApp.dtos.question;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreateDto {
    @NotBlank(message = "Question text cannot be blank")
    @Size(min = 10, max = 1000, message = "Question text must be between 10 and 1000 characters")
    private String text;

    @NotBlank(message = "Option 1 cannot be blank")
    @Size(max = 255, message = "Option 1 must not exceed 255 characters")
    private String option1;

    @NotBlank(message = "Option 2 cannot be blank")
    @Size(max = 255, message = "Option 2 must not exceed 255 characters")
    private String option2;

    @NotBlank(message = "Option 3 cannot be blank")
    @Size(max = 255, message = "Option 3 must not exceed 255 characters")
    private String option3;

    @NotBlank(message = "Option 4 cannot be blank")
    @Size(max = 255, message = "Option 4 must not exceed 255 characters")
    private String option4;

    @Min(value = 1, message = "Correct option must be between 1 and 4")
    @Max(value = 4, message = "Correct option must be between 1 and 4")
    private int correctOption;

    @Min(value = 10, message = "Time to answer must be at least 10 seconds")
    @Max(value = 600, message = "Time to answer must not exceed 600 seconds")
    private int timeToAnswer;

    @NotNull(message = "Exam ID cannot be null")
    @Positive(message = "Exam ID must be positive")
    private Long examId;
}
