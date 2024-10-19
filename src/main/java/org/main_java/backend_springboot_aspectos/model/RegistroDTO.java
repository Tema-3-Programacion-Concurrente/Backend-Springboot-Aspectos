package org.main_java.backend_springboot_aspectos.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegistroDTO {

    private Long id;

    private Long usuarioId;

    private String accion;

    private LocalDateTime fecha;

    private String resultado;
}

