package com.example.bank.demo.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Builder
@Getter
@ToString
@Jacksonized
@EqualsAndHashCode
@Schema(name = "DepositRequest")
public class DepositRequestDto {

    @NotNull(message = "accountId cannot be null")
    @Schema(title = "Account making the deposit request ID", example = "10L")
    private  Long accountId;

    @NotNull(message = "depositAmount cannot be null")
    @DecimalMin(value = "1", message = "depositAmount must be greater than or equal to 1")
    @Schema(title = "the amount of money for the deposit", example = "20000")
    private  BigDecimal depositAmount;
}
