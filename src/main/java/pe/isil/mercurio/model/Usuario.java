package pe.isil.mercurio.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer id;

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    @Column(name = "nom_completo")
    private String nombreCompleto;

    @Email
    private String email;


    private String password;

    @NotBlank(message = "la contraseña no puede estar en blanco")
    @Transient
    private String password1;

    @NotBlank(message = "la contraseña no puede estar en blanco")
    @Transient
    private String password2;


    public enum Rol {
        ADMIN,
        ESTUDIANTE
    }

    @Enumerated(EnumType.STRING)
    private Rol rol;


    @PrePersist
    @PreUpdate
    void asignarNombreCompleto() {
        nombreCompleto = nombres + " " + apellidos;
    }
}
