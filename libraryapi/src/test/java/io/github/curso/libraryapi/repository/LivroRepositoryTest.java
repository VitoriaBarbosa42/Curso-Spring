package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.model.AutorEntity;
import io.github.curso.libraryapi.model.GeneroLivro;
import io.github.curso.libraryapi.model.LivroEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;


    @Test
    void salvarTest(){
        LivroEntity livro = new LivroEntity();
        livro.setIsbn("1234-5678");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        AutorEntity autor = autorRepository.findById(UUID.fromString("c6325ce9-5fae-41c7-81db-7ee9b22192a8"))
                .orElse(null);
        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest(){
        LivroEntity livro = new LivroEntity();
        livro.setIsbn("9876-5432");
        livro.setPreco(BigDecimal.valueOf(20,99));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Asss");
        livro.setDataPublicacao(LocalDate.of(2000, 1, 2));

        AutorEntity autor = new AutorEntity();
        autor.setNome("Tereza");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1980, 2, 3));

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarAutorELivroTest(){
        LivroEntity livro = new LivroEntity();
        livro.setIsbn("2065-1541");
        livro.setPreco(BigDecimal.valueOf(35,99));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("mais um");
        livro.setDataPublicacao(LocalDate.of(2001, 3, 2));

        AutorEntity autor = new AutorEntity();
        autor.setNome("Carlos");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1985, 4, 30));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro(){
        UUID id = UUID.fromString("f7fed26d-f0de-4ec5-93d9-9f6e436914e7");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("38be42e3-bac8-4433-8668-b1a34ae7a8c8");
        AutorEntity carlos = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(carlos);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("f7fed26d-f0de-4ec5-93d9-9f6e436914e7");
        repository.deleteById(id);
    }

    @Test
    void deletarCascade(){
        UUID id = UUID.fromString("6c7bfb2b-e616-419b-b2f5-b46eb72cb4a9");
        repository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTeste(){
        UUID id = UUID.fromString("c8246554-a863-44b5-b0e5-a7277ae9b2b7");
        LivroEntity livro = repository.findById(id).orElse(null);
        System.out.print("Livro:");
        System.out.println(livro.getTitulo());

        System.out.print("Autor:");
        System.out.println(livro.getAutor().getNome());

    }
}