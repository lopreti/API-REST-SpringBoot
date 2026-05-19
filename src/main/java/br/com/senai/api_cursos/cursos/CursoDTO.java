package br.com.senai.api_cursos.cursos;

public record CursoDTO(Long id, String nome, Periodo periodo, Boolean ativo) {
    public CursoDTO(Curso c) {
        this(c.getId(), c.getNome(), c.getPeriodo(), c.getAtivo());
    }
}