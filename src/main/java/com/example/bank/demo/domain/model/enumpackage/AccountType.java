package com.example.bank.demo.domain.model.enumpackage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Arrays.stream;


@Getter
@RequiredArgsConstructor
public enum AccountType {
    SAVING_ACCOUNT(1),
    CLASSIC_ACCOUNT(2);

    private final Integer code;

    public static AccountType getAccountTypeByCode(int code) {
        return stream(values()).filter(accountType -> accountType.getCode() == code).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
