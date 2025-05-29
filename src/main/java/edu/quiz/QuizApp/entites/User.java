package edu.quiz.QuizApp.entites;

import edu.quiz.QuizApp.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    // Teacher-specific relationships
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<Exam> teacherExams = new HashSet<>();

    // Student-specific relationships
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Enrollment> studentEnrollments = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Paper> studentPapers = new HashSet<>();

    // Admin-specific relationships
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private Set<Course> adminCourses = new HashSet<>();
}
