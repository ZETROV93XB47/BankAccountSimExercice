package com.example.bank.demo.infrastructure.adapters.mappers;

import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.ports.mapper.BankAccountMapperPort;
import com.example.bank.demo.domain.ports.mapper.OperationMapperPort;
import com.example.bank.demo.infrastructure.entity.BankAccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankAccountMapperAdapter implements BankAccountMapperPort {

    private final OperationMapperPort operationMapperPort;

    @Override
    public BankAccount mapToBankAccount(BankAccountEntity bankAccountEntity) {


        return BankAccount.builder()
                .operations(operationMapperPort.mapToOperation(bankAccountEntity.getOperations()))
                .overdraftLimit(bankAccountEntity.getOverdraftLimit())
                .accountNumber(bankAccountEntity.getAccountNumber())
                .accountType(bankAccountEntity.getAccountType())
                .accountId(bankAccountEntity.getAccountId())
                .balance(bankAccountEntity.getBalance())
                .build();
    }
}
