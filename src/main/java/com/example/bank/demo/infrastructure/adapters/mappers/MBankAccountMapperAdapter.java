package com.example.bank.demo.infrastructure.adapters.mappers;

import com.example.bank.demo.domain.model.BankAccount;
import com.example.bank.demo.infrastructure.entity.BankAccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MBankAccountMapperAdapter {
    BankAccountEntity mapFromBankAccountModelToBankAccountEntity(BankAccount bankAccount);

    BankAccount mapFromBankAccountEntityToBankAccountModel(BankAccountEntity bankAccountEntity);
}
