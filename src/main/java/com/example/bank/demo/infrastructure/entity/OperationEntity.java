package com.example.bank.demo.infrastructure.entity;

import com.example.bank.demo.domain.model.enumpackage.AccountType;
import com.example.bank.demo.domain.model.enumpackage.TypeOperation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
//@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operation")
public class OperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "accountid", nullable = false)
    private BankEntity accountId;

    @Column(name = "typeoperation", nullable = false)
    private TypeOperation typeOperation;

    @Column(nullable = false)
    private BigDecimal montant;

    @Column(name = "accounttype", nullable = false)
    private AccountType accountType;

    @Column(name = "dateoperation", nullable = false)
    private LocalDateTime dateOperation;
}