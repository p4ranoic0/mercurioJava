package pe.isil.mercurio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.isil.mercurio.model.Curso;
import pe.isil.mercurio.repository.CursoRepository;

import java.util.List;

@Controller
public class CursoController {
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping("")
    String index(Model model) {
        //Listar los 8 cursos
        List<Curso> ultimosCursos = cursoRepository.findTop8ByOrderByFechaCreacionDesc();
        model.addAttribute("ultimosCursos", ultimosCursos);
        return "index";
    }

    @GetMapping("/cursos")
    String getCursos(Model model,
                     @PageableDefault(size = 8, sort = "titulo") Pageable pageable) {
        Page<Curso> cursos = cursoRepository.findAll(pageable);
        model.addAttribute("cursos", cursos);
        return "lista-cursos";
    }

    @GetMapping("/cursos/{id}")
    String getCurso(@PathVariable Integer id, Model model) {
        Curso curso = cursoRepository.getById(id);
        model.addAttribute("curso", curso);
        return "detalles-curso";
    }

}
