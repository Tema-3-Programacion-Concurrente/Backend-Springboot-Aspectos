package org.main_java.backend_springboot_aspectos.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SeguridadDTO {

    private Long id;

    private boolean autorizado;

    private LocalDateTime fechaAutorizacion;

    private Long usuarioId;

    private String accion;
}

