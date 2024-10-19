package org.main_java.backend_springboot_aspectos.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventoMagicoDTO {

    private Long id;

    private String descripcion;

    private LocalDateTime fecha;

    private String tipoEvento;

    private Long lanzadorId;  // Relación con Usuario

    private Long hechizoLanzadoId;  // Relación con Hechizo
}
