package io.github.curso.libraryapi.controller;

import io.github.curso.libraryapi.controller.dto.AutorDTO;
import io.github.curso.libraryapi.model.AutorEntity;
import io.github.curso.libraryapi.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<AutorEntity> autorOptional = service.obterPorId(idAutor);
        if(autorOptional.isPresent()){
            AutorEntity autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<AutorEntity> autorOptional = service.obterPorId(idAutor);

        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    // required = false | Diz ao spring que o campo pode ser nulo

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<AutorEntity> resultado = service.pesquisar(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream().map(autor -> new AutorDTO(
                autor.getId(),
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()
                )).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable("id") String id, @RequestBody AutorDTO dto
    ) {
        var idAutor = UUID.fromString(id);
        Optional<AutorEntity> autorOpitional = service.obterPorId(idAutor);

        if(autorOpitional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var autor = autorOpitional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();
    }
}

/*
 * Por que usar .buider: o método .build() faz parte do que chamamos de Builder Pattern (Padrão Construtor).
 * Em vez de você instanciar um objeto ResponseEntity usando new e passar um monte de argumentos no construtor
 * (o que pode ficar confuso), o Spring oferece uma interface fluida onde você "monta" a resposta passo a passo.
 *
 * Comparação:
 * Sem Builder: return new ResponseEntity<>(HttpStatus.NOT_FOUND);
 * Com Builder: return ResponseEntity.notFound().build();
 *
 * Você usará o .build() sempre que a sua resposta não tiver um corpo (body).
 *
 * Se você precisar retornar um objeto no corpo da resposta (como um JSON do Autor), você não usa o .build(), mas sim
 * o método .body(objeto). O método .body() já finaliza a construção por você.
 */
