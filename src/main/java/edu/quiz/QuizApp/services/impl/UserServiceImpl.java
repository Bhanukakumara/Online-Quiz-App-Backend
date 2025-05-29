package edu.quiz.QuizApp.services.impl;

import edu.quiz.QuizApp.dtos.user.CreateUserDto;
import edu.quiz.QuizApp.dtos.user.RequestUserDto;
import edu.quiz.QuizApp.entites.*;
import edu.quiz.QuizApp.enums.UserRole;
import edu.quiz.QuizApp.repositories.UserRepository;
import edu.quiz.QuizApp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Override
    public User createUser(CreateUserDto createUserDto) {
        if (userRepository.existsByUsername(createUserDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return userRepository.save(modelMapper.map(createUserDto, User.class));
    }

    @Override
    public Optional<User> getUserById(Long id) {
        if (!userRepository.existsById(id)) {
            return Optional.empty();
        }
        return Optional.of(modelMapper.map(userRepository.findById(id).get(), User.class));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        if (!userRepository.existsByUsername(username)) {
            return Optional.empty();
        }
        return Optional.of(modelMapper.map(userRepository.findByUsername(username), User.class));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            return Optional.empty();
        }
        return Optional.of(modelMapper.map(userRepository.findByEmail(email), User.class));
    }

    @Override
    public List<RequestUserDto> getAllUsers() {
        List<RequestUserDto> users = new ArrayList<>();
        userRepository.findAll().forEach(u -> users.add(modelMapper.map(u, RequestUserDto.class)));
        return users;
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            if (user.getRole() == role) {
                users.add(user);
            }
        });
        return users;
    }
}
