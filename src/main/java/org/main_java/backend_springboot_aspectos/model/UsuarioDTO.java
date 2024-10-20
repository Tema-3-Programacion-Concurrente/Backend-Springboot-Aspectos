package org.main_java.backend_springboot_aspectos.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;


@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Size(max = 255)
    private String apellido1;

    @NotNull
    @Size(max = 255)
    private String apellido2;

    @NotNull
    @Size(max = 255)
    private String correo;

    @NotNull
    private Integer telefono;

    @Size(max = 255)
    private String direccion;

    private Long rolId;

    private Long credencialesId;

    private OffsetDateTime dateCreated;

    @NotNull
    private int poder;
}
