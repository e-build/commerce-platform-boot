package com.ebuild.commerce.controller;

import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.exception.AlreadyExistsException;
import com.ebuild.commerce.exception.NotFoundException;
import com.ebuild.commerce.exception.security.JwtTokenNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
        org.springframework.validation.BindException.class
        , MethodArgumentNotValidException.class
        , AlreadyExistsException.class
        , NotFoundException.class
        , IllegalArgumentException.class
        })
    public ResponseEntity<CommonResponse> badRequest(Exception e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(CommonResponse.ERROR(e));
    }

    @ExceptionHandler(value = {
        JwtTokenNotExistsException.class
        , UsernameNotFoundException.class
        , BadCredentialsException.class
    })
    public ResponseEntity<CommonResponse> securityException(Exception e) {
        e.printStackTrace();
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(CommonResponse.ERROR(e));
    }

    @ExceptionHandler(value = {
        Exception.class
    })
    public ResponseEntity<CommonResponse> etc(Exception e) {
        e.printStackTrace();
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(CommonResponse.ERROR(e));
    }

}
