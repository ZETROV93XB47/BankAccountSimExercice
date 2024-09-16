package com.example.bank.demo.domain.adapters.mappers;

import com.example.bank.demo.domain.dto.response.WithdrawalResponseDto;
import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.ports.mapper.WithdrawalResponseDtoMapperPort;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class WithdrawalResponseDtoMapperAdapter implements WithdrawalResponseDtoMapperPort {

    @Override
    public WithdrawalResponseDto mapToWithdrawalResponseDto(Bank account) {
        return WithdrawalResponseDto.builder()
                .balance(account.getBalance())
                .accountNumber(account.getAccountNumber().toString())
                .build();
    }
}
