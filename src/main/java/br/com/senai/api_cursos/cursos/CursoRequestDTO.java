package br.com.senai.api_cursos.cursos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CursoRequestDTO(
        @NotBlank @Size(min = 3) String nome,
        @NotNull Periodo periodo
) {}