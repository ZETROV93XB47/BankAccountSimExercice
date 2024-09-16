package com.example.bank.demo.domain.ports.out.bank_account;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.model.BankAccount;

import java.util.Optional;

public interface BankAccountRepositoryPort {
    BankAccount saveBankAccount (BankAccount bankAccount);

    Optional<BankAccount> findById(Long id);
}
