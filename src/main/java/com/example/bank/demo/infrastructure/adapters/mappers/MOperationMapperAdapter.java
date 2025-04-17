package com.example.bank.demo.infrastructure.adapters.mappers;

import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.infrastructure.entity.OperationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MOperationMapperAdapter {
    OperationEntity mapFromOperationModelToOperationEntity(Operation operation);

    Operation mapFromOperationEntityToOperationModel(OperationEntity operationEntity);
}
