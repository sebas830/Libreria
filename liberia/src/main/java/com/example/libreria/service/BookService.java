package com.example.libreria.service;

import com.example.libreria.dto.BookCreateDTO;
import com.example.libreria.dto.BookDTO;
import com.example.libreria.exception.BookNotFoundException;
import com.example.libreria.model.Book;
import com.example.libreria.repository.BookRepository;
import jakarta.validation.Valid;
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

    // Obtener todos los libros
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener libro por ID
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado"));
        return convertToDTO(book);
    }

    // Crear libro
    public BookDTO createBook(@Valid BookCreateDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("El título del libro es obligatorio");
        }
        if (dto.getAuthor() == null || dto.getAuthor().isBlank()) {
            throw new IllegalArgumentException("El autor del libro es obligatorio");
        }

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setAvailable(true);

        return convertToDTO(bookRepository.save(book));
    }

    // Actualizar libro
    public BookDTO updateBook(Long id, @Valid BookCreateDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado"));

        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("El título del libro es obligatorio");
        }
        if (dto.getAuthor() == null || dto.getAuthor().isBlank()) {
            throw new IllegalArgumentException("El autor del libro es obligatorio");
        }

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());

        return convertToDTO(bookRepository.save(book));
    }

    // Eliminar libro
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Libro no encontrado");
        }
        bookRepository.deleteById(id);
    }
}




