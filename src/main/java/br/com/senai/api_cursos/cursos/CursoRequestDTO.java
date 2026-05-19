package br.com.senai.api_cursos.cursos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record CursoRequestDTO(
        @Schema(example = "Desenvolvimento de Sistemas")
        @NotBlank @Size(min = 3) String nome,

        @Schema(example = "MATUTINO")
        @NotNull Periodo periodo
) {}