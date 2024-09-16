package com.example.bank.demo.infrastructure.utils;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class BaseIntegTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void cleanUp() {
        jdbcTemplate.execute(" SET FOREIGN_KEY_CHECKS = 0;");
        jdbcTemplate.execute(" DELETE FROM operation;");
        jdbcTemplate.execute(" DELETE FROM bank_account;");
        jdbcTemplate.execute(" DELETE FROM saving_account;");
        jdbcTemplate.execute(" DELETE FROM bank;");
        jdbcTemplate.execute(" SET FOREIGN_KEY_CHECKS = 1;");
    }

    @Container
    private static final MySQLContainer<?> database = new MySQLContainer<>("mysql:8.1.0")
            .withDatabaseName("testDatabase")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("sql/databaseInitFile.sql")
            .withReuse(false);

    @DynamicPropertySource
    static void datasourceConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.password", database::getPassword);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.driver-class-name", database::getDriverClassName);
    }
}
