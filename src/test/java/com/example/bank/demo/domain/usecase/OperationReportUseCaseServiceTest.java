package com.example.bank.demo.domain.usecase;

import com.example.bank.demo.domain.adapters.service.OperationsReportUseCaseService;
import com.example.bank.demo.domain.dto.response.OperationReportResponseDto;
import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.ports.mapper.OperationReportResponseDtoMapperPort;
import com.example.bank.demo.domain.ports.out.bank.BankRepositoryPort;
import com.example.bank.demo.domain.ports.out.bank_account.BankAccountRepositoryPort;
import com.example.bank.demo.domain.ports.out.operation.OperationRepositoryPort;
import com.example.bank.demo.domain.ports.out.saving_account.SavingAccountRepositoryPort;
import com.example.bank.demo.domain.utils.DateProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.bank.demo.domain.model.enumpackage.AccountType.CLASSIC_ACCOUNT;
import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.DEPOSIT;
import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.WITHDRAWAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperationReportUseCaseServiceTest {

    @Mock
    private BankRepositoryPort bankRepositoryPort;

    @Mock
    private DateProvider dateProvider;

    @Mock
    private OperationRepositoryPort operationRepositoryPort;

    @Mock
    private BankAccountRepositoryPort bankAccountRepositoryPort;

    @Mock
    private SavingAccountRepositoryPort savingAccountRepositoryPort;

    @Mock
    private OperationReportResponseDtoMapperPort operationReportResponseDtoMapperPort;

    @InjectMocks
    private OperationsReportUseCaseService operationsReportUseCaseService;

    @Test
    void shouldFindByAccountIdOperationsSortedByOperationDateDesc() {
        BankAccount account = new BankAccount(1L, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));

        Operation expectedOperation1 = new Operation(1L, account, DEPOSIT, account.getBalance(), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 15));
        Operation expectedOperation2 = new Operation(2L, account, DEPOSIT, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 30));
        Operation expectedOperation3 = new Operation(3L, account, WITHDRAWAL, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 35));
        Operation expectedOperation4 = new Operation(4L, account, DEPOSIT, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 40));
        Operation expectedOperation5 = new Operation(5L, account, WITHDRAWAL, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 45));

        List<Operation> operations = List.of(expectedOperation1, expectedOperation2, expectedOperation3, expectedOperation4, expectedOperation5);

        OperationReportResponseDto expectedOperations = OperationReportResponseDto.builder()
                .accountNumber("745c6891-1122-11ef-bee2-0242ac170002")
                .totalNumberOfOperations(5)
                .operations(operations)
                .build();

        when(bankRepositoryPort.findById(1L)).thenReturn(Optional.of(account));
        when(operationRepositoryPort.findByAccountIdOrderByDateOperationDesc(account)).thenReturn(Optional.of(operations));
        when(operationReportResponseDtoMapperPort.mapToOperationResponseDto(operations, account.getAccountNumber())).thenReturn(expectedOperations);

        OperationReportResponseDto operationReportResponseDto = operationsReportUseCaseService.getOperationsReport(1L);

        assertThat(operationReportResponseDto).isNotNull();
        assertThat(operationReportResponseDto.getAccountNumber()).isEqualTo("745c6891-1122-11ef-bee2-0242ac170002");
        assertThat(operationReportResponseDto.getTotalNumberOfOperations()).isEqualTo(5);
        assertThat(operationReportResponseDto.getOperations()).containsAll(operations);

    }
}
