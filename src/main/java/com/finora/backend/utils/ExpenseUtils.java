package com.finora.backend.utils;

import com.finora.backend.domain.ExpenseDTO;
import com.finora.backend.domain.enums.Type;
import com.finora.backend.domain.dto.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ExpenseUtils {

    public static List<ExpenseDTO> getExpenseDTOs(List<Expense> expenses) {
        return expenses.stream()
                .map(expense -> {
                    ExpenseDTO dto = new ExpenseDTO();
                    dto.setId(expense.getUuid());
                    dto.setDate(expense.getDate());
                    dto.setName(expense.getName());
                    dto.setType(expense.getType());
                    dto.setAmount(expense.getAmount());
                    return dto;
                })
                .toList();
    }

    public static List<Expense> createExpenseVariantsWithUuid(String uuid, String date, Type type, String name, Double amount) {
        LocalDate parsedDate = LocalDate.parse(date); // assumes format YYYY-MM-DD
        String month = date.substring(0, 7); // YYYY-MM
        String week = month + "-W" + getWeekOfMonth(parsedDate); // e.g. 2025-04-W1

        String dateKey = "DATE#" + date + "#" + uuid;

        return List.of(
                // 1. By month
                Expense.builder()
                        .pk("MONTH#" + month)
                        .sk(dateKey)
                        .uuid(uuid)
                        .date(date)
                        .month(month)
                        .week(week)
                        .type(type)
                        .name(name)
                        .amount(amount)
                        .build(),

                // 2. By date
                Expense.builder()
                        .pk("DATE#" + date)
                        .sk("EXPENSE#" + type + "#" + uuid)
                        .uuid(uuid)
                        .date(date)
                        .month(month)
                        .week(week)
                        .type(type)
                        .name(name)
                        .amount(amount)
                        .build(),

                // 3. By type and month
                Expense.builder()
                        .pk("TYPE#" + type + "#MONTH#" + month)
                        .sk(dateKey)
                        .uuid(uuid)
                        .date(date)
                        .month(month)
                        .week(week)
                        .type(type)
                        .name(name)
                        .amount(amount)
                        .build(),

                // 4. By month and week
                Expense.builder()
                        .pk("MONTH#" + month + "#WEEK#" + getWeekOfMonth(parsedDate))
                        .sk(dateKey)
                        .uuid(uuid)
                        .date(date)
                        .month(month)
                        .week(week)
                        .type(type)
                        .name(name)
                        .amount(amount)
                        .build()
        );
    }

    public static List<Expense> createExpenseVariants(String date, Type type, String name, Double amount) {
        String uuid = UUID.randomUUID().toString();
        return createExpenseVariantsWithUuid(uuid, date, type, name, amount);
    }

    private static int getWeekOfMonth(LocalDate date) {
        return (date.getDayOfMonth() - 1) / 7 + 1;
    }
}