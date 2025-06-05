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
public class GetEnrollmentDTO {
    private Long id;
    private Long studentId;
    private Long examId;
    private LocalDate enrollmentDate;
}
