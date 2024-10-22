package com.example.bank.demo.infrastructure.adapters.saving_account.operation;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.ports.persistance.operation.OperationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OperationRepositoryAdapter implements OperationRepositoryPort {

    private final OperationJpa operationJPA;

    @Override
    public Optional<List<Operation>> findByAccountIdOrderByDateOperationDesc(Bank accountId) {
        return operationJPA.findByAccountIdOrderByDateOperationDesc(accountId);
    }

    @Override
    public List<Operation> findAll() {
        return operationJPA.findAll();
    }

    @Override
    public Operation saveOperation(Operation operation) {
        return operationJPA.save(operation);
    }
}
