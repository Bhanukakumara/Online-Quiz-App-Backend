package edu.quiz.QuizApp.dtos.paper;

import edu.quiz.QuizApp.dtos.paper_question.PaperQuestionResponseDTO;
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
public class PaperResponseDTO {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalMarks;
    private Double obtainedMarks;
    private Integer attemptNumber;
    private String studentName;
    private String examTitle;
    private Set<PaperQuestionResponseDTO> questions;
}
