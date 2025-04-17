package com.example.bank.demo.domain.model;

import com.example.bank.demo.domain.model.enumpackage.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

//TODO: Revoir la doc de cette classe
@Getter
@Setter
@ToString
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
public class Bank {

    @Schema(title = "Operation type", example = "DEPOSIT")
    protected Long accountId;

    @Schema(title = "Operation type", example = "DEPOSIT")
    protected UUID accountNumber;

    @Schema(title = "Operation type", example = "DEPOSIT")
    protected BigDecimal balance;

    @Schema(title = "Operation type", example = "DEPOSIT")
    protected AccountType accountType;

    @Schema(title = "Operation type", example = "DEPOSIT")
    private List<Operation> operations;
}