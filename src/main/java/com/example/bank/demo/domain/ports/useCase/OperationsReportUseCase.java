package com.example.bank.demo.domain.ports.useCase;

import com.example.bank.demo.application.controller.dto.response.OperationReportResponseDto;

public interface OperationsReportUseCase {
    OperationReportResponseDto getOperationsReport(Long accountId);
}
