package com.example.bank.demo.infrastructure.entity;

import com.example.bank.demo.domain.model.enumpackage.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@ToString
@SuperBuilder
@EqualsAndHashCode
@RequiredArgsConstructor
@Table(name = "bank_account")
public class BankAccountEntity extends BankEntity {

    @Column(name = "overdraft", nullable = false)
    private BigDecimal overdraftLimit;

    public BankAccountEntity(Long accountId, UUID accountNumber, BigDecimal balance, AccountType accountType, List<OperationEntity> operations, BigDecimal overdraftLimit) {
        super(accountId, accountNumber, balance, accountType, operations);
        this.overdraftLimit = overdraftLimit;
    }
}
