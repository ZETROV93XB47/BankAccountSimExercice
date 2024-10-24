package com.example.bank.demo.infrastructure.adapters.mappers;

import com.example.bank.demo.domain.model.SavingAccount;
import com.example.bank.demo.domain.ports.mapper.OperationMapperPort;
import com.example.bank.demo.domain.ports.mapper.SavingAccountMapperPort;
import com.example.bank.demo.infrastructure.entity.SavingAccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SavingAccountMapperAdapter implements SavingAccountMapperPort {

    private final OperationMapperPort operationMapperPort;

    @Override
    public SavingAccount mapToSavingAccount(SavingAccountEntity savingAccountEntity) {
        return SavingAccount.builder()
                .accountNumber(savingAccountEntity.getAccountNumber())
                .accountType(savingAccountEntity.getAccountType())
                .accountId(savingAccountEntity.getAccountId())
                .balance(savingAccountEntity.getBalance())
                .depositLimit(savingAccountEntity.getDepositLimit())
                .operations(operationMapperPort.mapToOperation(savingAccountEntity.getOperations()))
                .build();
    }
}
