package com.example.bank.demo.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@Schema(name = "DepositResponse")
public class DepositResponseDto {

    @Schema(title = "account number", example = "b10f10ab-e638-4bde-9b28-1f45e9eb2424")
    private String accountNumber;

    @Schema(title = "the amount of money on this account", example = "20000")
    private BigDecimal balance;
}
