package com.example.libreria.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookCreateDTO {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "El autor es obligatorio")
    private String author;
}
