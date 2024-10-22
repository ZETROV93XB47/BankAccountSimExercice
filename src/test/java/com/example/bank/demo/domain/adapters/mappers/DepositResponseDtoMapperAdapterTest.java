package com.example.bank.demo.domain.adapters.mappers;

import com.example.bank.demo.application.controller.dto.response.DepositResponseDto;
import com.example.bank.demo.application.controller.mappers.DepositResponseDtoMapperAdapter;
import com.example.bank.demo.domain.model.Bank;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DepositResponseDtoMapperAdapterTest {
    private final DepositResponseDtoMapperAdapter mapper = new DepositResponseDtoMapperAdapter();

    @Test
    void shouldMapToDepositResponseDto() {
        Bank bank = Bank.builder()
                .accountNumber(UUID.fromString("b10f10ab-e638-4bde-9b28-1f45e9eb2424"))
                .balance(new BigDecimal("20000.00"))
                .build();

        DepositResponseDto depositResponseDto = mapper.mapToDepositResponseDto(bank);

        assertThat(depositResponseDto.getBalance()).isEqualTo(new BigDecimal("20000.00"));
        assertThat(depositResponseDto.getAccountNumber()).isEqualTo("b10f10ab-e638-4bde-9b28-1f45e9eb2424");
    }
}