package br.com.senai.api_cursos.cursos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByAtivoTrue();

    boolean existsByNomeAndAtivoTrue(String nome);

    boolean existsByNomeAndAtivoTrueAndIdNot(String nome, Long id);
}