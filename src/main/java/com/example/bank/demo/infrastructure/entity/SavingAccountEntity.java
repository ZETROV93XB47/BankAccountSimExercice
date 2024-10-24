package com.example.bank.demo.infrastructure.entity;

import com.example.bank.demo.domain.model.enumpackage.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

//TODO: Séparer les Entité du domaine pour toutes les Entities

@Setter
@Getter
@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "saving_account")
public class SavingAccountEntity extends BankEntity {

    @Column(name = "depositlimit", nullable = false)
    private BigDecimal depositLimit;

    public SavingAccountEntity(Long accountId, UUID accountNumber, BigDecimal balance, AccountType accountType, List<OperationEntity> operations, BigDecimal depositLimit) {
        super(accountId, accountNumber, balance, accountType, operations);

        this.depositLimit = depositLimit;
    }
}