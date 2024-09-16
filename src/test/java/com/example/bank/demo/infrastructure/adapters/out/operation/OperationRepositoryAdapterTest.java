package com.example.bank.demo.infrastructure.adapters.out.operation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OperationRepositoryAdapterTest {

    @Mock
    private OperationJpa operationJpa;

    @InjectMocks
    private OperationRepositoryAdapter operationRepositoryAdapter;


    @Test
    void shouldFindOperationByAccountIdOrderByDateOperationDesc () {

    }

    @Test
    void shouldSaveOperation() {

    }
}