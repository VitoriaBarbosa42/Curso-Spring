package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.model.AutorEntity;
import io.github.curso.libraryapi.model.GeneroLivro;
import io.github.curso.libraryapi.model.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<LivroEntity, UUID> {

    // Query Method

    // select * from livro where id_autor =  id
    List<LivroEntity> findByAutor(AutorEntity autor);

    // select * from livro where titulo =  titulo
    List<LivroEntity> findByTitulo(String titulo);

    // select * from livro where isbn =  isbn
    List<LivroEntity> findByIsbn(String isbn);

    // select * from livro where titulo = ? and preco = ?
    List<LivroEntity> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = ? or isbn = ?
    List<LivroEntity> findByTituloOrIsbn(String titulo, String isbn);

    // // select * from livro where data_piblicacao between ? and ?
    List<LivroEntity> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    //JPQL -> Referencia as entidades e as propriedades
    // select l.* from livro as l order by l.titulo
    @Query("select l from LivroEntity as l order by l.titulo")
    List<LivroEntity> listarTodos();

    @Query("select a from LivroEntity as l join l.autor as a")
    List<AutorEntity> listarAutoresDosLivros();

    @Query("""
        select l.genero
        from LivroEntity l
        join l.autor a
        where a.nacionalidade = 'Brasileira'
    """)
    List<String> listarGenerosAutoresBarsileiros();

    @Query("select l from LivroEntity as l where l.genero = :genero order by :paramOrdenacao")
    List<LivroEntity> findByGenero(
            @Param("genero")GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomeParametro
    );

    // positional parameters
    @Query("select l from LivroEntity as l where l.genero = ?1 order by ?2")
    List<LivroEntity> findByGeneroPositionalParameters(
            GeneroLivro generoLivro,
            String nomeParametro
    );

    @Modifying
    @Transactional
    @Query(" delete from LivroEntity where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query(" update LivroEntity set preco = ?1 where titulo = ?2")
    void updatePreco(BigDecimal preco, String titulo);


}
