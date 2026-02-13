package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.model.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<AutorEntity, UUID> {
    List<Object> findAllById(UUID id);

    List<AutorEntity> findByNome(String nome);
    List<AutorEntity> findByNacionalidade(String nacionalidade);
    List<AutorEntity> findByNomeAndNacionalidade(String nome, String nacionalidade);

    Optional<AutorEntity> findByNomeAndDataNascimentoAndNacionalidade(
        String nome, LocalDate dataNascimento, String nacionalidade
    );
}
