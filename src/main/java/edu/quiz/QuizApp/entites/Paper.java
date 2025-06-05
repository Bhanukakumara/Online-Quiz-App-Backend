package edu.quiz.QuizApp.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
@Table(name = "papers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "total_marks")
    private int totalMarks;

    @Column(name = "obtained_marks")
    private int obtainedMarks;

    @Column(name = "attempt_number", nullable = false)
    private int attemptNumber;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "student_answers", columnDefinition = "json")
    private List<StudentAnswer> studentAnswers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentAnswer {
        private Long questionId;
        private Integer givenAnswer;
    }
}
