package com.example.bank.demo.infrastructure.adapters.persistence.bank;

import com.example.bank.demo.infrastructure.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankJpa extends JpaRepository<BankEntity, Long> {
}
