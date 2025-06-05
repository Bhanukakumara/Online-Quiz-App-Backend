package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.dtos.paper.CreatePaperDto;
import edu.quiz.QuizApp.entites.Enrollment;
import edu.quiz.QuizApp.entites.Paper;
import edu.quiz.QuizApp.entites.User;
import edu.quiz.QuizApp.repositories.EnrollmentRepository;
import edu.quiz.QuizApp.repositories.PaperRepository;
import edu.quiz.QuizApp.services.EnrollmentService;
import edu.quiz.QuizApp.services.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaperServiceImpl implements PaperService {
    private final PaperRepository paperRepository;
    private final EnrollmentService enrollmentService;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public void savePaper(CreatePaperDto createPaperDto) {
        Enrollment lastEnrollmentID = enrollmentService.getLastEnrollmentID(createPaperDto.getStudentId(), createPaperDto.getExamId());
        Paper paper = new Paper();
        paper.setStartTime(createPaperDto.getStartTime());
        paper.setEndTime(createPaperDto.getEndTime());
        paper.setTotalMarks(createPaperDto.getTotalMarks());
        paper.setObtainedMarks(createPaperDto.getObtainedMarks());
        paper.setAttemptNumber(enrollmentRepository.countByStudentIdAndExamId(createPaperDto.getStudentId(), createPaperDto.getExamId()));
        paper.setStudentAnswers(createPaperDto.getStudentAnswers());
        paper.setEnrollment(lastEnrollmentID);
        paperRepository.save(paper);
    }
}
