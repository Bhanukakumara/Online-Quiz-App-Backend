package edu.quiz.QuizApp.dtos.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetQuestionDto {
    private Long id;
    private String text;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctOption;
    private int timeToAnswer;
    private int marks;
    private Long examId;
    private String examTitle;
    private Long teacherId;
}
