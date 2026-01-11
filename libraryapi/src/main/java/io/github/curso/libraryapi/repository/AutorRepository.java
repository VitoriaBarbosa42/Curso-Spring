package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.model.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<AutorEntity, UUID> {
}
