package com.example.bank.demo.infrastructure.adapters.out.operation;

import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.infrastructure.adapters.saving_account.operation.OperationJpa;
import com.example.bank.demo.infrastructure.adapters.saving_account.operation.OperationRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.bank.demo.domain.model.enumpackage.AccountType.CLASSIC_ACCOUNT;
import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.DEPOSIT;
import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.WITHDRAWAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperationRepositoryAdapterTest {

    @Mock
    private OperationJpa operationJpa;

    @InjectMocks
    private OperationRepositoryAdapter operationRepositoryAdapter;

    @Test
    void shouldFindAndSortByDateDescOperationsRelatedToAccount() throws Throwable {
        BankAccount account = new BankAccount(1L, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));

        Operation expectedOperation1 = new Operation(1L, account, DEPOSIT, account.getBalance(), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 15));
        Operation expectedOperation2 = new Operation(2L, account, DEPOSIT, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 30));
        Operation expectedOperation3 = new Operation(3L, account, WITHDRAWAL, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 35));
        Operation expectedOperation4 = new Operation(4L, account, DEPOSIT, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 40));
        Operation expectedOperation5 = new Operation(5L, account, WITHDRAWAL, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 45));

        List<Operation> operations = new ArrayList<>(List.of(expectedOperation5, expectedOperation4, expectedOperation3, expectedOperation2, expectedOperation1));

        when(operationJpa.findByAccountIdOrderByDateOperationDesc(account)).thenReturn(Optional.of(operations));

        Optional<List<Operation>> operationsResults = operationRepositoryAdapter.findByAccountIdOrderByDateOperationDesc(account);

        assertThat(operationsResults).isNotNull().isNotEmpty();
        assertThat(operationsResults.get())
                .hasSize(5)
                .isSortedAccordingTo(Comparator.comparing(Operation::getDateOperation).reversed());
    }


    @Test
    void shouldSaveOperation() {
        // Given
        Operation operation = new Operation(1L, new BankAccount(1L, UUID.randomUUID(), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00")), DEPOSIT, new BigDecimal("100.00"), CLASSIC_ACCOUNT, LocalDateTime.now());

        when(operationJpa.save(operation)).thenReturn(operation);

        // When
        Operation savedOperation = operationRepositoryAdapter.saveOperation(operation);

        // Then

        verify(operationJpa).save(operation);

        assertThat(savedOperation)
                .isNotNull()
                .isEqualTo(operation);
    }
}