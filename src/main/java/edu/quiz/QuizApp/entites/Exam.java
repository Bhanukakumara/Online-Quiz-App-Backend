package edu.quiz.QuizApp.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exams")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "question_count", nullable = false)
    private int questionCount;

    @Column(name = "max_attempts", nullable = false)
    private int maxAttempts;

    @Column(name = "total_time_minutes", nullable = false)
    private int totalTimeMinutes;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private Set<Enrollment> examEnrollments = new HashSet<>();

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private Set<Paper> papers = new HashSet<>();
}
