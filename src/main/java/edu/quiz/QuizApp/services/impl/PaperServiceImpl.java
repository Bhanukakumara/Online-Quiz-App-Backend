package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.dtos.paper.PaperDTO;
import edu.quiz.QuizApp.dtos.paper.PaperRequestDTO;
import edu.quiz.QuizApp.dtos.paper.PaperResponseDTO;
import edu.quiz.QuizApp.dtos.paper_question.PaperQuestionResponseDTO;
import edu.quiz.QuizApp.entites.*;
import edu.quiz.QuizApp.exceptions.ResourceNotFoundException;
import edu.quiz.QuizApp.repositories.ExamRepository;
import edu.quiz.QuizApp.repositories.PaperRepository;
import edu.quiz.QuizApp.repositories.QuestionRepository;
import edu.quiz.QuizApp.repositories.UserRepository;
import edu.quiz.QuizApp.repositories.*;
import edu.quiz.QuizApp.services.PaperService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PaperServiceImpl implements PaperService {
    private final PaperRepository paperRepository;
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final QuestionRepository questionRepository;
    private final PaperQuestionRepository paperQuestionRepository;
    private final ModelMapper modelMapper;

    @Override
    public PaperResponseDTO createPaper(PaperRequestDTO paperRequestDTO) {
        User student = userRepository.findById(paperRequestDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Exam exam = examRepository.findById(paperRequestDTO.getExamId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        Enrollment enrollment = enrollmentRepository.findById(paperRequestDTO.getEnrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));

        // Validate exam belongs to enrollment
        if (enrollment.getExam().getId().equals(exam.getId())) {
            throw new IllegalArgumentException("Exam does not belong to the enrollment");
        }

        // Check attempt limit
        int currentAttempt = paperRequestDTO.getAttemptNumber();
        int maxAttempts = exam.getMaxAttempts();
        if (currentAttempt > maxAttempts) {
            throw new IllegalArgumentException("Maximum attempt limit reached");
        }

        // Get total available questions for this exam
        Long totalAvailableQuestions = questionRepository.countByExamId(exam.getId());
        int requiredQuestions = exam.getQuestionCount();

        // Validate enough questions exist
        if (totalAvailableQuestions < requiredQuestions) {
            throw new IllegalStateException(String.format(
                    "Not enough questions available. Required: %d, Available: %d",
                    requiredQuestions, totalAvailableQuestions));
        }

        Paper paper = new Paper();
        paper.setStartTime(new Date());
        paper.setStudent(student);
        paper.setExam(exam);
        paper.setEnrollment(enrollment);
        paper.setAttemptNumber(currentAttempt);

        // Generate exactly question_count random questions
        Set<Question> randomQuestions = questionRepository.findRandomQuestionsByExamId(
                exam.getId(), requiredQuestions);

        // Verify we got the correct number of questions
        if (randomQuestions.size() != requiredQuestions) {
            throw new IllegalStateException("Failed to generate required number of questions");
        }

        // Create paper questions
        Set<PaperQuestion> paperQuestions = randomQuestions.stream()
                .map(question -> {
                    PaperQuestion pq = new PaperQuestion();
                    pq.setQuestion(question);
                    pq.setPaper(paper);
                    return pq;
                })
                .collect(Collectors.toSet());

        paper.setPaperQuestions(paperQuestions);
        Paper savedPaper = paperRepository.save(paper);

        return convertToResponseDTO(savedPaper);
    }

    @Override
    public PaperResponseDTO getPaperById(Long id) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paper not found"));
        return convertToResponseDTO(paper);
    }

    @Override
    public List<PaperResponseDTO> getAllPapers() {
        return paperRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaperResponseDTO> getPapersByStudent(Long studentId) {
        List<Paper> byStudentId = paperRepository.findByStudentId(studentId);
        List<PaperResponseDTO> paperResponseDTOS = new ArrayList<>();
        byStudentId.forEach(paper -> paperResponseDTOS.add(convertToResponseDTO(paper)));
        return paperResponseDTOS;
    }

    @Override
    public List<PaperResponseDTO> getPapersByExam(Long examId) {
        return paperRepository.findByExamId(examId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaperResponseDTO submitPaper(Long paperId, PaperDTO paperDTO) {
        Paper paper = paperRepository.findById(paperId)
                .orElseThrow(() -> new ResourceNotFoundException("Paper not found"));

        paper.setEndTime(new Date());

        // Update paper questions with student answers
        paperDTO.getPaperQuestions().forEach(pqDTO -> {
            PaperQuestion pq = paperQuestionRepository.findById(pqDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Paper question not found"));
            pq.setSelectedOption(pqDTO.getSelectedOption());
            pq.setIsCorrect(pq.getSelectedOption() != null &&
                    pq.getSelectedOption() == pq.getQuestion().getCorrectOption());
            paperQuestionRepository.save(pq);
        });

        Paper updatedPaper = paperRepository.save(paper);
        return calculateResult(updatedPaper.getId());
    }

    @Override
    public void deletePaper(Long id) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paper not found"));
        paperRepository.delete(paper);
    }

    @Override
    public PaperResponseDTO startExam(Long studentId, Long examId) {
        // Check if student is enrolled
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndExamId(studentId, examId);

        // Get current attempt number
        int attemptNumber = paperRepository.countByStudentIdAndExamId(studentId, examId) + 1;

        PaperRequestDTO requestDTO = new PaperRequestDTO();
        requestDTO.setStudentId(studentId);
        requestDTO.setExamId(examId);
        requestDTO.setEnrollmentId(enrollment.getId());
        requestDTO.setAttemptNumber(attemptNumber);

        return createPaper(requestDTO);
    }

    @Override
    public PaperResponseDTO calculateResult(Long paperId) {
        Paper paper = paperRepository.findById(paperId)
                .orElseThrow(() -> new ResourceNotFoundException("Paper not found"));

        long totalQuestions = paper.getPaperQuestions().size();
        long correctAnswers = paper.getPaperQuestions().stream()
                .filter(pq -> Boolean.TRUE.equals(pq.getIsCorrect()))
                .count();

        double totalMarks = paper.getTotalMarks();
        double obtainedMarks = (correctAnswers * totalMarks) / totalQuestions;

        paper.setTotalMarks(totalMarks);
        paper.setObtainedMarks(obtainedMarks);
        Paper updatedPaper = paperRepository.save(paper);

        return convertToResponseDTO(updatedPaper);
    }

    private PaperResponseDTO convertToResponseDTO(Paper paper) {
        PaperResponseDTO responseDTO = modelMapper.map(paper, PaperResponseDTO.class);
        responseDTO.setStudentName(paper.getStudent().getName());
        responseDTO.setExamTitle(paper.getExam().getTitle());

        Set<PaperQuestionResponseDTO> questionDTOs = paper.getPaperQuestions().stream()
                .map(pq -> {
                    PaperQuestionResponseDTO pqDTO = modelMapper.map(pq, PaperQuestionResponseDTO.class);
                    Question question = pq.getQuestion();
                    pqDTO.setQuestionText(question.getText());
                    pqDTO.setOption1(question.getOption1());
                    pqDTO.setOption2(question.getOption2());
                    pqDTO.setOption3(question.getOption3());
                    pqDTO.setOption4(question.getOption4());
                    pqDTO.setCorrectOption(question.getCorrectOption());
                    pqDTO.setTimeToAnswer(question.getTimeToAnswer());
                    return pqDTO;
                })
                .collect(Collectors.toSet());

        responseDTO.setQuestions(questionDTOs);
        return responseDTO;
    }
}
