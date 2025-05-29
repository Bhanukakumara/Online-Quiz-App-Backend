package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.dtos.question.QuestionCreateDto;
import edu.quiz.QuizApp.dtos.question.QuestionDto;
import edu.quiz.QuizApp.dtos.question.QuestionUpdateDto;
import edu.quiz.QuizApp.entites.Exam;
import edu.quiz.QuizApp.entites.Question;
import edu.quiz.QuizApp.exceptions.ResourceNotFoundException;
import edu.quiz.QuizApp.repositories.ExamRepository;
import edu.quiz.QuizApp.repositories.QuestionRepository;
import edu.quiz.QuizApp.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    @Override
    public QuestionDto createQuestion(QuestionCreateDto questionCreateDto) {
        // Verify exam exists
        Exam exam = examRepository.findById(questionCreateDto.getExamId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + questionCreateDto.getExamId()));

        Question question = new Question();
        question.setText(questionCreateDto.getText());
        question.setOption1(questionCreateDto.getOption1());
        question.setOption2(questionCreateDto.getOption2());
        question.setOption3(questionCreateDto.getOption3());
        question.setOption4(questionCreateDto.getOption4());
        question.setCorrectOption(questionCreateDto.getCorrectOption());
        question.setTimeToAnswer(questionCreateDto.getTimeToAnswer());
        question.setExam(exam);

        Question savedQuestion = questionRepository.save(question);
        return convertToDto(savedQuestion);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionDto getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + id));
        return convertToDto(question);
    }

    @Override
    public QuestionDto updateQuestion(Long id, QuestionUpdateDto questionUpdateDto) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + id));

        // Update only non-null fields
        if (questionUpdateDto.getText() != null) {
            question.setText(questionUpdateDto.getText());
        }
        if (questionUpdateDto.getOption1() != null) {
            question.setOption1(questionUpdateDto.getOption1());
        }
        if (questionUpdateDto.getOption2() != null) {
            question.setOption2(questionUpdateDto.getOption2());
        }
        if (questionUpdateDto.getOption3() != null) {
            question.setOption3(questionUpdateDto.getOption3());
        }
        if (questionUpdateDto.getOption4() != null) {
            question.setOption4(questionUpdateDto.getOption4());
        }
        if (questionUpdateDto.getCorrectOption() != null) {
            question.setCorrectOption(questionUpdateDto.getCorrectOption());
        }
        if (questionUpdateDto.getTimeToAnswer() != null) {
            question.setTimeToAnswer(questionUpdateDto.getTimeToAnswer());
        }

        Question updatedQuestion = questionRepository.save(question);
        return convertToDto(updatedQuestion);
    }

    @Override
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found with ID: " + id);
        }
        questionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getAllQuestions() {
        List<QuestionDto> questions = new ArrayList<>();
        questionRepository.findAll().forEach(question -> questions.add(convertToDto(question)));
        return questions;
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionsByExamId(Long examId) {
        // Verify exam exists
        if (!examRepository.existsById(examId)) {
            throw new ResourceNotFoundException("Exam not found with ID: " + examId);
        }

        List<Question> questions = questionRepository.findByExamId(examId);
        return questions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionDto> getQuestionsByExamId(Long examId, Pageable pageable) {
        // Verify exam exists
        if (!examRepository.existsById(examId)) {
            throw new ResourceNotFoundException("Exam not found with ID: " + examId);
        }

        Page<Question> questions = questionRepository.findByExamId(examId, pageable);
        return questions.map(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return questionRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countQuestionsByExamId(Long examId) {
        return questionRepository.countByExamId(examId);
    }

    private QuestionDto convertToDto(Question question) {
        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setOption1(question.getOption1());
        dto.setOption2(question.getOption2());
        dto.setOption3(question.getOption3());
        dto.setOption4(question.getOption4());
        dto.setCorrectOption(question.getCorrectOption());
        dto.setTimeToAnswer(question.getTimeToAnswer());
        dto.setExamId(question.getExam().getId());
        dto.setExamTitle(question.getExam().getTitle()); // Assuming Exam has a title field
        return dto;
    }
}
