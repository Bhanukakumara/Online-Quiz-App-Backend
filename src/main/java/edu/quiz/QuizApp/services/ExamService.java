package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.exam.CreateExamDTO;
import edu.quiz.QuizApp.dtos.exam.GetExamDTO;

import java.util.List;
import java.util.Optional;

public interface ExamService {
    Optional<GetExamDTO> createExam(CreateExamDTO createExamDTO);
    Optional<List<GetExamDTO>> getAllExams();
    Optional<GetExamDTO> getExamById(Long id);
    Optional<List<GetExamDTO>> getExamByCourseId(Long courseId);
    Optional<List<GetExamDTO>> getExamByTeacherId(Long teacherId);

}
