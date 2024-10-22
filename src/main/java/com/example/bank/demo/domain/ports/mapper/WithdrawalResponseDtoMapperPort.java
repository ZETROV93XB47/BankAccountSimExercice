package com.example.bank.demo.domain.ports.mapper;

import com.example.bank.demo.application.controller.dto.response.WithdrawalResponseDto;
import com.example.bank.demo.domain.model.Bank;

public interface WithdrawalResponseDtoMapperPort {
    WithdrawalResponseDto mapToWithdrawalResponseDto(Bank account);
}
