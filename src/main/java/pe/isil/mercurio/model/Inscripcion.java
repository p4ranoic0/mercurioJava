package pe.isil.mercurio.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinscripcion")
    private Integer id;
    private LocalDateTime fechaInscripcion;

    @ManyToOne
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idcurso", referencedColumnName = "idcurso")
    private Curso curso;
}
