package com.example.bank.demo.domain.service;

import com.example.bank.demo.application.controller.dto.response.OperationReportResponseDto;
import com.example.bank.demo.domain.exceptions.AccountNotFoundException;
import com.example.bank.demo.domain.exceptions.OperationNotFoundException;
import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.ports.mapper.OperationReportResponseDtoMapperPort;
import com.example.bank.demo.domain.ports.persistance.bank.BankRepositoryPort;
import com.example.bank.demo.domain.ports.persistance.operation.OperationRepositoryPort;
import com.example.bank.demo.domain.ports.useCase.OperationsReportUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationsReportUseCaseService implements OperationsReportUseCase {

    private final BankRepositoryPort bankRepositoryPort;
    private final OperationRepositoryPort operationRepositoryPort;
    private final OperationReportResponseDtoMapperPort operationReportResponseDtoMapperPort;

    @Override
    public OperationReportResponseDto getOperationsReport(Long accountId) {
        Bank accountForOperations;

        Optional<Bank> account = bankRepositoryPort.findById(accountId);

        if (account.isPresent()) {
            accountForOperations = account.get();
            log.info("account found for id : {}", accountForOperations.getAccountId());

        } else {
            throw new AccountNotFoundException("Account not found for id : " + accountId);
        }

        List<Operation> operations = operationRepositoryPort.findByAccountIdOrderByDateOperationDesc(accountForOperations)
                .orElseThrow(() -> new OperationNotFoundException("No Operation found for account Id : " + accountId));

        log.info("Associated Operations on this account found ");

        return operationReportResponseDtoMapperPort.mapToOperationResponseDto(operations, accountForOperations.getAccountNumber());
    }
}
