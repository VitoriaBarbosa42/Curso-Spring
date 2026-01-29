package io.github.curso.libraryapi.controller.dto;

import io.github.curso.libraryapi.model.AutorEntity;

import java.time.LocalDate;

public record AutorDTO(String nome,
                       LocalDate dataNascomento,
                       String nascionalidade) {

    public AutorEntity mapearParaAutor(){
        AutorEntity autor = new AutorEntity();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascomento);
        autor.setNascionalidade(this.nascionalidade);
        return autor;
    }

}
