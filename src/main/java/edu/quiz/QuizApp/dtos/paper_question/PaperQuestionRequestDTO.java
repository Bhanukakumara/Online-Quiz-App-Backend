package edu.quiz.QuizApp.dtos.paper_question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaperQuestionRequestDTO {
    private Integer selectedOption;
    private Long paperId;
    private Long questionId;
}
