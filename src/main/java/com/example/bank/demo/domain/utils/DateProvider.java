package com.example.bank.demo.domain.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@NoArgsConstructor
public class DateProvider {
    public LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }
}
