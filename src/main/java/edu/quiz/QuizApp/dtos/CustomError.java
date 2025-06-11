package edu.quiz.QuizApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CustomError {
    private String massage;
    private Integer code;
}
