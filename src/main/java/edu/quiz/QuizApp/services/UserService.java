package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.user.CreateUserDto;
import edu.quiz.QuizApp.dtos.user.RequestUserDto;
import edu.quiz.QuizApp.entites.*;
import edu.quiz.QuizApp.enums.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(CreateUserDto createUserDto);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    List<RequestUserDto> getAllUsers();
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
    List<User> getUsersByRole(UserRole role);
}
