package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.exam.ExamCreateDTO;
import edu.quiz.QuizApp.dtos.exam.ExamResponseDTO;
import edu.quiz.QuizApp.dtos.exam.ExamUpdateDTO;

import java.util.List;

public interface ExamService {
    ExamResponseDTO createExam(ExamCreateDTO examCreateDTO);
    ExamResponseDTO getExamById(Long id);
    List<ExamResponseDTO> getAllExams();
    List<ExamResponseDTO> getExamsByCourseId(Long courseId);
    List<ExamResponseDTO> getExamsByTeacherId(Long teacherId);
    ExamResponseDTO updateExam(Long id, ExamUpdateDTO examUpdateDTO);
    void deleteExam(Long id);
    boolean existsById(Long id);
    long getExamCountByCourseId(Long courseId);
    long getExamCountByTeacherId(Long teacherId);
}
