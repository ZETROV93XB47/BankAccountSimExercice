package com.example.bank.demo.infrastructure.adapters.mappers;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.ports.mapper.OperationMapperPort;
import com.example.bank.demo.infrastructure.entity.BankEntity;
import com.example.bank.demo.infrastructure.entity.OperationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OperationMapperAdapter implements OperationMapperPort {

    @Override
    public List<Operation> mapToOperations(List<OperationEntity> operationEntity) {
        return operationEntity.stream()
                .map(operationEntity1 -> Operation.builder()
                        .accountId(createBank(operationEntity1.getAccountId()))
                        .dateOperation(operationEntity1.getDateOperation())
                        .typeOperation(operationEntity1.getTypeOperation())
                        .accountType(operationEntity1.getAccountType())
                        .id(operationEntity1.getId())
                        .build())
                .toList();
    }

    @Override
    public Operation mapToOperation(OperationEntity operationEntity) {
        return Operation.builder()
                .accountId(createBank(operationEntity.getAccountId()))
                .dateOperation(operationEntity.getDateOperation())
                .typeOperation(operationEntity.getTypeOperation())
                .accountType(operationEntity.getAccountType())
                .id(operationEntity.getId())
                .build();
    }

    //TODO: J'aurais du rajouter les operation normalement mais j'ai une dépendance circulaire en faisant ça
    //TODO: Voir si je ne peux pas apr la suite améliorer ça
    private Bank createBank(BankEntity bankEntity) {
        return Bank.builder()
                .accountNumber(bankEntity.getAccountNumber())
                .accountType(bankEntity.getAccountType())
                .accountId(bankEntity.getAccountId())
                .balance(bankEntity.getBalance())
                .build();
    }
}
