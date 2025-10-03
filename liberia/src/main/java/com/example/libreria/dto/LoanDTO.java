package com.example.libreria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class LoanDTO {
    private Long id;
    private Long bookId;
    private Long userId;
    private LocalDate loanDate;
    private LocalDate returnDate;
}
