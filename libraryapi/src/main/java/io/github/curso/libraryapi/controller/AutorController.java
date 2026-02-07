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
}
