package com.example.bank.demo.domain.model.ports.useCase;

import com.example.bank.demo.domain.dto.response.WithdrawalResponseDto;

import java.math.BigDecimal;

public interface MakeWithDrawalUseCase {
    WithdrawalResponseDto makeWithdrawal(BigDecimal withdrawalValue, Long accountId);
}
