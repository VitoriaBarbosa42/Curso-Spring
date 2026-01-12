package io.github.curso.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "livro")
@Data
public class LivroEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_autor")
    private AutorEntity autor;

}

// @Enumerated(EnumType.ORDINAL) - Guarda a posição da String

/* ddl-auto: update - Altera e persiste as alterações
 * ddl-auto: none - Não permite nenhuma alteração no banco. Default.
 * ddl-auto: create-drop - cria e altera todas as operações no banco, mas apaga assim que a aplicação fot derrubada.
 * Ideal para ambientes de teste.
 */

/* BigDecimal é o mais indicado para trabalhar com valor monetário, com valor do tipo Double o precisiondeve ser até 12
 * e não se utiliza o scale
 */