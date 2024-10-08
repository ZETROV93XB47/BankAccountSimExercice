package com.example.bank.demo.infrastructure.repository;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.model.SavingAccount;
import com.example.bank.demo.domain.ports.out.bank.BankRepositoryPort;
import com.example.bank.demo.domain.ports.out.bank_account.BankAccountRepositoryPort;
import com.example.bank.demo.domain.ports.out.saving_account.SavingAccountRepositoryPort;
import com.example.bank.demo.infrastructure.utils.BaseIntegTest;
import com.example.bank.demo.infrastructure.utils.TransactionalTestingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static com.example.bank.demo.domain.model.enumpackage.AccountType.CLASSIC_ACCOUNT;
import static com.example.bank.demo.domain.model.enumpackage.AccountType.SAVING_ACCOUNT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BankRepositoryTest extends BaseIntegTest {

    @Autowired
    private BankRepositoryPort bankRepositoryPort;

    @Autowired
    private BankAccountRepositoryPort bankAccountRepositoryPort;

    @Autowired
    private SavingAccountRepositoryPort savingAccountRepositoryPort;

    @Autowired
    private TransactionalTestingService transactionalTestingService;

    @Test
    @DirtiesContext
    void shouldFindBankAccount() throws Throwable {
        BankAccount bankAccount = new BankAccount(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));
        Long savedId;

        savedId = transactionalTestingService.inNewTransaction(() -> bankRepositoryPort.saveBank(bankAccount).getAccountId());

        Optional<Bank> bank = transactionalTestingService.inNewTransaction(() -> bankRepositoryPort.findById(savedId));

        assertThat(bank).isNotEmpty();
        assertThat(bank).contains(bankAccount);
    }

    @Test
    @DirtiesContext
    void shouldFindSavingAccount() throws Throwable {
        SavingAccount savingAccount = new SavingAccount(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("100.00"), SAVING_ACCOUNT, new ArrayList<>(), new BigDecimal("1000.00"));
        Long savedId;

        savedId = transactionalTestingService.inNewTransaction(() -> savingAccountRepositoryPort.saveSavingAccount(savingAccount).getAccountId());

        Optional<Bank> bank = transactionalTestingService.inNewTransaction(() -> bankRepositoryPort.findById(savedId));

        assertThat(bank).isNotEmpty();
        assertThat(bank).contains(savingAccount);
    }

}