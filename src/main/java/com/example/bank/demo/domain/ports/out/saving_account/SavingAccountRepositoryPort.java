package com.example.bank.demo.domain.ports.out.saving_account;

import com.example.bank.demo.domain.model.SavingAccount;

import java.util.Optional;

public interface SavingAccountRepositoryPort {
    SavingAccount saveSavingAccount (SavingAccount savingAccount);

    Optional<SavingAccount> findById(Long id);
}
