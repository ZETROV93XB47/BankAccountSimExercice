package com.example.bank.demo.infrastructure.utils;

import lombok.RequiredArgsConstructor;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
@Transactional(propagation = REQUIRES_NEW)
public class TransactionalTestingService {

    public <T> T inNewTransaction(ThrowingSupplier<T> supplier) throws Throwable {
        return supplier.get();
    }

    public void inNewTransaction(ThrowingRunnable runnable) throws Throwable {
        runnable.run();
    }
}
