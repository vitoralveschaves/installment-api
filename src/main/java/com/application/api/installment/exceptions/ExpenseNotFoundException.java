package com.application.api.installment.exceptions;

public class ExpenseNotFoundException extends RuntimeException{
    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
