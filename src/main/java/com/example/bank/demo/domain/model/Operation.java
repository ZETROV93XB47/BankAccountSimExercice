package com.example.bank.demo.domain.model;

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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "accountid", nullable = false)
    private Bank accountId;

    @Column(name = "typeoperation", nullable = false)
    private TypeOperation typeOperation;

    @Column(nullable = false)
    private BigDecimal montant;

    @Column(name = "accounttype", nullable = false)
    private AccountType accountType;

    @Column(name = "dateoperation", nullable = false)
    private LocalDateTime dateOperation;
}