package com.example.bank.demo.application.controller.handler.error;

import com.example.bank.demo.application.controller.handler.error.model.ErrorResponseDto;
import com.example.bank.demo.domain.exceptions.AccountNotFoundException;
import com.example.bank.demo.domain.exceptions.DepositLimitExceededException;
import com.example.bank.demo.domain.exceptions.OperationNotFoundException;
import com.example.bank.demo.domain.exceptions.WithdrawalAmountBiggerThanBalanceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

import static com.example.bank.demo.application.controller.handler.error.enumpackage.ErrorCodeType.FUNCTIONAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorManagementControllerTest {
    private final ErrorManagementController errorManagementController = new ErrorManagementController();

    @Test
    void shouldHandleWithdrawalAmountBiggerThanBalanceException() {
        WithdrawalAmountBiggerThanBalanceException exception = new WithdrawalAmountBiggerThanBalanceException("Withdrawal amount exceeds balance");

        ResponseEntity<ErrorResponseDto> responseEntity = errorManagementController.handleWithdrawalAmountBiggerThanBalanceException(exception);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getErrorCodeType()).isEqualTo(FUNCTIONAL);
        assertEquals("Withdrawal amount exceeds balance", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void shouldHandleOperationNotFoundException() {
        OperationNotFoundException exception = new OperationNotFoundException("Operation not found");

        ResponseEntity<ErrorResponseDto> responseEntity = errorManagementController.handleOperationNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getErrorCodeType()).isEqualTo(FUNCTIONAL);
        assertEquals("Operation not found", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void shouldHandleAccountNotFoundException() {
        AccountNotFoundException exception = new AccountNotFoundException("Account not found");

        ResponseEntity<ErrorResponseDto> responseEntity = errorManagementController.handleAccountNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getErrorCodeType()).isEqualTo(FUNCTIONAL);
        assertEquals("Account not found", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void shouldHandleDepositLimitExceededException() {
        DepositLimitExceededException exception = new DepositLimitExceededException("Deposit exceeded deposit limit");

        ResponseEntity<ErrorResponseDto> responseEntity = errorManagementController.handleDepositLimitExceededException(exception);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getErrorCodeType()).isEqualTo(FUNCTIONAL);
        assertEquals("Deposit exceeded deposit limit", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }
}