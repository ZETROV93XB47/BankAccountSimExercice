package com.example.bank.demo.domain.ports.out.operation;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.model.Operation;

import java.util.List;
import java.util.Optional;

public interface OperationRepositoryPort {
    Optional<List<Operation>> findByAccountIdOrderByDateOperationDesc(Bank accountId);

    Operation saveOperation (Operation operation);
}
