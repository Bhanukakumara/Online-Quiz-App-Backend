package edu.quiz.QuizApp.dtos.paper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AiRequestDto {
    private String studentName;
    private List<String> questions;
    private List<String> correctAnswer;
    private List<String> givenAnswer;
}
