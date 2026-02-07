package io.github.curso.libraryapi.controller.dto;

import io.github.curso.libraryapi.model.AutorEntity;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        String nome,
        LocalDate dataNascimento,
        String nacionalidade
) {

    public AutorEntity mapearParaAutor(){
        AutorEntity autor = new AutorEntity();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }

}
