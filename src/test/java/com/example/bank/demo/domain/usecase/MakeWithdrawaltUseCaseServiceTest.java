package com.example.bank.demo.domain.usecase;

import com.example.bank.demo.domain.adapters.service.MakeWithdrawalUseCaseService;
import com.example.bank.demo.domain.dto.response.WithdrawalResponseDto;
import com.example.bank.demo.domain.exceptions.AccountNotFoundException;
import com.example.bank.demo.domain.exceptions.WithdrawalAmountBiggerThanBalanceException;
import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.domain.model.SavingAccount;
import com.example.bank.demo.domain.ports.mapper.WithdrawalResponseDtoMapperPort;
import com.example.bank.demo.domain.utils.DateProvider;
import com.example.bank.demo.infrastructure.repository.BankAccountRepository;
import com.example.bank.demo.infrastructure.repository.BankRepository;
import com.example.bank.demo.infrastructure.repository.OperationRepository;
import com.example.bank.demo.infrastructure.repository.SavingAccountRepository;
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
class MakeWithdrawaltUseCaseServiceTest {

    @Mock
    private BankRepository bankRepository;

    @Mock
    private DateProvider dateProvider;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private SavingAccountRepository savingAccountRepository;

    @Mock
    private WithdrawalResponseDtoMapperPort withdrawalResponseDtoMapperPort;

    @InjectMocks
    private MakeWithdrawalUseCaseService makeWithdrawalUseCaseService;

    @Test
    void shouldMakeWithdrawalForNormalAccount() {
        //Given
        int withdrawalValue = 100;
        BankAccount account = new BankAccount(1L, UUID.randomUUID(), new BigDecimal(withdrawalValue), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal(0));
        WithdrawalResponseDto responseDto = WithdrawalResponseDto.builder().accountNumber(account.getAccountNumber().toString()).balance(account.getBalance()).build();

        //When
        when(bankRepository.findById(1L)).thenReturn(Optional.of(account));
        when(withdrawalResponseDtoMapperPort.mapToWithdrawalResponseDto(account)).thenReturn(responseDto);
        when(dateProvider.getCurrentDate()).thenReturn(LocalDateTime.of(2024, 5, 14, 16, 24, 30));

        WithdrawalResponseDto response = makeWithdrawalUseCaseService.makeWithdrawal(new BigDecimal(withdrawalValue), 1L);

        //Then
        verify(bankRepository, times(1)).findById(1L);
        //verify(operationRepository, times(1)).save(any(Operation.class));
        verify(withdrawalResponseDtoMapperPort, times(1)).mapToWithdrawalResponseDto(account);

        assertThat(response.getBalance()).isEqualTo(new BigDecimal(withdrawalValue));
    }

    @Test
    void shouldMakeWithdrawalForSavingAccount() {
        //Given
        int initBalance = 100;
        SavingAccount account = new SavingAccount(1L, UUID.randomUUID(), new BigDecimal(initBalance), SAVING_ACCOUNT, new ArrayList<>(), new BigDecimal(1000));
        WithdrawalResponseDto responseDto = WithdrawalResponseDto.builder().accountNumber(account.getAccountNumber().toString()).balance(account.getBalance()).build();

        //When
        when(bankRepository.findById(1L)).thenReturn(Optional.of(account));
        when(withdrawalResponseDtoMapperPort.mapToWithdrawalResponseDto(account)).thenReturn(responseDto);
        when(dateProvider.getCurrentDate()).thenReturn(LocalDateTime.of(2024, 5, 14, 16, 24, 30));

        WithdrawalResponseDto response = makeWithdrawalUseCaseService.makeWithdrawal(new BigDecimal(initBalance), 1L);

        //Then
        verify(bankRepository, times(1)).findById(1L);
        //verify(operationRepository, times(1)).save(any(Operation.class));
        verify(withdrawalResponseDtoMapperPort, times(1)).mapToWithdrawalResponseDto(account);

        assertThat(response.getBalance()).isEqualTo(new BigDecimal(initBalance));
    }

    @Test
    void shouldThrowAccountNotFoundException() {
        //Given
        int initBalance = 100;
        //When
        when(bankRepository.findById(1L)).thenThrow(new AccountNotFoundException("Account not found"));

        assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> makeWithdrawalUseCaseService.makeWithdrawal(new BigDecimal(initBalance), 1L))
                .withMessage("Account not found");
    }

    @Test
    void shouldThrowWithdrawalAmountBiggerThanBalanceException() {
        //Given
        int withdrawalValue = 100;
        BankAccount account = new BankAccount(1L, UUID.randomUUID(), new BigDecimal(withdrawalValue), CLASSIC_ACCOUNT, new ArrayList<>(), new BigDecimal(10000));

        //When
        when(bankRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThatExceptionOfType(WithdrawalAmountBiggerThanBalanceException.class)
                .isThrownBy(() -> makeWithdrawalUseCaseService.makeWithdrawal(new BigDecimal(20000), 1L))
                .withMessage("Le montant que vous tentez de retirer est supérieur au montant autorisé pour votre compte");
    }
}