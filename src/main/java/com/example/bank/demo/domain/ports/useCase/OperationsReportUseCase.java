package com.example.bank.demo.domain.ports.useCase;

import com.example.bank.demo.domain.dto.response.OperationReportResponseDto;

public interface OperationsReportUseCase {
    OperationReportResponseDto getOperationsReport(Long accountId);
}
