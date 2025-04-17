package com.example.bank.demo.infrastructure.entity;

import com.example.bank.demo.domain.model.enumpackage.AccountType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class BankEntity implements Serializable {
    @Id
    @Column(name = "accountid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long accountId;

    @Column(name = "accountnumber", columnDefinition = "BINARY(16) NOT NULL UNIQUE", nullable = false)
    protected UUID accountNumber;

    @Column(name = "balance", nullable = false)
    protected BigDecimal balance;

    @Column(name = "accounttype", nullable = false)
    protected AccountType accountType;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "accountId", cascade = CascadeType.PERSIST)
    private List<OperationEntity> operations;
}