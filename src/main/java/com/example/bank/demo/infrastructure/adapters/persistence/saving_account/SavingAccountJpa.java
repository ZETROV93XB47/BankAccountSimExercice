package com.example.bank.demo.infrastructure.adapters.persistence.saving_account;

import com.example.bank.demo.domain.model.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingAccountJpa extends JpaRepository<SavingAccount, Long> {
}