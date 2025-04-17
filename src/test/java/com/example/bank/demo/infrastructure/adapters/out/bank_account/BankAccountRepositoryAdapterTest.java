package com.example.bank.demo.infrastructure.adapters.out.bank_account;

import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.infrastructure.adapters.persistence.bank_account.BankAccountJpa;
import com.example.bank.demo.infrastructure.adapters.persistence.bank_account.BankAccountRepositoryAdapter;
import com.example.bank.demo.infrastructure.entity.BankAccountEntity;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountRepositoryAdapterTest {

    @Mock
    private BankAccountJpa bankAccountJpa;

    @InjectMocks
    private BankAccountRepositoryAdapter bankAccountRepositoryAdapter;

    @Test
    void shouldFindBankAccount() {

        BankAccountEntity bankAccountEntity = new BankAccountEntity(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170003"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));
        BankAccount bankAccount = new BankAccount(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170003"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));

        when(bankAccountJpa.save(bankAccountEntity)).thenReturn(bankAccountEntity);
        when(bankAccountJpa.findById(bankAccount.getAccountId())).thenReturn(Optional.of(bankAccountEntity));

        Long savedId = bankAccountRepositoryAdapter.saveBankAccount(bankAccount).getAccountId();

        Optional<BankAccount> bank = bankAccountRepositoryAdapter.findById(savedId);

        verify(bankAccountJpa).save(bankAccountEntity);
        verify(bankAccountJpa).findById(bankAccount.getAccountId());

        assertThat(bank).isNotEmpty();
        assertThat(bank).contains(bankAccount);
    }

    @Test
    void shouldSaveBankAccount() {
        BankAccount bankAccount = new BankAccount(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170003"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));
        BankAccountEntity bankAccountEntity = new BankAccountEntity(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170003"), new BigDecimal("100.00"), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal("0.00"));

        when(bankAccountJpa.save(bankAccountEntity)).thenReturn(bankAccountEntity);

        BankAccount savedBank = bankAccountRepositoryAdapter.saveBankAccount(bankAccount);

        verify(bankAccountJpa).save(bankAccountEntity);

        assertThat(savedBank)
                .isNotNull()
                .isEqualTo(bankAccount);
    }

}