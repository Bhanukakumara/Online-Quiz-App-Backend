package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.paper.CreatePaperDto;
import edu.quiz.QuizApp.services.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paper")
@RequiredArgsConstructor
public class PaperController {
    private final PaperService paperService;

    @PostMapping("/save")
    public void savePaper(@RequestBody CreatePaperDto createPaperDto) {
        paperService.savePaper(createPaperDto);
    }
}
