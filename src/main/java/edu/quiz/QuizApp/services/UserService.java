package edu.quiz.QuizApp.services;

import edu.quiz.QuizApp.dtos.user.CreateUserDto;
import edu.quiz.QuizApp.dtos.user.GetUserDto;
import edu.quiz.QuizApp.enums.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<GetUserDto> createUser(CreateUserDto createUserDto);
    Optional<List<GetUserDto>> getAllUsers();
    Optional<GetUserDto> getUserById(Long id);
    Optional<GetUserDto> getUserByEmail(String email);
    Optional<List<GetUserDto>> getAllUsersByRole(UserRole role);
    void createUserList(CreateUserDto[] createUserDto);
    Long totalUsersCount();
    Long totalAdminCount();
    Long totalTeacherCount();
    Long totalStudentCount();
}
