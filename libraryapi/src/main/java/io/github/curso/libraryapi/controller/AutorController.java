package io.github.curso.libraryapi.controller;

import io.github.curso.libraryapi.controller.dto.AutorDTO;
import io.github.curso.libraryapi.model.AutorEntity;
import io.github.curso.libraryapi.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("autores")
// http://localhost:8080/autores
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service){
        this.service = service;
    }

    // ResponseEntity representa todos os dados que você pode retornar em uma resposta
    // A estrutura básica segue o padrão: ResponseEntity<T>(corpo, headers, status)
    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor) {
        AutorEntity autorEntidade = autor.mapearParaAutor();
        service.salvar(autorEntidade);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
