package com.example.bank.demo.domain.ports.mapper;

import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.infrastructure.entity.OperationEntity;

import java.util.List;

public interface OperationMapperPort {
    List<Operation> mapToOperation(List<OperationEntity> operationEntity);
}
