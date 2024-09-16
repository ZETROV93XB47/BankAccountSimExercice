package com.example.bank.demo.domain.adapters.mappers;

import com.example.bank.demo.domain.dto.response.WithdrawalResponseDto;
import com.example.bank.demo.domain.model.Bank;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class WithdrawalResponseDtoMapperAdapterTest {
    private final WithdrawalResponseDtoMapperAdapter mapper = new WithdrawalResponseDtoMapperAdapter();

    @Test
    void shouldMapToWithdrawalResponseDto() {
        Bank bank = Bank.builder()
                .accountNumber(UUID.fromString("b10f10ab-e638-4bde-9b28-1f45e9eb2424"))
                .balance(new BigDecimal("20000.00"))
                .build();

        WithdrawalResponseDto withdrawalResponseDto = mapper.mapToWithdrawalResponseDto(bank);

        assertThat(withdrawalResponseDto.getBalance()).isEqualTo(new BigDecimal("20000.00"));
        assertThat(withdrawalResponseDto.getAccountNumber()).isEqualTo("b10f10ab-e638-4bde-9b28-1f45e9eb2424");
    }
}