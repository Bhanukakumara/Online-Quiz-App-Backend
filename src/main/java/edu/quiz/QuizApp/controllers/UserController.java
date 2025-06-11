package edu.quiz.QuizApp.controllers;

import edu.quiz.QuizApp.dtos.user.CreateUserDto;
import edu.quiz.QuizApp.dtos.user.GetUserDto;
import edu.quiz.QuizApp.dtos.user.UpdateUserDto;
import edu.quiz.QuizApp.enums.UserRole;
import edu.quiz.QuizApp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<GetUserDto> createUser(@RequestBody CreateUserDto createUserDto) {
        Optional<GetUserDto> user = userService.createUser(createUserDto);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    @PutMapping("/update-by-id/{id}")
    public ResponseEntity<GetUserDto> updateUserById(@PathVariable Long id, @RequestBody UpdateUserDto dto){
        Optional<GetUserDto> getUserDto = userService.updateUserById(id, dto);
        return getUserDto
                .map(userDto -> ResponseEntity.ok(userDto)) // If present, return 200 OK with body
                .orElseGet(() -> ResponseEntity.notFound().build()); // If empty, return 404 Not Found
    }


    @GetMapping("/get-all")
    public ResponseEntity<List<GetUserDto>> getAllUsers() {
        Optional<List<GetUserDto>> users = userService.getAllUsers();
        return users.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<GetUserDto> getUserById(@PathVariable Long id) {
        Optional<GetUserDto> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<GetUserDto> getUserByEmail(@PathVariable String email) {
        Optional<GetUserDto> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-by-role/{role}")
    public ResponseEntity<List<GetUserDto>> getAllUsersByRole(@PathVariable UserRole role) {
        Optional<List<GetUserDto>> users = userService.getAllUsersByRole(role);
        return users.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/create-list")
    public ResponseEntity<GetUserDto> createUserList(@RequestBody CreateUserDto[] createUserDto) {
        userService.createUserList(createUserDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/total-count")
    public ResponseEntity<Long> getTotalCount() {
        return ResponseEntity.ok(userService.totalUsersCount());
    }

    @GetMapping("/total-admin-count")
    public ResponseEntity<Long> getTotalAdminCount() {
        return ResponseEntity.ok(userService.totalAdminCount());
    }

    @GetMapping("/total-teacher-count")
    public ResponseEntity<Long> getTotalTeacherCount() {
        return ResponseEntity.ok(userService.totalTeacherCount());
    }

    @GetMapping("/total-student-count")
    public ResponseEntity<Long> getTotalStudentCount() {
        return ResponseEntity.ok(userService.totalStudentCount());
    }
}
