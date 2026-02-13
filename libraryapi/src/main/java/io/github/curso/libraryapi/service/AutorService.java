package io.github.curso.libraryapi.service;

import io.github.curso.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.curso.libraryapi.model.AutorEntity;
import io.github.curso.libraryapi.repository.AutorRepository;
import io.github.curso.libraryapi.repository.LivroRepository;
import io.github.curso.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repositoryAutor;
    private final LivroRepository repositoryLivro;
    private final AutorValidator validator;

//    public AutorService(AutorRepository repository, AutorValidator validator, LivroRepository repositoryLivro){
//        this.repositoryAutor = repository;
//        this.validator = validator;
//        this.repositoryLivro = repositoryLivro;
//    }

    public AutorEntity salvar(AutorEntity autor){
        validator.validar(autor);
        return repositoryAutor.save(autor);
    }

    public void atualizar(AutorEntity autor){

        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessario que o autor ja esteja salvo na base.");
        }
        validator.validar(autor);
        repositoryAutor.save(autor);
    }

    public Optional<AutorEntity> obterPorId(UUID id){
        return repositoryAutor.findById(id);
    }

    public void deletar(AutorEntity autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é permitido excluir autor  que possue livros cadastrado!");
        }
        repositoryAutor.delete(autor);
    }

    public List<AutorEntity> pesquisar(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return repositoryAutor.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if(nacionalidade != null){
            return repositoryAutor.findByNacionalidade(nacionalidade);
        }

        if(nome != null){
            return repositoryAutor.findByNome(nome);
        }

        return repositoryAutor.findAll();
    }

    public boolean possuiLivro(AutorEntity autor){
        return repositoryLivro.existsByAutor(autor);
    }
}
