package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.model.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<LivroEntity, UUID> {
}
