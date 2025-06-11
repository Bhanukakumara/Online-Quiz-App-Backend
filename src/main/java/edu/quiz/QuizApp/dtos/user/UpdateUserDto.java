package edu.quiz.QuizApp.dtos.user;

import edu.quiz.QuizApp.enums.UserRole;
import lombok.Data;

@Data
public class UpdateUserDto {
    private String username;
    private String password;
    private String name;
    private String email;
}
