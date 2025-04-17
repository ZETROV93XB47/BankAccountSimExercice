package com.example.bank.demo.infrastructure.adapters.persistence.operation;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.infrastructure.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperationJpa extends JpaRepository<OperationEntity, Long> {
    Optional<List<OperationEntity>> findByAccountIdOrderByDateOperationDesc(Bank accountId);
}
