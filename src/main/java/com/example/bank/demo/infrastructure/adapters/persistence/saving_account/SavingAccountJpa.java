package com.example.bank.demo.infrastructure.adapters.persistence.saving_account;

import com.example.bank.demo.infrastructure.entity.SavingAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingAccountJpa extends JpaRepository<SavingAccountEntity, Long> {
}