package com.example.bank.demo.domain.model;

import com.example.bank.demo.domain.model.enumpackage.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

//TODO: Séparer les Entité du domaine pour toutes les Entities

@Setter
@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class SavingAccount extends Bank {

    @Column(name = "depositlimit",  nullable = false)
    private BigDecimal depositLimit;

    public SavingAccount(Long accountId, UUID accountNumber, BigDecimal balance, AccountType accountType, List<Operation> operations, BigDecimal depositLimit) {
        super(accountId, accountNumber, balance, accountType, operations);

        this.depositLimit = depositLimit;
    }
}