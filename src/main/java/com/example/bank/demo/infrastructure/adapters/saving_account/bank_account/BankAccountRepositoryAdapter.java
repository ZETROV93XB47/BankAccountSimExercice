package com.example.bank.demo.infrastructure.adapters.saving_account.bank_account;

import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.ports.persistance.bank_account.BankAccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BankAccountRepositoryAdapter implements BankAccountRepositoryPort {

    private final BankAccountJpa bankAccountJpa;

    @Override
    public BankAccount saveBankAccount(BankAccount bankAccount) {
        return bankAccountJpa.save(bankAccount);
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccountJpa.findById(id);
    }
}
