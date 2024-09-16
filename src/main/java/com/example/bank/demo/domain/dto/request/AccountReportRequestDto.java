package com.example.bank.demo.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@ToString
@Jacksonized
@EqualsAndHashCode
@Schema(name = "AccountReportRequest")
public class AccountReportRequestDto {

    @NotNull(message = "accountId cannot be null")
    @Schema(title = "Account making the withdrawal request ID", example = "10L")
    private Long accountId;
}

