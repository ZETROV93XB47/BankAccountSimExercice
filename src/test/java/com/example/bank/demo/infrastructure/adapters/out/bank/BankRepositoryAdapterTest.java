package com.example.bank.demo.infrastructure.adapters.out.bank;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.model.SavingAccount;
import com.example.bank.demo.infrastructure.adapters.saving_account.bank.BankJpa;
import com.example.bank.demo.infrastructure.adapters.saving_account.bank.BankRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static com.example.bank.demo.domain.model.enumpackage.AccountType.CLASSIC_ACCOUNT;
import static com.example.bank.demo.domain.model.enumpackage.AccountType.SAVING_ACCOUNT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankRepositoryAdapterTest {

    @Mock
    private BankJpa bankJpa;

    @InjectMocks
    private BankRepositoryAdapter bankRepositoryAdapter;

    @Test
    void shouldFindSavingAccount() {

        SavingAccount savingAccount = new SavingAccount(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("100.00"), SAVING_ACCOUNT, new ArrayList<>(), new BigDecimal("1000.00"));

        when(bankJpa.save(savingAccount)).thenReturn(savingAccount);
        when(bankJpa.findById(savingAccount.getAccountId())).thenReturn(Optional.of(savingAccount));

        Long savedId = bankRepositoryAdapter.saveBank(savingAccount).getAccountId();

        Optional<Bank> bank = bankRepositoryAdapter.findById(savedId);

        verify(bankJpa).save(savingAccount);
        verify(bankJpa).findById(savingAccount.getAccountId());

        assertThat(bank).isNotEmpty();
        assertThat(bank).contains(savingAccount);
    }

    @Test
    void shouldFindBankAccount() {

        BankAccount bankAccount = new BankAccount(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170003"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));

        when(bankJpa.save(bankAccount)).thenReturn(bankAccount);
        when(bankJpa.findById(bankAccount.getAccountId())).thenReturn(Optional.of(bankAccount));

        Long savedId = bankRepositoryAdapter.saveBank(bankAccount).getAccountId();

        Optional<Bank> bank = bankRepositoryAdapter.findById(savedId);

        verify(bankJpa).save(bankAccount);
        verify(bankJpa).findById(bankAccount.getAccountId());

        assertThat(bank).isNotEmpty();
        assertThat(bank).contains(bankAccount);
    }

    //TODO: Vérifier la pertinence de ce test après
    @Test
    void shouldSaveBankAccount() {
        BankAccount bankAccount = new BankAccount(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170003"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));

        when(bankJpa.save(bankAccount)).thenReturn(bankAccount);

        Bank savedBank = bankRepositoryAdapter.saveBank(bankAccount);

        verify(bankJpa).save(bankAccount);

        assertThat(savedBank).isNotNull();
        assertThat(savedBank).isEqualTo(bankAccount);
    }

    //TODO: Vérifier la pertinence de ce test après
    @Test
    void shouldSaveSavingAccount() {
        SavingAccount savingAccount = new SavingAccount(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170003"), new BigDecimal("100.00"), SAVING_ACCOUNT, new ArrayList<>(), new BigDecimal("1000.00"));

        when(bankJpa.save(savingAccount)).thenReturn(savingAccount);

        Bank savedBank = bankRepositoryAdapter.saveBank(savingAccount);

        verify(bankJpa).save(savingAccount);

        assertThat(savedBank).isNotNull();
        assertThat(savedBank).isEqualTo(savingAccount);
    }
}