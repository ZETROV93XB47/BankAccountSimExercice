package com.example.bank.demo.infrastructure.adapters.persistence.bank_account;

import com.example.bank.demo.domain.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountJpa extends JpaRepository<BankAccount, Long> {

}
