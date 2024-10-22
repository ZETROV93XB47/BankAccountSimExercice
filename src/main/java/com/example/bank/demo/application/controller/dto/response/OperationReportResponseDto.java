package com.example.bank.demo.application.controller.dto.response;

import com.example.bank.demo.domain.model.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@Schema(name = "OperationReportResponse")
public class OperationReportResponseDto {

    @Schema(title = "account number", example = "b10f10ab-e638-4bde-9b28-1f45e9eb2424")
    private String accountNumber;

    @Schema(title = "total number of operations on this account")
    private int totalNumberOfOperations;

    @Schema(title = "List of operations related to this account")
    private List<Operation> operations;
}
