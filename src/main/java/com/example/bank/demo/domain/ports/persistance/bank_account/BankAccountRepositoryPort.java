package com.example.bank.demo.domain.ports.persistance.bank_account;

import com.example.bank.demo.domain.model.BankAccount;

import java.util.Optional;

public interface BankAccountRepositoryPort {
    BankAccount saveBankAccount (BankAccount bankAccount);

    Optional<BankAccount> findById(Long id);
}
