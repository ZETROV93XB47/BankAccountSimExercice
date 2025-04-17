package com.example.bank.demo.infrastructure.adapters.persistence.saving_account;

import com.example.bank.demo.domain.model.SavingAccount;
import com.example.bank.demo.domain.ports.mapper.SavingAccountMapperPort;
import com.example.bank.demo.domain.ports.persistance.saving_account.SavingAccountRepositoryPort;
import com.example.bank.demo.infrastructure.entity.SavingAccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SavingAccountRepositoryAdapter implements SavingAccountRepositoryPort {

    private final SavingAccountJpa savingAccountJpa;
    private final SavingAccountMapperPort savingAccountMapperPort;

    @Override
    public SavingAccount saveSavingAccount(SavingAccount savingAccount) {
        SavingAccountEntity savingAccountEntity = SavingAccountEntity.builder()
                .accountId(savingAccount.getAccountId())
                .accountNumber(savingAccount.getAccountNumber())
                .balance(savingAccount.getBalance())
                .accountType(savingAccount.getAccountType())
                .depositLimit(savingAccount.getDepositLimit())
                .build();

        return savingAccountMapperPort.mapToSavingAccount(savingAccountJpa.save(savingAccountEntity));
    }

    @Override
    public Optional<SavingAccount> findById(Long id) {
        return savingAccountJpa.findById(id).map(savingAccountMapperPort::mapToSavingAccount);
    }
}
