package com.example.bank.demo.domain.ports.out.bank;

import com.example.bank.demo.domain.model.Bank;

import java.util.Optional;

public interface BankRepositoryPort {
    Bank saveBank (Bank bank);

    Optional<Bank> findById(Long id);
}
