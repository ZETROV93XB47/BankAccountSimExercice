package com.example.bank.demo.domain.ports.persistance.bank;


import com.example.bank.demo.domain.model.Bank;

import java.util.Optional;

public interface BankRepositoryPort {
    Bank saveBank (Bank bank);

    Optional<Bank> findById(Long id);
}
