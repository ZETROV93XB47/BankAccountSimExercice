package com.example.bank.demo.domain.service;

import com.example.bank.demo.application.controller.dto.response.WithdrawalResponseDto;
import com.example.bank.demo.domain.exceptions.AccountNotFoundException;
import com.example.bank.demo.domain.exceptions.WithdrawalAmountBiggerThanBalanceException;
import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.model.SavingAccount;
import com.example.bank.demo.domain.ports.mapper.WithdrawalResponseDtoMapperPort;
import com.example.bank.demo.domain.ports.persistance.bank.BankRepositoryPort;
import com.example.bank.demo.domain.ports.persistance.bank_account.BankAccountRepositoryPort;
import com.example.bank.demo.domain.ports.persistance.operation.OperationRepositoryPort;
import com.example.bank.demo.domain.ports.persistance.saving_account.SavingAccountRepositoryPort;
import com.example.bank.demo.domain.ports.useCase.MakeWithDrawalUseCase;
import com.example.bank.demo.domain.utils.DateProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.bank.demo.domain.model.enumpackage.TypeOperation.WITHDRAWAL;

@Slf4j
@Service
@RequiredArgsConstructor
public class MakeWithdrawalUseCaseService implements MakeWithDrawalUseCase {

    private final DateProvider dateProvider;
    private final BankRepositoryPort bankRepositoryPort;
    private final OperationRepositoryPort operationRepositoryPort;
    private final BankAccountRepositoryPort bankAccountRepositoryPort;
    private final SavingAccountRepositoryPort savingAccountRepositoryPort;
    private final WithdrawalResponseDtoMapperPort withdrawalResponseDtoMapperPort;
    private static final Logger LOGGER = LoggerFactory.getLogger(MakeWithdrawalUseCaseService.class);

    @Override
    @Transactional
    public WithdrawalResponseDto makeWithdrawal(final BigDecimal withdrawalValue, final Long accountId) {
        Bank accountForWithdrawal;

        Optional<? extends Bank> account = bankRepositoryPort.findById(accountId);

        if (account.isPresent()) {
            accountForWithdrawal = account.get();
            LOGGER.info("account found for id : {}", accountForWithdrawal.getAccountId());
        } else {
            throw new AccountNotFoundException("Account not found for id : " + accountId);
        }

        return switch (accountForWithdrawal.getAccountType()) {
            case SAVING_ACCOUNT -> makeWithdrawalOnSavingAccount((SavingAccount) accountForWithdrawal, withdrawalValue);
            case CLASSIC_ACCOUNT -> makeWithdrawaltOnNormalAccount((BankAccount) accountForWithdrawal, withdrawalValue);
        };
    }

    private WithdrawalResponseDto makeWithdrawaltOnNormalAccount(final BankAccount bankAccount, final BigDecimal withdrawalValue) {
        BigDecimal currentBalance = bankAccount.getBalance();

        if (currentBalance.subtract(withdrawalValue).compareTo(bankAccount.getOverdraftLimit()) < 0) {
            throw new WithdrawalAmountBiggerThanBalanceException("Le montant que vous tentez de retirer est supérieur au montant autorisé pour votre compte");
        }

        bankAccount.setBalance(currentBalance.subtract(withdrawalValue));
        bankAccountRepositoryPort.saveBankAccount(bankAccount);
        saveAssociatedOperation(withdrawalValue, bankAccount);

        return withdrawalResponseDtoMapperPort.mapToWithdrawalResponseDto(bankAccount);
    }

    //TODO: Retirer les Dto du domaine
    private WithdrawalResponseDto makeWithdrawalOnSavingAccount(final SavingAccount savingAccount, final BigDecimal withdrawalValue) {
        BigDecimal currentBalance = savingAccount.getBalance();

        if (currentBalance.subtract(withdrawalValue).compareTo(new BigDecimal(0)) < 0) {
            throw new WithdrawalAmountBiggerThanBalanceException("Le montant que vous tentez de retirer est supérieur à votre solde");
        }

        savingAccount.setBalance(currentBalance.subtract(withdrawalValue));
        savingAccountRepositoryPort.saveSavingAccount(savingAccount);
        saveAssociatedOperation(withdrawalValue, savingAccount);

        LOGGER.info("Account saved : {}", savingAccount);

        return withdrawalResponseDtoMapperPort.mapToWithdrawalResponseDto(savingAccount);
    }

    private void saveAssociatedOperation(final BigDecimal withdrawalValue, final Bank accountForwithdrawal) {
        Operation operation = new Operation(null, accountForwithdrawal, WITHDRAWAL, withdrawalValue, accountForwithdrawal.getAccountType(), dateProvider.getCurrentDate());

        operationRepositoryPort.saveOperation(operation);

        LOGGER.info("Associated Operation saved : {}", operation);
    }
}

