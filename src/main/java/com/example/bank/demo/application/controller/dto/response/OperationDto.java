package com.example.bank.demo.application.controller.dto.response;

import com.example.bank.demo.domain.model.enumpackage.AccountType;
import com.example.bank.demo.domain.model.enumpackage.TypeOperation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//TODO: Utiliser ce Dto pour remplacer l'entité Operation renvoyée lors des opération de report
@Getter
@Builder
@ToString
@EqualsAndHashCode
@Schema(name = "OperationDto")
public class OperationDto {

    @Schema(title = "Operation type", example = "DEPOSIT")
    private TypeOperation typeOperation;

    @Schema(title = "the amount of money used during this Operation", example = "20000")
    private BigDecimal montant;

    @Schema(title = "the type of the account related to this operation", example = "SAVING_ACCOUNT")
    private AccountType accountType;

    @Schema(title = "the date and the time where this operation hve been made", example = "2024-09-16T10:56:56")
    private LocalDateTime dateOperation;
}
