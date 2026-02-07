package io.github.curso.libraryapi.service;

import io.github.curso.libraryapi.model.AutorEntity;
import io.github.curso.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository){
        this.repository = repository;
    }

    public AutorEntity salvar(AutorEntity autor){
        return repository.save(autor);
    }

    public Optional<AutorEntity> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(AutorEntity autor) {
        repository.delete(autor);
    }

    public List<AutorEntity> pesquisar(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if(nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }

        if(nome != null){
            return repository.findByNome(nome);
        }

        return repository.findAll();
    }
}
