package com.example.bank.demo.domain.adapters.mappers;

import com.example.bank.demo.domain.dto.response.DepositResponseDto;
import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.ports.mapper.DepositResponseDtoMapperPort;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class DepositResponseDtoMapperAdapter implements DepositResponseDtoMapperPort {

    @Override
    public DepositResponseDto mapToDepositResponseDto(Bank account) {
        return DepositResponseDto.builder()
                .balance(account.getBalance())
                .accountNumber(account.getAccountNumber().toString())
                .build();
    }
}
