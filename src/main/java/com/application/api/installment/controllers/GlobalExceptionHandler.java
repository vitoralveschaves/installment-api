package com.application.api.installment.controllers;

import com.application.api.installment.controllers.dto.ErrorResponseDTO;
import com.application.api.installment.controllers.dto.FieldErrorsDTO;
import com.application.api.installment.exceptions.ExpenseNotFoundException;
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
        List<FieldErrorsDTO> errorsList = e.getFieldErrors()
                .stream()
                .map(error -> new FieldErrorsDTO(
                        error.getField(), error.getDefaultMessage())
                )
                .toList();
        return new ErrorResponseDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", errorsList
        );
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleRevenueNotFoundException(ExpenseNotFoundException e) {
        return new ErrorResponseDTO(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }
}
