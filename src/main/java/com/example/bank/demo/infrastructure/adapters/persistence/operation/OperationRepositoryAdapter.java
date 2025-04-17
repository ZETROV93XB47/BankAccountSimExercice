package com.example.bank.demo.infrastructure.adapters.persistence.operation;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.ports.mapper.OperationMapperPort;
import com.example.bank.demo.domain.ports.persistance.operation.OperationRepositoryPort;
import com.example.bank.demo.infrastructure.entity.BankEntity;
import com.example.bank.demo.infrastructure.entity.OperationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OperationRepositoryAdapter implements OperationRepositoryPort {

    private final OperationJpa operationJPA;
    private final OperationMapperPort operationMapperPort;

    //TODO: Tester rigoureusement cette méthode après, vérifier si on prend en compte
    // le cas optional.empty renvoyé par la méthode quand il ne trouve rien en base de données
    @Override
    public Optional<List<Operation>> findByAccountIdOrderByDateOperationDesc(Bank accountId) {

        return operationJPA.findByAccountIdOrderByDateOperationDesc(accountId)
                .map(operationMapperPort::mapToOperations);
    }

    @Override
    public List<Operation> findAll() {
        return operationMapperPort.mapToOperations(operationJPA.findAll());
    }

    @Override
    public Operation saveOperation(Operation operation) {
        OperationEntity operationEntity = OperationEntity.builder()
                .typeOperation(operation.getTypeOperation())
                .dateOperation(operation.getDateOperation())
                .accountType(operation.getAccountType())
                .montant(operation.getMontant())
                .accountId(BankEntity.builder()
                        .accountNumber(operation.getAccountId().getAccountNumber())
                        .accountType(operation.getAccountId().getAccountType())
                        .accountId(operation.getAccountId().getAccountId())
                        .balance(operation.getAccountId().getBalance())
                        .build())
                .build();

        return operationMapperPort.mapToOperation(operationJPA.save(operationEntity));
    }
}
