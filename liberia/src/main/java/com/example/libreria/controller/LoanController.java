package com.example.libreria.controller;

import com.example.libreria.dto.LoanDTO;
import com.example.libreria.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<LoanDTO> getAllLoans() {
        return loanService.getAllLoans();
    }

    @PostMapping
    public LoanDTO createLoan(@RequestParam Long bookId, @RequestParam Long userId) {
        return loanService.createLoan(bookId, userId);
    }

    @PutMapping("/{id}/return")
    public LoanDTO returnLoan(@PathVariable Long id) {
        return loanService.returnLoan(id);
    }

    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }
}


