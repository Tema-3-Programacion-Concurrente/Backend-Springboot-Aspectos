package org.main_java.backend_springboot_aspectos.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditoriaDTO {

    private Long id;

    private LocalDateTime fecha;

    private String accion;

    private String descripcion;

    private Long usuarioId;  // Relación con Usuario

    private Long eventoAuditableId;  // Relación con EventoMagico
}

