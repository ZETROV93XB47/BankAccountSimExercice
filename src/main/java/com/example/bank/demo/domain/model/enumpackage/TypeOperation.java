package com.example.bank.demo.domain.model.enumpackage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum TypeOperation {
    WITHDRAWAL(1),
    DEPOSIT(2);

    private final Integer code;

    public static TypeOperation getOperationTypeByCode(int code) {
        return stream(values()).filter(typeOperation -> typeOperation.getCode() == code).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}