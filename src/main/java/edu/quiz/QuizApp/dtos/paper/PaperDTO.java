package edu.quiz.QuizApp.dtos.paper;

import edu.quiz.QuizApp.dtos.paper_question.PaperQuestionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaperDTO {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalMarks;
    private Double obtainedMarks;
    private Integer attemptNumber;
    private Long studentId;
    private Long examId;
    private Long enrollmentId;
    private Set<PaperQuestionDTO> paperQuestions;
}
