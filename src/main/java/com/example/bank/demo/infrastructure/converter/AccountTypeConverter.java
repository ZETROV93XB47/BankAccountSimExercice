package com.example.bank.demo.infrastructure.converter;

import com.example.bank.demo.domain.model.enumpackage.AccountType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AccountTypeConverter implements AttributeConverter<AccountType, Integer>{
    @Override
    public Integer convertToDatabaseColumn(AccountType category) {
        return category == null ? null : category.getCode();
    }

    @Override
    public AccountType convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return AccountType.getAccountTypeByCode(code);
    }
}
