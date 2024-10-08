package com.example.bank.demo.domain.usecase;

import com.example.bank.demo.domain.adapters.service.MakeDepositUseCaseService;
import com.example.bank.demo.domain.dto.response.DepositResponseDto;
import com.example.bank.demo.domain.exceptions.AccountNotFoundException;
import com.example.bank.demo.domain.exceptions.DepositLimitExceededException;
import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.model.Operation;
import com.example.bank.demo.domain.model.SavingAccount;
import com.example.bank.demo.domain.ports.mapper.DepositResponseDtoMapperPort;
import com.example.bank.demo.domain.ports.out.bank.BankRepositoryPort;
import com.example.bank.demo.domain.ports.out.bank_account.BankAccountRepositoryPort;
import com.example.bank.demo.domain.ports.out.operation.OperationRepositoryPort;
import com.example.bank.demo.domain.ports.out.saving_account.SavingAccountRepositoryPort;
import com.example.bank.demo.domain.utils.DateProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static com.example.bank.demo.domain.model.enumpackage.AccountType.CLASSIC_ACCOUNT;
import static com.example.bank.demo.domain.model.enumpackage.AccountType.SAVING_ACCOUNT;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MakeDepositUseCaseServiceTest {

    @Mock
     private BankRepositoryPort bankRepositoryPort;

    @Mock
    private DateProvider dateProvider;

    @Mock
    private OperationRepositoryPort operationRepositoryPort;

    @Mock
    private BankAccountRepositoryPort bankAccountRepositoryPort;

    @Mock
    private SavingAccountRepositoryPort savingAccountRepositoryPort;

    @Mock
    private DepositResponseDtoMapperPort depositResponseDtoMapperPort;

    @InjectMocks
    private MakeDepositUseCaseService makeDepositUseCaseService;

    @Test
    void shouldMakeDepositForNormalAccount() {
        //Given
        int depositValue = 100;
        BankAccount account = new BankAccount(1L, UUID.randomUUID(), new BigDecimal(depositValue), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal(0));
        DepositResponseDto responseDto = DepositResponseDto.builder().accountNumber(account.getAccountNumber().toString()).balance(account.getBalance()).build();

        //When
        when(bankRepositoryPort.findById(1L)).thenReturn(Optional.of(account));
        when(depositResponseDtoMapperPort.mapToDepositResponseDto(account)).thenReturn(responseDto);
        when(dateProvider.getCurrentDate()).thenReturn(LocalDateTime.of(2024, 5, 14, 16, 24, 30));

        DepositResponseDto response = makeDepositUseCaseService.makeDeposit(new BigDecimal(depositValue), 1L);

        //Then
        verify(bankRepositoryPort, times(1)).findById(1L);
        verify(operationRepositoryPort, times(1)).saveOperation(any(Operation.class));
        verify(depositResponseDtoMapperPort, times(1)).mapToDepositResponseDto(account);

        assertThat(response.getBalance()).isEqualTo(new BigDecimal(depositValue));
    }

    @Test
    void shouldMakeDepositForSavingAccount() {
        //Given
        int initBalance = 100;
        SavingAccount account = new SavingAccount(1L, UUID.randomUUID(), new BigDecimal(initBalance), SAVING_ACCOUNT, new ArrayList<>(), new BigDecimal(1000));
        DepositResponseDto responseDto = DepositResponseDto.builder().accountNumber(account.getAccountNumber().toString()).balance(account.getBalance()).build();

        //When
        when(bankRepositoryPort.findById(1L)).thenReturn(Optional.of(account));
        when(depositResponseDtoMapperPort.mapToDepositResponseDto(account)).thenReturn(responseDto);
        when(dateProvider.getCurrentDate()).thenReturn(LocalDateTime.of(2024, 5, 14, 16, 24, 30));

        DepositResponseDto response = makeDepositUseCaseService.makeDeposit(new BigDecimal(initBalance), 1L);

        //Then
        verify(bankRepositoryPort, times(1)).findById(1L);
        verify(operationRepositoryPort, times(1)).saveOperation(any(Operation.class));
        verify(depositResponseDtoMapperPort, times(1)).mapToDepositResponseDto(account);

        assertThat(response.getBalance()).isEqualTo(new BigDecimal(initBalance));
    }

    @Test
    void shouldThrowAccountNotFoundException() {
        //Given
        int initBalance = 100;
        //When
        when(bankRepositoryPort.findById(1L)).thenThrow(new AccountNotFoundException("Account not found"));

        assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> makeDepositUseCaseService.makeDeposit(new BigDecimal(initBalance), 1L))
                .withMessage("Account not found");
    }

    @Test
    void shouldThrowDepositExceedLimitException() {
        //Given
        int initBalance = 100;
        SavingAccount account = new SavingAccount(1L, UUID.randomUUID(), new BigDecimal(initBalance), SAVING_ACCOUNT, new ArrayList<>(), new BigDecimal(1000));

        //When
        when(bankRepositoryPort.findById(1L)).thenReturn(Optional.of(account));

        assertThatExceptionOfType(DepositLimitExceededException.class)
                .isThrownBy(() -> makeDepositUseCaseService.makeDeposit(new BigDecimal(20000), 1L))
                .withMessage("Your Deposit value exceed the deposit limit on your account");
    }
}