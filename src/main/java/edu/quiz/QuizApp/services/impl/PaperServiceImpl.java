package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.dtos.paper.CreatePaperDto;
import edu.quiz.QuizApp.dtos.question.GetQuestionDto;
import edu.quiz.QuizApp.entites.Enrollment;
import edu.quiz.QuizApp.entites.Paper;
import edu.quiz.QuizApp.entites.User;
import edu.quiz.QuizApp.repositories.EnrollmentRepository;
import edu.quiz.QuizApp.repositories.PaperRepository;
import edu.quiz.QuizApp.services.EnrollmentService;
import edu.quiz.QuizApp.services.ExamService;
import edu.quiz.QuizApp.services.PaperService;
import edu.quiz.QuizApp.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaperServiceImpl implements PaperService {
    private final PaperRepository paperRepository;
    private final EnrollmentService enrollmentService;
    private final EnrollmentRepository enrollmentRepository;
    private final ExamService examService;
    private final QuestionService questionService;

    @Override
    public void savePaper(CreatePaperDto createPaperDto) {
        Enrollment lastEnrollmentID = enrollmentService.getLastEnrollmentID(createPaperDto.getStudentId(), createPaperDto.getExamId());
        Paper paper = new Paper();
        paper.setStartTime(createPaperDto.getStartTime());
        paper.setEndTime(createPaperDto.getEndTime());
        paper.setTotalMarks(createPaperDto.getTotalMarks());
        paper.setAttemptNumber(enrollmentRepository.countByStudentIdAndExamId(createPaperDto.getStudentId(), createPaperDto.getExamId()));
        List<Paper.StudentAnswer> studentAnswers = createPaperDto.getStudentAnswers();
        paper.setStudentAnswers(studentAnswers);
        int obtainedMarks = 0;
        for (Paper.StudentAnswer studentAnswer : studentAnswers) {
            Optional<GetQuestionDto> questionById = questionService.getQuestionById(studentAnswer.getQuestionId());
            if (questionById.isPresent()) {
                GetQuestionDto getQuestionDto = questionById.get();
                int correctOption = getQuestionDto.getCorrectOption();
                Integer givenAnswer = studentAnswer.getGivenAnswer();
                if (givenAnswer != null && correctOption == givenAnswer) {
                    obtainedMarks += getQuestionDto.getMarks();
                }
            }
        }
        paper.setObtainedMarks(obtainedMarks);
        paper.setEnrollment(lastEnrollmentID);
        paperRepository.save(paper);
    }
}
