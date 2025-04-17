package com.example.bank.demo.infrastructure.adapters.persistence.bank;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.ports.mapper.BankMapperPort;
import com.example.bank.demo.domain.ports.persistance.bank.BankRepositoryPort;
import com.example.bank.demo.infrastructure.adapters.mappers.MBankAccountMapperAdapter;
import com.example.bank.demo.infrastructure.entity.BankEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BankRepositoryAdapter implements BankRepositoryPort {

    private final BankJpa bankJpa;
    private final BankMapperPort bankMapperPort;
    private final MBankAccountMapperAdapter mBankAccountMapperAdapter;

    @Override
    public Bank saveBank(Bank bank) {
        BankEntity bankEntity = BankEntity.builder()
                .accountNumber(bank.getAccountNumber())
                .accountType(bank.getAccountType())
                .accountId(bank.getAccountId())
                .balance(bank.getBalance())
                .build();

        return bankMapperPort.mapToBank(bankJpa.save(bankEntity));
    }

    @Override
    public Optional<Bank> findById(Long id) {
        return bankJpa.findById(id).map(bankMapperPort::mapToBank);
    }
}
