package com.example.bank.demo.application.controller.handler.error.model;

import com.example.bank.demo.application.controller.handler.error.enumpackage.ErrorCodeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorResponseDto {
    private final String message;
    private final int httpStatusCode;
    private final ErrorCodeType errorCodeType;
}