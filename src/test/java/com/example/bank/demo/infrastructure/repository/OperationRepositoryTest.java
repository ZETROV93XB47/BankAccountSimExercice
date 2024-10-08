package com.example.bank.demo.infrastructure.repository;

import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.ports.out.bank_account.BankAccountRepositoryPort;
import com.example.bank.demo.domain.ports.out.operation.OperationRepositoryPort;
import com.example.bank.demo.infrastructure.utils.BaseIntegTest;
import com.example.bank.demo.infrastructure.utils.TransactionalTestingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.bank.demo.domain.model.enumpackage.AccountType.CLASSIC_ACCOUNT;
import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.DEPOSIT;
import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.WITHDRAWAL;
import static org.assertj.core.api.Assertions.assertThat;

class OperationRepositoryTest extends BaseIntegTest {

    @Autowired
    private OperationRepositoryPort operationRepositoryPort;

    @Autowired
    private BankAccountRepositoryPort bankAccountRepositoryPort;

    @Autowired
    private TransactionalTestingService transactionalTestingService;

    @Test
    @DirtiesContext
    void shouldFindAndSortByDateDescOperationsRelatedToAccount() throws Throwable {
        BankAccount account = new BankAccount(1L, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));

        Operation expectedOperation1 = new Operation(1L, account, DEPOSIT, account.getBalance(), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 15));
        Operation expectedOperation2 = new Operation(2L, account, DEPOSIT, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 30));
        Operation expectedOperation3 = new Operation(3L, account, WITHDRAWAL, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 35));
        Operation expectedOperation4 = new Operation(4L, account, DEPOSIT, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 40));
        Operation expectedOperation5 = new Operation(5L, account, WITHDRAWAL, new BigDecimal("100.00"), account.getAccountType(), LocalDateTime.of(2024, 5, 14, 16, 24, 45));

        transactionalTestingService.inNewTransaction(() -> {

            bankAccountRepositoryPort.saveBankAccount(account);
            operationRepositoryPort.saveOperation(expectedOperation1);
            operationRepositoryPort.saveOperation(expectedOperation2);
            operationRepositoryPort.saveOperation(expectedOperation3);
            operationRepositoryPort.saveOperation(expectedOperation4);
            operationRepositoryPort.saveOperation(expectedOperation5);

        });

        Optional<List<Operation>> operations = transactionalTestingService.inNewTransaction(() -> operationRepositoryPort.findByAccountIdOrderByDateOperationDesc(account));

        assertThat(operations).isNotEmpty();
        assertThat(operations.get())
                .hasSize(5)
                .isSortedAccordingTo(Comparator.comparing(Operation::getDateOperation).reversed());
    }
}