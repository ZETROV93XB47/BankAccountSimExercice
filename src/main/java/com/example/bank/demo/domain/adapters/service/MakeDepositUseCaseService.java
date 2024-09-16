package com.example.bank.demo.domain.adapters.service;

import com.example.bank.demo.domain.dto.response.DepositResponseDto;
import com.example.bank.demo.domain.exceptions.AccountNotFoundException;
import com.example.bank.demo.domain.exceptions.DepositLimitExceededException;
import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.model.SavingAccount;
import com.example.bank.demo.domain.ports.out.bank.BankRepositoryPort;
import com.example.bank.demo.domain.ports.out.bank_account.BankAccountRepositoryPort;
import com.example.bank.demo.domain.ports.out.operation.OperationRepositoryPort;
import com.example.bank.demo.domain.ports.mapper.DepositResponseDtoMapperPort;
import com.example.bank.demo.domain.ports.out.saving_account.SavingAccountRepositoryPort;
import com.example.bank.demo.domain.utils.DateProvider;
import com.example.bank.demo.infrastructure.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.DEPOT;

@Slf4j
@Component
@RequiredArgsConstructor
public class MakeDepositUseCaseService implements com.example.bank.demo.domain.ports.useCase.MakeDepositUseCase {

    private final DateProvider dateProvider;
    private final BankRepositoryPort bankRepositoryPort;
    private final OperationRepositoryPort operationRepositoryPort;
    private final BankAccountRepositoryPort bankAccountRepositoryPort;
    private final SavingAccountRepositoryPort savingAccountRepositoryPort;
    private final DepositResponseDtoMapperPort depositResponseDtoMapperPort;
    private static final Logger LOGGER = LoggerFactory.getLogger(MakeDepositUseCaseService.class);

    @Override
    @Transactional
    public DepositResponseDto makeDeposit(final BigDecimal depositValue, final Long accountId) {
        Bank accountForDeposit;

        Optional<Bank> account = bankRepositoryPort.findById(accountId);

        if (account.isPresent()) {
            accountForDeposit = account.get();
            LOGGER.info("account found for id : {}", accountForDeposit.getAccountId());

        } else {
            throw new AccountNotFoundException("Account not found for id : " + accountId);
        }

        return switch (accountForDeposit.getAccountType()) {
            case SAVING_ACCOUNT -> makeDepositOnSavingAccount((SavingAccount)accountForDeposit, depositValue);
            case CLASSIC_ACCOUNT -> makeDepositOnNormalAccount((BankAccount)accountForDeposit, depositValue);
        };
    }

    private DepositResponseDto makeDepositOnNormalAccount(final BankAccount bankAccount, final BigDecimal depositValue) {
        BigDecimal currentBalance = bankAccount.getBalance();
        bankAccount.setBalance(currentBalance.add(depositValue));
        bankAccountRepositoryPort.saveBankAccount(bankAccount);
        saveAssociatedOperation(depositValue, bankAccount);

        return depositResponseDtoMapperPort.mapToDepositResponseDto(bankAccount);
    }

    private DepositResponseDto makeDepositOnSavingAccount(final SavingAccount savingAccount, final BigDecimal depositValue) {
        BigDecimal currentBalance = savingAccount.getBalance();

        if(currentBalance.add(depositValue).compareTo(savingAccount.getDepositLimit()) > 0) {
            throw new DepositLimitExceededException("Your Deposit value exceed the deposit limit on your account");
        }

        savingAccount.setBalance(currentBalance.add(depositValue));
        savingAccountRepositoryPort.saveSavingAccount(savingAccount);
        saveAssociatedOperation(depositValue, savingAccount);

        LOGGER.info("Account saved : {}", savingAccount);

        return depositResponseDtoMapperPort.mapToDepositResponseDto(savingAccount);
    }

    private void saveAssociatedOperation(BigDecimal depositValue, Bank accountForDeposit) {
        Operation operation = new Operation(null, accountForDeposit, DEPOT, depositValue, accountForDeposit.getAccountType(), dateProvider.getCurrentDate());

        operationRepositoryPort.saveOperation(operation);

        LOGGER.info("Associated Operation saved : {}", operation);
    }
}
