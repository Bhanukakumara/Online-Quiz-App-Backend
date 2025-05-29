package edu.quiz.QuizApp.dtos.paper_question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaperQuestionDTO {
    private Long id;
    private Integer selectedOption;
    private Boolean isCorrect;
    private Long questionId;
}
