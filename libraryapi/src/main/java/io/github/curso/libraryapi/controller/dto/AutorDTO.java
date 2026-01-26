package io.github.curso.libraryapi.controller.dto;

import java.time.LocalDate;

public record AutorDTO(String nome,
                       LocalDate dataNascomento,
                       String nascionalidade) {
}
