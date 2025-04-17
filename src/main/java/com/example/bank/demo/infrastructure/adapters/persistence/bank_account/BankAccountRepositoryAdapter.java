package com.example.bank.demo.infrastructure.adapters.persistence.bank_account;

import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.ports.mapper.BankAccountMapperPort;
import com.example.bank.demo.domain.ports.persistance.bank_account.BankAccountRepositoryPort;
import com.example.bank.demo.infrastructure.entity.BankAccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BankAccountRepositoryAdapter implements BankAccountRepositoryPort {

    private final BankAccountJpa bankAccountJpa;
    private final BankAccountMapperPort bankAccountMapperPort;

    @Override
    public BankAccount saveBankAccount(BankAccount bankAccount) {
        BankAccountEntity bankAccountEntity = BankAccountEntity.builder()
                .overdraftLimit(bankAccount.getOverdraftLimit())
                .accountNumber(bankAccount.getAccountNumber())
                .accountType(bankAccount.getAccountType())
                .accountId(bankAccount.getAccountId())
                .balance(bankAccount.getBalance())
                .build();

        return bankAccountMapperPort.mapToBankAccount(bankAccountJpa.save(bankAccountEntity));
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccountJpa.findById(id).map(bankAccountMapperPort::mapToBankAccount);
    }
}
