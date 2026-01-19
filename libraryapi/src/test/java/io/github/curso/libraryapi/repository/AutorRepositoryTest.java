package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.model.AutorEntity;
import io.github.curso.libraryapi.model.GeneroLivro;
import io.github.curso.libraryapi.model.LivroEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    LivroRepository livroRepository;
    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest(){
        AutorEntity autor = new AutorEntity();
        autor.setNome("Joao");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1959, 2, 21));

        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor Salvo" + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("4b87f1ce-02fc-4fcb-8c80-38a6e9652541");
        Optional<AutorEntity> possivelAutor = autorRepository.findById(id);

        if(possivelAutor.isPresent()){

            AutorEntity autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 30));

            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<AutorEntity> lista = autorRepository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + autorRepository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("4b87f1ce-02fc-4fcb-8c80-38a6e9652541");
        Optional<AutorEntity> possivelAutor = autorRepository.findById(id);

        livroRepository.deleteById(id);
    }

    @Test
    void salvarAutorComLivroTest(){
        AutorEntity autor = new AutorEntity();
        autor.setNome("Antonio");
        autor.setNascionalidade("Americana");
        autor.setDataNascimento(LocalDate.of(1970,8,5));

        LivroEntity livro = new LivroEntity();
        livro.setIsbn("5440-6286");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("tanana");
        livro.setDataPublicacao(LocalDate.of(1920, 3, 2));
        livro.setAutor(autor);

        LivroEntity livro2 = new LivroEntity();
        livro2.setIsbn("7788-7788");
        livro2.setPreco(BigDecimal.valueOf(200));
        livro2.setGenero(GeneroLivro.FICCAO);
        livro2.setTitulo("tututu");
        livro2.setDataPublicacao(LocalDate.of(1980, 3, 2));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void listarLivrosAutor(){
        var id = UUID.fromString("e8835d81-4712-45aa-8ffa-bb580b1f0648");
        var autor = autorRepository.findById(id).get();

        List<LivroEntity> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }

    @Test
    void pesquisaPorTitulo(){
        List<LivroEntity> lista = livroRepository.findByTitulo("tututu");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorISBN(){
        List<LivroEntity> lista = livroRepository.findByIsbn("7788-7788");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTest(){
        var preco = BigDecimal.valueOf(200.00);
        var tituloPesquisa = "tututu";

        List<LivroEntity> lista = livroRepository.findByTituloAndPreco(tituloPesquisa, preco);
        lista.forEach(System.out::println);
    }

}
