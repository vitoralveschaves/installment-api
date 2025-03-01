package com.application.api.installment.controllers;

import com.application.api.installment.dto.ErrorResponseDto;
import com.application.api.installment.dto.FieldErrorsDto;
import com.application.api.installment.exceptions.AlreadyExistsException;
import com.application.api.installment.exceptions.NotFoundException;
import com.application.api.installment.exceptions.TokenNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldErrorsDto> errorsList = e.getFieldErrors()
                .stream()
                .map(error -> new FieldErrorsDto(
                        error.getField(), error.getDefaultMessage())
                )
                .toList();
        return new ErrorResponseDto(
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", errorsList
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleNotFoundException(NotFoundException e) {
        return new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleAlreadyExistsException(AlreadyExistsException e) {
        return new ErrorResponseDto(HttpStatus.CONFLICT.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleBadCredentialsException(BadCredentialsException e) {
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleRuntimeException(Exception e) {
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado: " + e.getMessage() + " " + e.getClass(),
                List.of());
    }
}
