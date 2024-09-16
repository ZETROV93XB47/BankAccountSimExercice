package com.example.bank.demo.domain.model.ports.mapper;

import com.example.bank.demo.domain.dto.response.DepositResponseDto;
import com.example.bank.demo.domain.model.Bank;

public interface DepositResponseDtoMapperPort {
    DepositResponseDto mapToDepositResponseDto(Bank account);
}
