package io.github.curso.libraryapi.service;

import io.github.curso.libraryapi.model.AutorEntity;
import io.github.curso.libraryapi.model.GeneroLivro;
import io.github.curso.libraryapi.model.LivroEntity;
import io.github.curso.libraryapi.repository.AutorRepository;
import io.github.curso.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    /// livro (titulo,..., nome_arquivo) -> id.png
    @Transactional
    public void salvarLivroComFoto(){
        // salva o livro
        // repository.save(livro);

        // pega o id do livro = livro.getId();
        // var id = livro.getId();

        // salvar foto do livro -> bucket na nuvem
        // bucketService.salvar(livro.getFoto(), id + ".png");

        // atualizar o nome arquivo que foi salvo
        // livro.setNomeArquivoFoto(id + ".png");
    }

    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository
                .findById(UUID.fromString("daed83b3-65fd-49eb-9400-cbc0af13059d"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024,6,1));
    }


    @Transactional
    public void executar(){

        //salva o autor
        AutorEntity autor = new AutorEntity();
        autor.setNome("Carlos");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1985, 4, 30));

        autorRepository.save(autor);

        //salva o livro
        LivroEntity livro = new LivroEntity();
        livro.setIsbn("2995-1541");
        livro.setPreco(BigDecimal.valueOf(35,99));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Livro da Francisca");
        livro.setDataPublicacao(LocalDate.of(2001, 3, 2));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if(autor.getNome().equals("Carlos")){
            throw new RuntimeException("Rollback");
        }
    }

}
