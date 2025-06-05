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
public class CreateQuestionDto {
    private String text;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    @Min(value = 1, message = "Correct option must be between 1 and 4")
    @Max(value = 4, message = "Correct option must be between 1 and 4")
    private int correctOption;

    @Min(value = 1, message = "Time to answer must be at least 10 seconds")
    @Max(value = 600, message = "Time to answer must not exceed 600 seconds")
    private int timeToAnswer;

    private int marks;

    private Long examId;

    private Long teacherId;
}
