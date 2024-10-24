package com.example.bank.demo.domain.ports.mapper;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.infrastructure.entity.BankEntity;

public interface BankMapperPort {
    Bank mapToBank(BankEntity bank);
}
