package com.example.bank.demo.domain.ports.mapper;

import com.example.bank.demo.domain.dto.response.OperationReportResponseDto;
import com.example.bank.demo.domain.model.Operation;

import java.util.List;
import java.util.UUID;

public interface OperationReportResponseDtoMapperPort {
    OperationReportResponseDto mapToOperationResponseDto(List<Operation> operation, UUID accountNumber);
}
