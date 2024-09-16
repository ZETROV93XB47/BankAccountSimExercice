package com.example.bank.demo.domain.dto.response;

import com.example.bank.demo.domain.model.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@Schema(name = "AccountReportResponseDto")
public class AccountReportResponseDto {

    @Schema(title = "account number", example = "b10f10ab-e638-4bde-9b28-1f45e9eb2424")
    private String accountNumber;

    @Schema(title = "Account making the withdrawal request ID", example = "10L")
    List<Operation> operations;
}
