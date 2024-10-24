package com.example.bank.demo.domain.ports.mapper;

import com.example.bank.demo.domain.model.SavingAccount;
import com.example.bank.demo.infrastructure.entity.SavingAccountEntity;

public interface SavingAccountMapperPort {
    SavingAccount mapToSavingAccount(SavingAccountEntity savingAccountEntity);
}
