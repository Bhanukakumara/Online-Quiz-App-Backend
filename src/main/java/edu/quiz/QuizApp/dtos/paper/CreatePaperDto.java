package edu.quiz.QuizApp.dtos.paper;

import edu.quiz.QuizApp.dtos.paper_question.PaperQuestionDTO;
import edu.quiz.QuizApp.entites.Paper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaperDto {
    private Long studentId;
    private Long examId;
    private int totalMarks;
    private Date endTime;
    private Date startTime;
    private List<Paper.StudentAnswer> studentAnswers;
}
