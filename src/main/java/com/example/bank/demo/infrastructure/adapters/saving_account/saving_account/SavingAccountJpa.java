package com.example.bank.demo.infrastructure.adapters.saving_account.saving_account;

import com.example.bank.demo.domain.model.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingAccountJpa extends JpaRepository<SavingAccount, Long> {
}