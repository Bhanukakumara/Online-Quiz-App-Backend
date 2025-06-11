package edu.quiz.QuizApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CustomException {
    private String massege;
    private Integer code;
}
