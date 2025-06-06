package edu.quiz.QuizApp.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String role;
}
