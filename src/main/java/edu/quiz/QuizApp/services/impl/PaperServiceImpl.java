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

}
