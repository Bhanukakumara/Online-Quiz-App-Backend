package edu.quiz.QuizApp.dtos.paper;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaperRequestDTO {
    private Date startTime;
    private Date endTime;
    private int obtainedMarks;
    private Long studentId;
    private Long examId;
    private Long enrollmentId;
}
