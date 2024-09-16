package com.example.bank.demo.domain.exceptions;

public class DepositLimitExceededException extends RuntimeException{
    public DepositLimitExceededException(String message) {
        super(message);
    }
}
