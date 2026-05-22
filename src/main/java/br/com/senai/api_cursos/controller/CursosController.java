package br.com.senai.api_cursos.controller;

import br.com.senai.api_cursos.cursos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Cursos")
public class CursosController {

    private final CursoRepository repository;

    public CursosController(CursoRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Lista todos os cursos ativos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CursoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum curso encontrado",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<CursoDTO>> listar() {
        var cursos = repository.findByAtivoTrue().stream().map(CursoDTO::new).toList();
        return ResponseEntity.ok(cursos);
    }

    @Operation(summary = "Busca um curso pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CursoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .filter(c -> c.getAtivo())
                .map(c -> ResponseEntity.ok(new CursoDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todos os períodos disponíveis")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Períodos retornados com sucesso",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/periodos")
    public ResponseEntity<List<Periodo>> periodos() {
        return ResponseEntity.ok(Arrays.asList(Periodo.values()));
    }

    @Operation(summary = "Cadastra um novo curso")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Curso criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CursoDTO.class))),
            @ApiResponse(responseCode = "409", description = "Nome já cadastrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Nome já cadastrado")))
    })
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid CursoRequestDTO dto) {
        if (repository.existsByNomeAndAtivoTrue(dto.nome()))
            return ResponseEntity.status(409).body("Nome já cadastrado");
        var curso = new Curso();
        curso.setNome(dto.nome());
        curso.setPeriodo(dto.periodo());
        curso.setAtivo(true);
        return ResponseEntity.status(201).body(new CursoDTO(repository.save(curso)));
    }

    @Operation(summary = "Atualiza nome e/ou período de um curso")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CursoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Nome já cadastrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Nome já cadastrado")))
    })
    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid CursoUpdateDTO dto) {
        return repository.findById(dto.id())
                .filter(c -> c.getAtivo())
                .map(c -> {
                    if (repository.existsByNomeAndAtivoTrueAndIdNot(dto.nome(), dto.id()))
                        return ResponseEntity.status(409).body("Nome já cadastrado");
                    if (dto.nome() != null) c.setNome(dto.nome());
                    if (dto.periodo() != null) c.setPeriodo(dto.periodo());
                    return ResponseEntity.ok((Object) new CursoDTO(repository.save(c)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Exclusão lógica de um curso")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Curso desativado com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return repository.findById(id)
                .filter(c -> c.getAtivo())
                .map(c -> {
                    c.setAtivo(false);
                    repository.save(c);
                    return ResponseEntity.<Void>noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}