package edu.quiz.QuizApp.exceptions;

import edu.quiz.QuizApp.dtos.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomException> handleUserNotFoundException(UserNotFoundException ex){
        return ResponseEntity.badRequest().body(CustomException.builder().massege(ex.getMessage()).code(HttpStatus.NOT_FOUND.value()).build());
    }

    //Generic Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        // Handle unexpected exceptions
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }

}
