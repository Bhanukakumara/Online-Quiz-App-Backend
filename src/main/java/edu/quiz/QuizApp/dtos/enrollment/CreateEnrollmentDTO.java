package edu.quiz.QuizApp.dtos.enrollment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEnrollmentDTO {
    private Long studentId;
    private Long examId;
}
