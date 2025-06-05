package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.paper.PaperDTO;
import edu.quiz.QuizApp.dtos.paper.PaperRequestDTO;
import edu.quiz.QuizApp.dtos.paper.PaperResponseDTO;
import edu.quiz.QuizApp.services.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/paper")
@RequiredArgsConstructor
public class PaperController {
    private final PaperService paperService;

}
