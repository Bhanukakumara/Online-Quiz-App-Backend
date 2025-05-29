package edu.quiz.QuizApp.dtos.exam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamResponseDTO {
    private Long id;
    private String title;
    private String description;
    private int questionCount;
    private int maxAttempts;
    private int totalTimeMinutes;
    private Long courseId;
    private String courseName;
    private Long teacherId;
    private String teacherName;
    private int totalQuestions;
    private int totalEnrollments;
    private int totalPapers;
}
