package com.example.bank.demo.domain.model;

import com.example.bank.demo.domain.model.enumpackage.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "bank_account")
public class BankAccount extends Bank {

    @Column(name = "overdraft",  nullable = false)
    private BigDecimal overdraftLimit;

    public BankAccount(Long accountId, UUID accountNumber, BigDecimal balance, AccountType accountType, List<Operation> operations, BigDecimal overdraftLimit) {
        super(accountId, accountNumber, balance, accountType, operations);

        this.overdraftLimit = overdraftLimit;
    }
}
