package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.dtos.question.CreateQuestionDto;
import edu.quiz.QuizApp.dtos.question.GetQuestionDto;
import edu.quiz.QuizApp.entites.Exam;
import edu.quiz.QuizApp.entites.Question;
import edu.quiz.QuizApp.entites.User;
import edu.quiz.QuizApp.repositories.ExamRepository;
import edu.quiz.QuizApp.repositories.QuestionRepository;
import edu.quiz.QuizApp.repositories.UserRepository;
import edu.quiz.QuizApp.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public GetQuestionDto questionToGetQuestionDto(Question question) {
        return new GetQuestionDto(
                question.getId(),
                question.getText(),
                question.getOption1(),
                question.getOption2(),
                question.getOption3(),
                question.getOption4(),
                question.getCorrectOption(),
                question.getTimeToAnswer(),
                question.getMarks(),
                question.getExam().getId(),
                question.getExam().getTitle(),
                question.getUser().getId()
        );
    }

    @Override
    public Optional<GetQuestionDto> createQuestion(CreateQuestionDto createQuestionDto) {
        Exam exam = examRepository.findById(createQuestionDto.getExamId()).orElseThrow(() -> new RuntimeException("Exam not found"));
        User user = userRepository.findById(createQuestionDto.getTeacherId()).orElseThrow(() -> new RuntimeException("Teacher not found"));
        Question question = getQuestion(createQuestionDto, exam, user);
        return Optional.of(questionToGetQuestionDto(questionRepository.save(question)));
    }

    private static Question getQuestion(CreateQuestionDto createQuestionDto, Exam exam, User user) {
        Question question = new Question();
        question.setText(createQuestionDto.getText());
        question.setOption1(createQuestionDto.getOption1());
        question.setOption2(createQuestionDto.getOption2());
        question.setOption3(createQuestionDto.getOption3());
        question.setOption4(createQuestionDto.getOption4());
        question.setCorrectOption(createQuestionDto.getCorrectOption());
        question.setTimeToAnswer(createQuestionDto.getTimeToAnswer());
        question.setMarks(createQuestionDto.getMarks());
        question.setExam(exam);
        question.setUser(user);
        return question;
    }

    @Override
    public Optional<List<GetQuestionDto>> getAllQuestions() {
        List<GetQuestionDto> questions = new ArrayList<>();
        questionRepository.findAll().forEach(question -> questions.add(questionToGetQuestionDto(question)));
        return Optional.of(questions);
    }

    @Override
    public Optional<GetQuestionDto> getQuestionById(long id) {
        return questionRepository.findById(id).map(this::questionToGetQuestionDto);
    }

    @Override
    public Boolean deleteQuestion(long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<List<GetQuestionDto>> getAllQuestionByExamId(long examId) {
        List<GetQuestionDto> questions = new ArrayList<>();
        questionRepository.findAllByExamId(examId).forEach(question -> questions.add(questionToGetQuestionDto(question)));
        return Optional.of(questions);
    }

    @Override
    public Optional<List<GetQuestionDto>> getAllQuestionsByUserId(long userId) {
        List<GetQuestionDto> questions = new ArrayList<>();
        questionRepository.findAllByUserId(userId).forEach(question -> questions.add(questionToGetQuestionDto(question)));
        return Optional.of(questions);
    }

    @Override
    public Optional<List<GetQuestionDto>> getQuestionPaperByExamId(long examId) {

        Exam exam = examRepository.findById(examId).orElseThrow(() -> new RuntimeException("Exam not found"));
        List<Question> allQuestionByExamId = questionRepository.findAllByExamId(examId);

        if (exam.getQuestionCount() > allQuestionByExamId.size()) {
            throw new RuntimeException(String.format("Exam requires %d questions but only %d available", exam.getQuestionCount(), allQuestionByExamId.size()));
        }

        Collections.shuffle(allQuestionByExamId);
        List<Question> randomQuestions = allQuestionByExamId.stream()
                .limit(exam.getQuestionCount())
                .collect(Collectors.toList());

        List<GetQuestionDto> randomQuestionList = randomQuestions.stream()
                .map(this::questionToGetQuestionDto)
                .collect(Collectors.toList());

        return Optional.of(randomQuestionList);
    }

    @Override
    public Long totalQuestionCount() {
        return questionRepository.count();
    }
}
