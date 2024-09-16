package com.example.bank.demo.infrastructure.adapters.out.bank;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.ports.out.bank.BankRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BankRepositoryAdapter implements BankRepositoryPort {

    private final BankJpa bankJpa;

    @Override
    public Bank saveBank(Bank bank) {
        return bankJpa.save(bank);
    }

    @Override
    public Optional<Bank> findById(Long id) {
        return bankJpa.findById(id);
    }
}
