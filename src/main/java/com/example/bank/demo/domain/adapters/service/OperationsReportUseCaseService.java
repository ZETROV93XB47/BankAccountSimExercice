package com.example.bank.demo.domain.adapters.service;

import com.example.bank.demo.domain.dto.response.OperationReportResponseDto;
import com.example.bank.demo.domain.exceptions.AccountNotFoundException;
import com.example.bank.demo.domain.exceptions.OperationNotFoundException;
import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.ports.mapper.OperationReportResponseDtoMapperPort;
import com.example.bank.demo.domain.ports.out.bank.BankRepositoryPort;
import com.example.bank.demo.domain.ports.out.operation.OperationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OperationsReportUseCaseService implements com.example.bank.demo.domain.ports.useCase.OperationsReportUseCase {

    private final BankRepositoryPort bankRepositoryPort;
    private final OperationRepositoryPort operationRepositoryPort;
    private final OperationReportResponseDtoMapperPort operationReportResponseDtoMapperPort;
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationsReportUseCaseService.class);

    @Override
    public OperationReportResponseDto getOperationsReport(Long accountId) {
        Bank accountForOperations;

        Optional<Bank> account = bankRepositoryPort.findById(accountId);

        if (account.isPresent()) {
            accountForOperations = account.get();
            LOGGER.info("account found for id : {}", accountForOperations.getAccountId());

        } else {
            throw new AccountNotFoundException("Account not found for id : " + accountId);
        }

        List<Operation> operations = operationRepositoryPort.findByAccountIdOrderByDateOperationDesc(accountForOperations)
                .orElseThrow(() -> new OperationNotFoundException("No Operation found for account Id : " + accountId));

        LOGGER.info("Associated Operations on this account found ");

        return operationReportResponseDtoMapperPort.mapToOperationResponseDto(operations, accountForOperations.getAccountNumber());
    }
}
