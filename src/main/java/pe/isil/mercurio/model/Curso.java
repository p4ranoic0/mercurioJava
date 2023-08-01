package pe.isil.mercurio.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcurso")
    private Integer id;

    @NotBlank
    private String titulo;

    @Size(max = 500)
    private String descripcion;

    private String rutaImagen;

    @NotNull
    @Min(1)
    @Max(1000)
    private Float precio;

    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_act")
    private LocalDateTime fechaActualizacion;

    @Transient
    private MultipartFile imagen;

    @PrePersist
    void prePersist() { //cuando insertamos el valor de fecha de creacion es la fecha del sistema
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() { //cuando actualizamos el valor de fecha de creacion es la fecha del sistema
        fechaActualizacion = LocalDateTime.now();
    }
}
