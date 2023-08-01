package pe.isil.mercurio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.isil.mercurio.model.Curso;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
    Page<Curso> findByTituloContaining(String titulo, Pageable pageable);
    //SELECT * FROM Curso WHERE Titulo like %?% NEXT 5

    //LISTAR LOS PRIMEROS 8 CURSOS ORDENANDO POR FECHA CREACION DESCENDETE
    List<Curso> findTop8ByOrderByFechaCreacionDesc();

}
