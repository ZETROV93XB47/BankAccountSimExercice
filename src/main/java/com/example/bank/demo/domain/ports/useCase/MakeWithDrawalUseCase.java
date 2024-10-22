package com.example.bank.demo.domain.ports.useCase;

import com.example.bank.demo.application.controller.dto.response.WithdrawalResponseDto;

import java.math.BigDecimal;

public interface MakeWithDrawalUseCase {
    WithdrawalResponseDto makeWithdrawal(BigDecimal withdrawalValue, Long accountId);
}
