package com.todoist.web.globalException;

import com.todoist.web.globalException.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public  ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExists ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordMisMatch.class)
    public ResponseEntity<ErrorResponse> handlePasswordMisMatch(PasswordMisMatch ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPassword.class)
    public ResponseEntity<ErrorResponse> handleInvalidPassword(InvalidPassword ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BlockedUser.class)
    public ResponseEntity<ErrorResponse> handleBlockedUser(BlockedUser ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}
