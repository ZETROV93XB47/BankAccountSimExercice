package com.example.bank.demo.domain.model;

import com.example.bank.demo.domain.model.enumpackage.AccountType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Bank {
    @Id
    @Column(name = "accountid",  nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long accountId;

    @Column(name = "accountnumber",  columnDefinition = "BINARY(16) NOT NULL UNIQUE", nullable = false)
    protected UUID accountNumber;

    @Column(name = "balance",  nullable = false)
    protected BigDecimal balance;

    @Column(name = "accounttype", nullable = false)
    protected AccountType accountType;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "accountId", cascade = CascadeType.ALL)
    private List<Operation> operations = new ArrayList<>();
}