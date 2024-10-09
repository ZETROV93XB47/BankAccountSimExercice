package com.example.bank.demo.application.controller.api;

import com.example.bank.demo.application.controller.handler.error.model.ErrorResponseDto;
import com.example.bank.demo.domain.dto.request.AccountReportRequestDto;
import com.example.bank.demo.domain.dto.request.DepositRequestDto;
import com.example.bank.demo.domain.dto.request.WithdrawalRequestDto;
import com.example.bank.demo.domain.dto.response.DepositResponseDto;
import com.example.bank.demo.domain.dto.response.OperationReportResponseDto;
import com.example.bank.demo.domain.dto.response.WithdrawalResponseDto;
import com.example.bank.demo.domain.ports.useCase.MakeDepositUseCase;
import com.example.bank.demo.domain.ports.useCase.MakeWithDrawalUseCase;
import com.example.bank.demo.domain.ports.useCase.OperationsReportUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "BankAccountController")
@RequestMapping("/bank/services")
public class BankAccountController {

    private final MakeDepositUseCase makeDepositUseCase;
    private final MakeWithDrawalUseCase makeWithDrawalUseCase;
    private final OperationsReportUseCase operationsReportUseCase;

    @Operation(summary = "Make a Deposit",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = DepositResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Operation forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @PostMapping(value = "/deposit", produces = APPLICATION_JSON_VALUE)
    public DepositResponseDto makeDeposit(@Valid @RequestBody DepositRequestDto requestDto) {
        return makeDepositUseCase.makeDeposit(requestDto.getDepositAmount(), requestDto.getAccountId());
    }

    @Operation(summary = "Make a Withdrawal",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = DepositResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Operation forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @PostMapping(value = "/withdrawal", produces = APPLICATION_JSON_VALUE)
    public WithdrawalResponseDto makeWithdrawal(@Valid @RequestBody WithdrawalRequestDto requestDto) {
        return makeWithDrawalUseCase.makeWithdrawal(requestDto.getWithdrawalAmount(), requestDto.getAccountId());
    }

    @Operation(summary = "Consult Operations related to an account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = OperationReportResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @PostMapping(value = "/report", produces = APPLICATION_JSON_VALUE)
    public OperationReportResponseDto getOperations(@Valid @RequestBody AccountReportRequestDto requestDto) {
        return operationsReportUseCase.getOperationsReport(requestDto.getAccountId());
    }

    @GetMapping(value = "/healthCheck", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> healthCheck() {
        return ok("Hello Guys !!! 😁🔥🔥🔥");
    }

}
