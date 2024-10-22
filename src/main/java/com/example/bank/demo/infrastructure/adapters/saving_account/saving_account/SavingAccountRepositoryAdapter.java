package com.example.bank.demo.infrastructure.adapters.saving_account.saving_account;

import com.example.bank.demo.domain.model.SavingAccount;
import com.example.bank.demo.domain.ports.persistance.saving_account.SavingAccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SavingAccountRepositoryAdapter implements SavingAccountRepositoryPort {

    private final SavingAccountJpa savingAccountJpa;

    @Override
    public SavingAccount saveSavingAccount(SavingAccount savingAccount) {
        return savingAccountJpa.save(savingAccount);
    }

    @Override
    public Optional<SavingAccount> findById(Long id) {
        return savingAccountJpa.findById(id);
    }
}
