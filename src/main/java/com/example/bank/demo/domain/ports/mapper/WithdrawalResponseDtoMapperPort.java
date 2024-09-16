package com.example.bank.demo.domain.ports.mapper;

import com.example.bank.demo.domain.dto.response.WithdrawalResponseDto;
import com.example.bank.demo.domain.model.Bank;

public interface WithdrawalResponseDtoMapperPort {
    WithdrawalResponseDto mapToWithdrawalResponseDto(Bank account);
}
