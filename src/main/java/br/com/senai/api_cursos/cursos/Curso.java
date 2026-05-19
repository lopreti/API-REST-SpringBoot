package br.com.senai.api_cursos.cursos;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "cursos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Curso {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private Periodo periodo;
    private Boolean ativo = true;
}