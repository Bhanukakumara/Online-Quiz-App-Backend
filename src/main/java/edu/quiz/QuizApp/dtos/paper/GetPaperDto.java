package edu.quiz.QuizApp.dtos.paper;

import edu.quiz.QuizApp.entites.Paper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPaperDto {
    private Long id;
    private String studentName;
    private String examName;
    private String courseName;
    private int attemptNumber;
    private int obtainedMarks;
    private int totalMarks;
    private Date startTime;
    private Date entTime;
    private List<Paper.StudentAnswer> studentAnswer;
}
