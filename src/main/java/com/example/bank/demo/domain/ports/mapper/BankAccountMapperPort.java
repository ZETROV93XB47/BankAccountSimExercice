package com.example.bank.demo.domain.ports.mapper;

import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.infrastructure.entity.BankAccountEntity;

public interface BankAccountMapperPort {
    BankAccount mapToBankAccount(BankAccountEntity bankAccountEntity);
}
