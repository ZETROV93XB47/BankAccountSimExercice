package com.example.bank.demo.infrastructure.adapters.mappers;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.ports.mapper.BankMapperPort;
import com.example.bank.demo.domain.ports.mapper.OperationMapperPort;
import com.example.bank.demo.infrastructure.entity.BankEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankMapperAdapter implements BankMapperPort {

    private final OperationMapperPort operationMapperPort;

    @Override
    public Bank mapToBank(BankEntity bankEntity) {
        return Bank.builder()
                .operations(operationMapperPort.mapToOperations(bankEntity.getOperations()))
                .accountNumber(bankEntity.getAccountNumber())
                .accountType(bankEntity.getAccountType())
                .accountId(bankEntity.getAccountId())
                .balance(bankEntity.getBalance())
                .build();
    }
}
