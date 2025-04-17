package com.example.bank.demo.domain.model;

import com.example.bank.demo.domain.model.enumpackage.AccountType;
import com.example.bank.demo.domain.model.enumpackage.TypeOperation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//TODO: rajouter doc sur cette classe

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Operation {

    private Long id;

    private Bank accountId;

    private TypeOperation typeOperation;

    private BigDecimal montant;

    private AccountType accountType;

    private LocalDateTime dateOperation;
}