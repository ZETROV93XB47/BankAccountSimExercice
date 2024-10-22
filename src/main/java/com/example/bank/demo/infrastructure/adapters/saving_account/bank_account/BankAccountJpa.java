package com.example.bank.demo.infrastructure.adapters.saving_account.bank_account;

import com.example.bank.demo.domain.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountJpa extends JpaRepository<BankAccount, Long> {

}
