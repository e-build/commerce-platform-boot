package com.ebuild.commerce.controller;

import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.exception.AlreadyExistsException;
import com.ebuild.commerce.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
