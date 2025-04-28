package com.finora.backend.service;

import com.finora.backend.domain.ExpenseDTO;
import com.finora.backend.domain.dto.Expense;
import com.finora.backend.repository.ExpenseRepository;
import com.finora.backend.utils.ExpenseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public void saveExpense(ExpenseDTO expenseDTO) {
        List<Expense> expenseVariants = ExpenseUtils.createExpenseVariants(
                expenseDTO.getDate(), expenseDTO.getType(), expenseDTO.getName(), expenseDTO.getAmount()
        );
        expenseVariants.forEach(expenseRepository::save);
    }

    public void updateExpense(ExpenseDTO expenseDTO) {
        List<Expense> expenseVariants = ExpenseUtils.createExpenseVariantsWithUuid(expenseDTO.getId(), expenseDTO.getDate(), expenseDTO.getType(), expenseDTO.getName(), expenseDTO.getAmount()
        );
        expenseVariants.forEach(expenseRepository::save);
    }

    public List<ExpenseDTO> getByMonth(String month) {
        List<Expense> expenses = expenseRepository.query("MONTH#" + month, "DATE#");
        return ExpenseUtils.getExpenseDTOs(expenses);
    }

    public List<ExpenseDTO> getByDate(String date) {
        List<Expense> expenses = expenseRepository.query("DATE#" + date);
        return ExpenseUtils.getExpenseDTOs(expenses);
    }

    public List<ExpenseDTO> getByTypeAndMonth(String type, String month) {
        List<Expense> expenses = expenseRepository.query("TYPE#" + type + "#MONTH#" + month);
        return ExpenseUtils.getExpenseDTOs(expenses);
    }

    public List<ExpenseDTO> getByMonthAndWeek(String month, int week) {
        List<Expense> expenses = expenseRepository.query("MONTH#" + month + "#WEEK#" + week, "DATE#");
        return ExpenseUtils.getExpenseDTOs(expenses);
    }

    public void deleteExpense(ExpenseDTO expenseDTO) {
        List<Expense> expenseVariants = ExpenseUtils.createExpenseVariantsWithUuid(expenseDTO.getId(), expenseDTO.getDate(), expenseDTO.getType(), expenseDTO.getName(), expenseDTO.getAmount()
        );
        expenseVariants.forEach(variant -> expenseRepository.delete(variant.getPk(), variant.getSk()));
    }
}