package com.finora.backend.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Expense {
    private String id;
    private LocalDateTime date;
    private String name;
    private String type;
    private double amount;
}
