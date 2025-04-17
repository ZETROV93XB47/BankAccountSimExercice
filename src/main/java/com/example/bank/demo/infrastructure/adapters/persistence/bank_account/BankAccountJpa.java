package com.example.bank.demo.infrastructure.adapters.persistence.bank_account;

import com.example.bank.demo.infrastructure.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountJpa extends JpaRepository<BankAccountEntity, Long> {

}
