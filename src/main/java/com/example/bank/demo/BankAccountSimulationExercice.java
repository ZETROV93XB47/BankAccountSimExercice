package com.example.bank.demo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BankAccountSimulationExercice implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountSimulationExercice.class);

	public static void main(String[] args) {
		SpringApplication.run(BankAccountSimulationExercice.class, args);
	}

	@Override
	public void run(String... args) {
		LOGGER.info("Application started with success! 😁🔥🔥🔥");
	}
}
