package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.user.CreateUserDto;
import edu.quiz.QuizApp.dtos.user.RequestUserDto;
import edu.quiz.QuizApp.entites.User;
import edu.quiz.QuizApp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auth/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PutMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok(userService.createUser(createUserDto));
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        if (userService.getUserById(id).isPresent()) {
            return ResponseEntity.ok(userService.getUserById(id).get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/get-by-username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        if (userService.getUserByUsername(username).isPresent()) {
            return ResponseEntity.ok(userService.getUserByUsername(username).get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        if (userService.getUserByEmail(email).isPresent()) {
            return ResponseEntity.ok(userService.getUserByEmail(email).get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<RequestUserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        if (userService.getUserById(id).isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.ok("User with ID " + id + " has been deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found.");
    }
}
