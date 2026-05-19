package br.com.senai.api_cursos.cursos;

import io.swagger.v3.oas.annotations.media.Schema;

public record CursoUpdateDTO(
        @Schema(example = "1") Long id,
        @Schema(example = "Desenvolvimento de Sistemas") String nome,
        @Schema(example = "NOTURNO") Periodo periodo
) {}