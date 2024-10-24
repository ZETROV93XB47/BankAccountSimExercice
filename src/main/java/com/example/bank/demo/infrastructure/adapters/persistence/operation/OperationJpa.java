package com.example.bank.demo.infrastructure.adapters.persistence.operation;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperationJpa extends JpaRepository<Operation, Long> {
    Optional<List<Operation>> findByAccountIdOrderByDateOperationDesc(Bank accountId);
}
