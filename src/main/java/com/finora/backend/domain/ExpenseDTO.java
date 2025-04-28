package com.finora.backend.domain;

import com.finora.backend.domain.enums.Type;
import lombok.Data;

@Data
public class ExpenseDTO {
    private String id;        // UUID
    private String date;      // YYYY-MM-DD
    private String name;
    private Type type;
    private double amount;
}