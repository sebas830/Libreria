package com.example.libreria.service;

import com.example.libreria.dto.BookCreateDTO;
import com.example.libreria.dto.BookDTO;
import com.example.libreria.model.Book;
import com.example.libreria.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private BookDTO convertToDTO(Book book) {
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.isAvailable());
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return convertToDTO(book);
    }

    public BookDTO createBook(BookCreateDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setAvailable(true);
        return convertToDTO(bookRepository.save(book));
    }

    public BookDTO updateBook(Long id, BookCreateDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());

        return convertToDTO(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}



