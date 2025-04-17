package com.example.bank.demo.infrastructure.adapters.out.saving_account;

import com.example.bank.demo.domain.model.SavingAccount;
import com.example.bank.demo.infrastructure.adapters.persistence.saving_account.SavingAccountJpa;
import com.example.bank.demo.infrastructure.adapters.persistence.saving_account.SavingAccountRepositoryAdapter;
import com.example.bank.demo.infrastructure.entity.SavingAccountEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static com.example.bank.demo.domain.model.enumpackage.AccountType.SAVING_ACCOUNT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SavingAccountRepositoryAdapterTest {

    @Mock
    private SavingAccountJpa savingAccountJpa;

    @InjectMocks
    private SavingAccountRepositoryAdapter savingAccountRepositoryAdapter;

    @Test
    void shouldFindSavingAccount() {

        SavingAccount savingAccount = new SavingAccount(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("100.00"), SAVING_ACCOUNT, new ArrayList<>(), new BigDecimal("1000.00"));
        SavingAccountEntity savingAccountEntity = new SavingAccountEntity(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170002"), new BigDecimal("100.00"), SAVING_ACCOUNT, new ArrayList<>(), new BigDecimal("1000.00"));

        when(savingAccountJpa.save(savingAccountEntity)).thenReturn(savingAccountEntity);
        when(savingAccountJpa.findById(savingAccount.getAccountId())).thenReturn(Optional.of(savingAccountEntity));

        Long savedId = savingAccountRepositoryAdapter.saveSavingAccount(savingAccount).getAccountId();

        Optional<SavingAccount> foundedSavingAccount = savingAccountRepositoryAdapter.findById(savedId);

        verify(savingAccountJpa).save(savingAccountEntity);
        verify(savingAccountJpa).findById(savingAccount.getAccountId());

        assertThat(foundedSavingAccount).isNotEmpty();
        assertThat(foundedSavingAccount).contains(savingAccount);
    }

    @Test
    void shouldSaveSavingAccount() {
        SavingAccount savingAccount = new SavingAccount(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170003"), new BigDecimal("100.00"), SAVING_ACCOUNT, new ArrayList<>(), new BigDecimal("1000.00"));
        SavingAccountEntity savingAccountEntity = new SavingAccountEntity(null, UUID.fromString("745c6891-1122-11ef-bee2-0242ac170003"), new BigDecimal("100.00"), SAVING_ACCOUNT, new ArrayList<>(), new BigDecimal("1000.00"));

        when(savingAccountJpa.save(savingAccountEntity)).thenReturn(savingAccountEntity);

        SavingAccount savedBank = savingAccountRepositoryAdapter.saveSavingAccount(savingAccount);

        verify(savingAccountJpa).save(savingAccountEntity);

        assertThat(savedBank)
                .isNotNull()
                .isEqualTo(savingAccount);
    }
}