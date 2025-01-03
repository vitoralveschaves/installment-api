package com.application.api.installment.controllers;

import com.application.api.installment.controllers.dto.ErrorResponseDTO;
import com.application.api.installment.controllers.dto.FieldErrors;
import com.application.api.installment.exceptions.RevenueNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldErrors> errorsList = e.getFieldErrors()
                .stream()
                .map(error -> new FieldErrors(
                        error.getField(), error.getDefaultMessage())
                )
                .toList();
        return new ErrorResponseDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", errorsList
        );
    }

    @ExceptionHandler(RevenueNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleRevenueNotFoundException(RevenueNotFoundException e) {
        return new ErrorResponseDTO(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }
}
