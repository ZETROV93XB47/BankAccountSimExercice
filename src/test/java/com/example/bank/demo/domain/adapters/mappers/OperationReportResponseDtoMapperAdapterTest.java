package com.example.bank.demo.domain.adapters.mappers;

import com.example.bank.demo.application.controller.dto.response.OperationReportResponseDto;
import com.example.bank.demo.application.controller.mappers.OperationReportResponseDtoMapperAdapter;
import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.model.Operation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.bank.demo.domain.model.enumpackage.AccountType.CLASSIC_ACCOUNT;
import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.DEPOSIT;
import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.WITHDRAWAL;
import static org.assertj.core.api.Assertions.assertThat;

class OperationReportResponseDtoMapperAdapterTest {
    private final OperationReportResponseDtoMapperAdapter operationReportResponseDtoMapperAdapter = new OperationReportResponseDtoMapperAdapter();

    @Test
    void shouldMapToOperationReportResponseDto() {
        BankAccount account = new BankAccount(1L, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));

        List<Operation> operations = getOperations(account);

        OperationReportResponseDto operationReportResponseDto = operationReportResponseDtoMapperAdapter.mapToOperationResponseDto(operations, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"));

        OperationReportResponseDto expectedOperationResponseDto = OperationReportResponseDto.builder()
                .accountNumber("745c6891-1122-11ef-bee2-0242ac170002")
                .totalNumberOfOperations(5)
                .operations(operations)
                .build();

        assertThat(operationReportResponseDto).isEqualTo(expectedOperationResponseDto);
    }

    private static List<Operation> getOperations(BankAccount account) {
        Operation expectedOperation1 = new Operation(1L, account, DEPOSIT, account.getBalance(), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 15));
        Operation expectedOperation2 = new Operation(2L, account, DEPOSIT, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 30));
        Operation expectedOperation3 = new Operation(3L, account, WITHDRAWAL, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 35));
        Operation expectedOperation4 = new Operation(4L, account, DEPOSIT, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 40));
        Operation expectedOperation5 = new Operation(5L, account, WITHDRAWAL, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 45));

        return List.of(expectedOperation1, expectedOperation2, expectedOperation3, expectedOperation4, expectedOperation5);
    }
}