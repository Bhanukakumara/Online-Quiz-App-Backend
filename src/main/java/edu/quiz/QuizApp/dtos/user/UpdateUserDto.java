package edu.quiz.QuizApp.dtos.user;

import lombok.Data;

@Data
public class UpdateUserDto {
    private String username;
    private String password;
    private String name;
    private String email;
}
