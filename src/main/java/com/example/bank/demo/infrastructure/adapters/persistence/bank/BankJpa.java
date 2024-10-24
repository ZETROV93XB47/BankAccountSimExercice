package com.example.bank.demo.infrastructure.adapters.persistence.bank;

import com.example.bank.demo.domain.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankJpa extends JpaRepository<Bank, Long> {

}
