package com.example.bank.demo.domain.adapters.mappers;

import com.example.bank.demo.domain.dto.response.OperationReportResponseDto;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.ports.mapper.OperationReportResponseDtoMapperPort;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@NoArgsConstructor
public class OperationReportResponseDtoMapperAdapter implements OperationReportResponseDtoMapperPort {
    @Override
    public OperationReportResponseDto mapToOperationResponseDto(List<Operation> operation, UUID accountNumber) {
        return OperationReportResponseDto.builder()
                .accountNumber(accountNumber.toString())
                .totalNumberOfOperations(operation.size())
                .operations(operation)
                .build();
    }
}
