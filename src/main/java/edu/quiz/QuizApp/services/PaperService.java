package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.paper.CreatePaperDto;

public interface PaperService {
    void savePaper(CreatePaperDto createPaperDto);
}
