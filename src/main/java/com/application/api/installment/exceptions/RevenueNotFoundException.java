package com.application.api.installment.exceptions;

public class RevenueNotFoundException extends RuntimeException{
    public RevenueNotFoundException(String message) {
        super(message);
    }
}
