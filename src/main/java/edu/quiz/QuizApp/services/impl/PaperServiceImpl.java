package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.dtos.paper.AiRequestDto;
import edu.quiz.QuizApp.dtos.paper.CreatePaperDto;
import edu.quiz.QuizApp.dtos.paper.GetPaperDto;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Override
    public List<Map<String, Object>> getSubmissionsByMinute(int minutes) {
        // Get data for the last N minutes
        Date endTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(endTime);
        cal.add(Calendar.MINUTE, -minutes);
        Date startTime = cal.getTime();

        // Query to get submissions grouped by minute
        List<Object[]> results = paperRepository.findSubmissionsByMinuteInterval(startTime, endTime);

        // Create time slots for all minutes in the range
        List<Map<String, Object>> chartData = new ArrayList<>();
        Calendar current = Calendar.getInstance();
        current.setTime(startTime);

        // Round down to the nearest minute
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);

        Map<String, Long> submissionMap = new HashMap<>();
        for (Object[] result : results) {
            String timeKey = result[0].toString();
            Long count = ((Number) result[1]).longValue();
            submissionMap.put(timeKey, count);
        }

        while (current.getTime().before(endTime) || current.getTime().equals(endTime)) {
            Map<String, Object> dataPoint = new HashMap<>();

            LocalDateTime localDateTime = current.getTime().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
            String timeKey = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String displayTime = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

            dataPoint.put("time", displayTime);
            dataPoint.put("timestamp", current.getTime().getTime());
            dataPoint.put("count", submissionMap.getOrDefault(timeKey, 0L));

            chartData.add(dataPoint);
            current.add(Calendar.MINUTE, 1);
        }

        return chartData;
    }

    @Override
    public Map<String, Object> getLiveSubmissionData() {
        Map<String, Object> data = new HashMap<>();

        // Get current minute submissions
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date currentMinute = cal.getTime();

        cal.add(Calendar.MINUTE, 1);
        Date nextMinute = cal.getTime();

        Long currentMinuteCount = paperRepository.countSubmissionsBetweenDates(currentMinute, nextMinute);

        // Get total submissions today
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date todayStart = cal.getTime();

        Long todayTotal = paperRepository.countSubmissionsBetweenDates(todayStart, new Date());

        data.put("currentMinuteCount", currentMinuteCount);
        data.put("todayTotal", todayTotal);
        data.put("timestamp", System.currentTimeMillis());

        return data;
    }

    @Override
    public List<GetPaperDto> getAllPapers() {
        List<Paper> allPaperList = paperRepository.findAll();
        List<GetPaperDto> paperDtoList = new ArrayList<>();
        allPaperList.forEach(paper -> {
            GetPaperDto getPaperDto = new GetPaperDto();
            getPaperDto.setId(paper.getId());
            Enrollment enrollment = paper.getEnrollment();
            getPaperDto.setStudentName(enrollment.getStudent().getName());
            getExamName(paperDtoList, paper, enrollment, getPaperDto);
        });
        return paperDtoList;
    }

    @Override
    public List<GetPaperDto> getAllPaperByStudentId(Long studentId) {
        List<Paper> paperList = paperRepository.findAll();
        List<GetPaperDto> paperDtoList = new ArrayList<>();
        paperList.forEach(paper -> {
            Enrollment enrollment = paper.getEnrollment();
            User student = enrollment.getStudent();
            if (student.getId().equals(studentId)) {
                GetPaperDto getPaperDto = new GetPaperDto();
                getPaperDto.setId(paper.getId());
                getPaperDto.setStudentName(student.getName());
                getExamName(paperDtoList, paper, enrollment, getPaperDto);
            }
        });
        return paperDtoList;
    }

    @Override
    public AiRequestDto getAiRequest(Long studentId) {
        List<GetPaperDto> allPaperByStudentId = getAllPaperByStudentId(studentId);
        List<String> questionList = new ArrayList<>();
        List<String> correctAnswerList = new ArrayList<>();
        List<String> givenAnswerList = new ArrayList<>();
        GetPaperDto getPaperDtoLast = allPaperByStudentId.getLast();
        AiRequestDto aiRequestDto = new AiRequestDto();
        aiRequestDto.setStudentName(getPaperDtoLast.getStudentName());
        List<Paper.StudentAnswer> studentAnswers = getPaperDtoLast.getStudentAnswer();
        studentAnswers.forEach(studentAnswer -> {
            Long questionId = studentAnswer.getQuestionId();
            if(questionService.getQuestionById(questionId).isPresent()){
                questionList.add(questionService.getQuestionById(questionId).get().getText());
            }
            aiRequestDto.setQuestions(questionList);
            int correctAnswer = studentAnswer.getCorrectAnswer();
            createList(correctAnswerList, questionId, correctAnswer);
            aiRequestDto.setCorrectAnswer(correctAnswerList);
            int givenAnswer = studentAnswer.getGivenAnswer();
            createList(givenAnswerList, questionId, givenAnswer);
            aiRequestDto.setGivenAnswer(givenAnswerList);
        });
        return aiRequestDto;
    }

    private void createList(List<String> givenAnswerList, Long questionId, int givenAnswer) {
        Optional<GetQuestionDto> questionById = questionService.getQuestionById(questionId);
        if (questionById.isPresent()) {
            if (givenAnswer == 1) {
                givenAnswerList.add(questionById.get().getOption1());
            }
            else if (givenAnswer == 2) {
                givenAnswerList.add(questionById.get().getOption2());
            }
            else if (givenAnswer == 3) {
                givenAnswerList.add(questionById.get().getOption3());
            }
            else if (givenAnswer == 4) {
                givenAnswerList.add(questionById.get().getOption4());
            }
        }

    }

    private void getExamName(List<GetPaperDto> paperDtoList, Paper paper, Enrollment enrollment, GetPaperDto getPaperDto) {
        getPaperDto.setExamName(enrollment.getExam().getTitle());
        getPaperDto.setCourseName(enrollment.getExam().getCourse().getName());
        getPaperDto.setAttemptNumber(paper.getAttemptNumber());
        getPaperDto.setObtainedMarks(paper.getObtainedMarks());
        getPaperDto.setTotalMarks(paper.getTotalMarks());
        getPaperDto.setStartTime(paper.getStartTime());
        getPaperDto.setEntTime(paper.getEndTime());
        getPaperDto.setStudentAnswer(paper.getStudentAnswers());
        paperDtoList.add(getPaperDto);
    }
}
