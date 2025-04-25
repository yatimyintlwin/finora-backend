package com.finora.backend.domain;

import lombok.Data;

@Data
public class ExpenseDTO {
    private String id;        // UUID
    private String date;      // YYYY-MM-DD
    private String name;
    private String type;
    private double amount;
}