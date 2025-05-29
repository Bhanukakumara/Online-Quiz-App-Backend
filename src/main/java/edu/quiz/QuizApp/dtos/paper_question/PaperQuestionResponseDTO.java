package edu.quiz.QuizApp.dtos.paper_question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaperQuestionResponseDTO {
    private Long id;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private Integer selectedOption;
    private Boolean isCorrect;
    private Integer correctOption;
    private Integer timeToAnswer;
}
