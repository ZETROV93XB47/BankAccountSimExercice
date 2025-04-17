package com.example.bank.demo.infrastructure.adapters.mappers;

import com.example.bank.demo.domain.model.Bank;
import com.example.bank.demo.infrastructure.entity.BankEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MBankMapperAdapter {
    BankEntity mapFromBankModelToBankEntity(Bank bank);

    Bank mapFromBankEntityToBankModel(BankEntity bankEntity);
}
