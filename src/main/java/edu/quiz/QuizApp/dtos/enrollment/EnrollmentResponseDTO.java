package edu.quiz.QuizApp.dtos.enrollment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponseDTO {
    private Long id;
    private String studentName;
    private String courseName;
    private String examTitle;
    private LocalDate enrollmentDate;
    private Integer activePapers;
}
