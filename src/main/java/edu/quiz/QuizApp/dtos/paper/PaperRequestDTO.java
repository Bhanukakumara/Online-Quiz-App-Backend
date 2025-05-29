package edu.quiz.QuizApp.dtos.paper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaperRequestDTO {
    private Long studentId;
    private Long examId;
    private Long enrollmentId;
    private Integer attemptNumber;
}
