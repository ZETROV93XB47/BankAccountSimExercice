package com.example.bank.demo.infrastructure.adapters.mappers;

import com.example.bank.demo.domain.model.SavingAccount;
import com.example.bank.demo.infrastructure.entity.SavingAccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MSavingAccountMapperAdapter {
    SavingAccountEntity mapFromSavingAccountModelToSavingAccountEntity(SavingAccount savingAccount);

    SavingAccount mapFromSavingAccountEntityToSavingAccountModel(SavingAccountEntity savingAccountEntity);
}
