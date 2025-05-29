package edu.quiz.QuizApp.dtos.exam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamSummaryDTO {
    private Long id;
    private String title;
    private String description;
    private int questionCount;
    private int maxAttempts;
    private int totalTimeMinutes;
    private String courseName;
    private String teacherName;
}
