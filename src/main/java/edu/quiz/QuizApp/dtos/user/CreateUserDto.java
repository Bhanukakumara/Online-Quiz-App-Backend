package edu.quiz.QuizApp.dtos.user;

import edu.quiz.QuizApp.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserDto {
    private String username;
    private String password;
    private String name;
    private String email;
    private UserRole role;
}
