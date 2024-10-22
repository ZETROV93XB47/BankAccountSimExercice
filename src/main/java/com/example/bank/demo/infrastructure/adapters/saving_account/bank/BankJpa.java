package com.example.bank.demo.infrastructure.adapters.saving_account.bank;

import com.example.bank.demo.domain.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankJpa extends JpaRepository<Bank, Long> {

}
