package edu.quiz.QuizApp.repositories;

import edu.quiz.QuizApp.entites.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {
    @Query(value = "SELECT DATE_FORMAT(end_time, '%Y-%m-%d %H:%i') as time_slot, COUNT(*) as count " +
            "FROM papers " +
            "WHERE end_time BETWEEN :startTime AND :endTime " +
            "AND end_time IS NOT NULL " +
            "GROUP BY DATE_FORMAT(end_time, '%Y-%m-%d %H:%i') " +
            "ORDER BY time_slot", nativeQuery = true)
    List<Object[]> findSubmissionsByMinuteInterval(@Param("startTime") Date startTime,
                                                   @Param("endTime") Date endTime);

    @Query("SELECT COUNT(p) FROM Paper p WHERE p.endTime BETWEEN :startTime AND :endTime AND p.endTime IS NOT NULL")
    Long countSubmissionsBetweenDates(@Param("startTime") Date startTime,
                                      @Param("endTime") Date endTime);
}
