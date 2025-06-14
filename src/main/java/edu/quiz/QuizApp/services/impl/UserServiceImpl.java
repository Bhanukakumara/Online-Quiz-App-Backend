package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.dtos.user.CreateUserDto;
import edu.quiz.QuizApp.dtos.user.GetUserDto;
import edu.quiz.QuizApp.dtos.user.UpdateUserDto;
import edu.quiz.QuizApp.entites.*;
import edu.quiz.QuizApp.enums.UserRole;
import edu.quiz.QuizApp.exceptions.UserNotFoundException;
import edu.quiz.QuizApp.repositories.UserRepository;
import edu.quiz.QuizApp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public GetUserDto userToGetUserDto(User user) {
        return new GetUserDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getUserRole());
    }

    @Override
    public Optional<GetUserDto> createUser(CreateUserDto createUserDto) {
        if (userRepository.findByEmail(createUserDto.getEmail()) != null) {
            return Optional.empty();
        }
        createUserDto.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        return Optional.of(userToGetUserDto(userRepository.save(modelMapper.map(createUserDto, User.class))));
    }

    @Override
    public Optional<List<GetUserDto>> getAllUsers() {
        List<GetUserDto> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(userToGetUserDto(user)));
        return Optional.of(users);
    }

    @Override
    public Optional<GetUserDto> getUserById(Long id) {
        return userRepository.findById(id).map(this::userToGetUserDto);
    }

    @Override
    public Optional<GetUserDto> getUserByEmail(String email) {
        if (userRepository.findByEmail(email) != null) {
            return Optional.of(userToGetUserDto(userRepository.findByEmail(email)));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<GetUserDto>> getAllUsersByRole(UserRole role) {
        List<GetUserDto> users = new ArrayList<>();
        userRepository.findByUserRole(role).forEach(user -> users.add(userToGetUserDto(user)));
        return Optional.of(users);
    }

    @Override
    public Optional<GetUserDto> updateUserById(long id, UpdateUserDto updateUserDto) {
       User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User Doesnt Exist"));
        user.setName(updateUserDto.getName()!=null? updateUserDto.getName() : user.getName());
        user.setEmail(updateUserDto.getEmail()!=null? updateUserDto.getEmail() : user.getEmail());
        //USer Role Taken as String
        user.setUsername(updateUserDto.getUsername()!=null? updateUserDto.getUsername() : user.getUsername());
        user.setPassword(updateUserDto.getPassword()!=null? updateUserDto.getPassword() : user.getPassword());
        User updatedUser = userRepository.save(user);
        return Optional.of(userToGetUserDto(updatedUser));
    }

    @Override
    public Boolean deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void createUserList(CreateUserDto[] createUserDto) {
        for (CreateUserDto createUserDto1 : createUserDto) {
            createUser(createUserDto1);
            log.info("Created user: {}", createUserDto1.getUsername());
        }
    }

    @Override
    public Long totalUsersCount() {
        return userRepository.count();
    }

    @Override
    public Long totalAdminCount() {
        return userRepository.countByUserRole(UserRole.ADMIN);
    }

    @Override
    public Long totalTeacherCount() {
        return userRepository.countByUserRole(UserRole.TEACHER);
    }

    @Override
    public Long totalStudentCount() {
        return userRepository.countByUserRole(UserRole.STUDENT);
    }
}
