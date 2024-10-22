package com.example.bank.demo.domain.ports.useCase;

import com.example.bank.demo.application.controller.dto.response.DepositResponseDto;

import java.math.BigDecimal;

public interface MakeDepositUseCase {
    DepositResponseDto makeDeposit(BigDecimal depositValue, Long accountId);
}
