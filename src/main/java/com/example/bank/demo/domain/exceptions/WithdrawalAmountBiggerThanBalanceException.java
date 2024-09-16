package com.example.bank.demo.domain.exceptions;

public class WithdrawalAmountBiggerThanBalanceException extends RuntimeException{
    public WithdrawalAmountBiggerThanBalanceException(String message) {
        super(message);
    }
}
