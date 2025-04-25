package com.finora.backend.controller;

import com.finora.backend.domain.ExpenseDTO;
import com.finora.backend.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Void> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        expenseService.saveExpense(expenseDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateExpense(@RequestBody ExpenseDTO expenseDTO) {
        expenseService.updateExpense(expenseDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/month/{month}")
    public ResponseEntity<List<ExpenseDTO>> getByMonth(@PathVariable String month) {
        return ResponseEntity.ok(expenseService.getByMonth(month));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<ExpenseDTO>> getByDate(@PathVariable String date) {
        return ResponseEntity.ok(expenseService.getByDate(date));
    }

    @GetMapping("/type/{type}/month/{month}")
    public ResponseEntity<List<ExpenseDTO>> getByTypeAndMonth(@PathVariable String type, @PathVariable String month) {
        return ResponseEntity.ok(expenseService.getByTypeAndMonth(type, month));
    }

    @GetMapping("/month/{month}/week/{week}")
    public ResponseEntity<List<ExpenseDTO>> getByMonthAndWeek(@PathVariable String month, @PathVariable int week) {
        return ResponseEntity.ok(expenseService.getByMonthAndWeek(month, week));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteExpense(@RequestBody ExpenseDTO expenseDTO) {
        expenseService.deleteExpense(expenseDTO);
        return ResponseEntity.ok().build();
    }
}