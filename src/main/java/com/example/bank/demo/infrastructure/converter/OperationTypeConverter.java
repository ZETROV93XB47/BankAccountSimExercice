package com.example.bank.demo.infrastructure.converter;

import com.example.bank.demo.domain.model.enumpackage.TypeOperation;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OperationTypeConverter implements AttributeConverter<TypeOperation, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TypeOperation category) {
        return category == null ? null : category.getCode();
    }

    @Override
    public TypeOperation convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return TypeOperation.getOperationTypeByCode(code);
    }
}
