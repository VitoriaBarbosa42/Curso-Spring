package io.github.curso.libraryapi.controller;

import io.github.curso.libraryapi.controller.dto.AutorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("autores")
// http://localhost:8080/autores
public class AutorController {

    // ResponseEntity representa todos os dados que você pode retornar em uma resposta
    // A estrutura básica segue o padrão: ResponseEntity<T>(corpo, headers, status)
    @PostMapping
    public ResponseEntity salvar(@RequestBody AutorDTO autor) {
        return new ResponseEntity("Autor Salvo com sucesso! " + autor, HttpStatus.CREATED);
    }

}
