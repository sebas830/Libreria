package com.example.libreria.controller;

import com.example.libreria.dto.BookCreateDTO;
import com.example.libreria.dto.BookDTO;
import com.example.libreria.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public BookDTO createBook(@Valid @RequestBody BookCreateDTO dto) {
        return bookService.createBook(dto);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @Valid @RequestBody BookCreateDTO dto) {
        return bookService.updateBook(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}


