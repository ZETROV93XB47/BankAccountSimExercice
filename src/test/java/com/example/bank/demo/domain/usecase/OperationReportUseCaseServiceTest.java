package com.example.bank.demo.domain.usecase;

import com.example.bank.demo.domain.adapters.service.OperationsReportUseCaseService;
import com.example.bank.demo.domain.dto.response.OperationReportResponseDto;
import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.ports.mapper.OperationReportResponseDtoMapperPort;
import com.example.bank.demo.domain.utils.DateProvider;
import com.example.bank.demo.infrastructure.repository.BankAccountRepository;
import com.example.bank.demo.infrastructure.repository.BankRepository;
import com.example.bank.demo.infrastructure.repository.OperationRepository;
import com.example.bank.demo.infrastructure.repository.SavingAccountRepository;
import org.json.JSONException;
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
import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.DEPOT;
import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.RETRAIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperationReportUseCaseServiceTest {

    @Mock
    private BankRepository bankRepository;

    @Mock
    private DateProvider dateProvider;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private SavingAccountRepository savingAccountRepository;

    @Mock
    private OperationReportResponseDtoMapperPort operationReportResponseDtoMapperPort;

    @InjectMocks
    private OperationsReportUseCaseService operationsReportUseCaseService;

    @Test
    void shouldFindByAccountIdOperationsSortedByOperationDateDesc() throws JSONException {
        BankAccount account = new BankAccount(1L, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));

        Operation expectedOperation1 = new Operation(1L, account, DEPOT, account.getBalance(), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 15));
        Operation expectedOperation2 = new Operation(2L, account, DEPOT, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 30));
        Operation expectedOperation3 = new Operation(3L, account, RETRAIT, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 35));
        Operation expectedOperation4 = new Operation(4L, account, DEPOT, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 40));
        Operation expectedOperation5 = new Operation(5L, account, RETRAIT, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 45));

        List<Operation> operations = List.of(expectedOperation1, expectedOperation2, expectedOperation3, expectedOperation4, expectedOperation5);

        OperationReportResponseDto expectedOperations = OperationReportResponseDto.builder()
                .accountNumber("745c6891-1122-11ef-bee2-0242ac170002")
                .totalNumberOfOperations(5)
                .operations(operations)
                .build();

        when(bankRepository.findById(1L)).thenReturn(Optional.of(account));
        when(operationRepository.findByAccountIdOrderByDateOperationDesc(account)).thenReturn(Optional.of(operations));
        when(operationReportResponseDtoMapperPort.mapToOperationResponseDto(operations, account.getAccountNumber())).thenReturn(expectedOperations);

        OperationReportResponseDto operationReportResponseDto = operationsReportUseCaseService.getOperationsReport(1L);

        assertThat(operationReportResponseDto).isNotNull();
        assertThat(operationReportResponseDto.getAccountNumber()).isEqualTo("745c6891-1122-11ef-bee2-0242ac170002");
        assertThat(operationReportResponseDto.getTotalNumberOfOperations()).isEqualTo(5);
        assertThat(operationReportResponseDto.getOperations()).containsAll(operations);

    }
}
