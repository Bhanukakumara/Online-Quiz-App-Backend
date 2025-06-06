package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.paper.CreatePaperDto;

import java.util.List;
import java.util.Map;

public interface PaperService {
    void savePaper(CreatePaperDto createPaperDto);
    List<Map<String, Object>> getSubmissionsByMinute(int minutes);
    Map<String, Object> getLiveSubmissionData();
}
