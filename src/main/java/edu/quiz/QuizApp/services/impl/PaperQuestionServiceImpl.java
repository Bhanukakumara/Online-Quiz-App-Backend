package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.dtos.paper_question.PaperQuestionDTO;
import edu.quiz.QuizApp.dtos.paper_question.PaperQuestionRequestDTO;
import edu.quiz.QuizApp.dtos.paper_question.PaperQuestionResponseDTO;
import edu.quiz.QuizApp.entites.*;
import edu.quiz.QuizApp.exceptions.ResourceNotFoundException;
import edu.quiz.QuizApp.repositories.*;
import edu.quiz.QuizApp.services.PaperQuestionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PaperQuestionServiceImpl implements PaperQuestionService {
    private final PaperQuestionRepository paperQuestionRepository;
    private final PaperRepository paperRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Override
    public PaperQuestionResponseDTO createPaperQuestion(PaperQuestionRequestDTO requestDTO) {
        Paper paper = paperRepository.findById(requestDTO.getPaperId())
                .orElseThrow(() -> new ResourceNotFoundException("Paper not found"));

        Question question = questionRepository.findById(requestDTO.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        // Validate question belongs to paper's exam
        if (!question.getExam().getId().equals(paper.getExam().getId())) {
            throw new IllegalArgumentException("Question does not belong to the paper's exam");
        }

        PaperQuestion paperQuestion = new PaperQuestion();
        paperQuestion.setPaper(paper);
        paperQuestion.setQuestion(question);
        paperQuestion.setSelectedOption(requestDTO.getSelectedOption());

        // Auto-check correctness if answer is provided
        if (requestDTO.getSelectedOption() != null) {
            paperQuestion.setIsCorrect(requestDTO.getSelectedOption().equals(question.getCorrectOption()));
        }

        PaperQuestion savedPaperQuestion = paperQuestionRepository.save(paperQuestion);
        return convertToResponseDTO(savedPaperQuestion);
    }

    @Override
    public PaperQuestionResponseDTO getPaperQuestionById(Long id) {
        PaperQuestion paperQuestion = paperQuestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaperQuestion not found"));
        return convertToResponseDTO(paperQuestion);
    }

    @Override
    public List<PaperQuestionResponseDTO> getQuestionsByPaper(Long paperId) {
        return paperQuestionRepository.findByPaperId(paperId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaperQuestionResponseDTO updatePaperQuestion(Long id, PaperQuestionDTO paperQuestionDTO) {
        PaperQuestion paperQuestion = paperQuestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaperQuestion not found"));

        modelMapper.map(paperQuestionDTO, paperQuestion);

        // Update correctness if answer changed
        if (paperQuestionDTO.getSelectedOption() != null) {
            paperQuestion.setIsCorrect(paperQuestionDTO.getSelectedOption()
                    .equals(paperQuestion.getQuestion().getCorrectOption()));
        }

        PaperQuestion updatedPaperQuestion = paperQuestionRepository.save(paperQuestion);
        return convertToResponseDTO(updatedPaperQuestion);
    }

    @Override
    public void deletePaperQuestion(Long id) {
        PaperQuestion paperQuestion = paperQuestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaperQuestion not found"));
        paperQuestionRepository.delete(paperQuestion);
    }

    @Override
    public PaperQuestionResponseDTO submitAnswer(Long id, Integer selectedOption) {
        PaperQuestion paperQuestion = paperQuestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaperQuestion not found"));

        paperQuestion.setSelectedOption(selectedOption);
        paperQuestion.setIsCorrect(selectedOption != null &&
                selectedOption.equals(paperQuestion.getQuestion().getCorrectOption()));

        PaperQuestion updatedPaperQuestion = paperQuestionRepository.save(paperQuestion);
        return convertToResponseDTO(updatedPaperQuestion);
    }

    @Override
    public List<PaperQuestionResponseDTO> bulkCreatePaperQuestions(List<PaperQuestionRequestDTO> requestDTOs) {
        return requestDTOs.stream()
                .map(this::createPaperQuestion)
                .collect(Collectors.toList());
    }

    @Override
    public int calculateScore(Long paperId) {
        List<PaperQuestion> paperQuestions = paperQuestionRepository.findByPaperId(paperId);
        return (int) paperQuestions.stream()
                .filter(pq -> Boolean.TRUE.equals(pq.getIsCorrect()))
                .count();
    }

    private PaperQuestionResponseDTO convertToResponseDTO(PaperQuestion paperQuestion) {
        PaperQuestionResponseDTO responseDTO = modelMapper.map(paperQuestion, PaperQuestionResponseDTO.class);
        Question question = paperQuestion.getQuestion();

        responseDTO.setQuestionText(question.getText());
        responseDTO.setOption1(question.getOption1());
        responseDTO.setOption2(question.getOption2());
        responseDTO.setOption3(question.getOption3());
        responseDTO.setOption4(question.getOption4());
        responseDTO.setCorrectOption(question.getCorrectOption());
        responseDTO.setTimeToAnswer(question.getTimeToAnswer());

        return responseDTO;
    }
}
