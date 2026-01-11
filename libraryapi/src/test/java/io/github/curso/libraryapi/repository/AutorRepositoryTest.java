package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.model.AutorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;
    @Autowired
    private AutorRepository autorRepository;

    @Test
    public void salvarTest(){
        AutorEntity autor = new AutorEntity();
        autor.setNome("Maria");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 5, 22));

        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor Salvo" + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("4b87f1ce-02fc-4fcb-8c80-38a6e9652541");
        Optional<AutorEntity> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){

            AutorEntity autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 30));

            repository.save(autorEncontrado);
        }
    }
}
