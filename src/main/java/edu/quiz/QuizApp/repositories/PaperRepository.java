package edu.quiz.QuizApp.repositories;

import edu.quiz.QuizApp.entites.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {
}
