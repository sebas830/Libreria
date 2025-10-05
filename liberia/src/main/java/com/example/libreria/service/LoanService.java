package com.example.libreria.service;

import com.example.libreria.dto.LoanDTO;
import com.example.libreria.exception.BookNotFoundException;
import com.example.libreria.exception.LoanNotFoundException;
import com.example.libreria.exception.UserNotFoundException;
import com.example.libreria.model.Book;
import com.example.libreria.model.Loan;
import com.example.libreria.model.User;
import com.example.libreria.repository.BookRepository;
import com.example.libreria.repository.LoanRepository;
import com.example.libreria.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    // Convertir entidad a DTO
    private LoanDTO convertToDTO(Loan loan) {
        return new LoanDTO(
                loan.getId(),
                loan.getBook().getId(),
                loan.getUser().getId(),
                loan.getLoanDate(),
                loan.getReturnDate()
        );
    }

    // Listar todos los préstamos
    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Crear préstamo
    public LoanDTO createLoan(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if (!book.isAvailable()) {
            throw new IllegalStateException("El libro no está disponible");
        }

        // Marcar libro como prestado
        book.setAvailable(false);
        bookRepository.save(book);

        // Crear el préstamo
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(LocalDate.now());

        return convertToDTO(loanRepository.save(loan));
    }

    // Devolver préstamo
    public LoanDTO returnLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Préstamo no encontrado"));

        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        loan.setReturnDate(LocalDate.now());
        return convertToDTO(loanRepository.save(loan));
    }

    // Eliminar préstamo
    public void deleteLoan(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new LoanNotFoundException("Préstamo no encontrado");
        }
        loanRepository.deleteById(id);
    }
}



