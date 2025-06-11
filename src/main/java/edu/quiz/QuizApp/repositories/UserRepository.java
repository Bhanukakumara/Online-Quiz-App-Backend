package edu.quiz.QuizApp.repositories;

import edu.quiz.QuizApp.entites.User;
import edu.quiz.QuizApp.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findByUserRole(UserRole userRole);

    Long countByUserRole(UserRole userRole);
}
