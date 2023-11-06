package com.jungle.springpost.exception;

import com.jungle.springpost.dto.LoginResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> illegalArgumentExceptionAdvice(IllegalArgumentException e) {
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .message(e.getMessage())
                .statusCode(400)
                .build();
        return ResponseEntity.badRequest().body(loginResponseDto);
    }

}
