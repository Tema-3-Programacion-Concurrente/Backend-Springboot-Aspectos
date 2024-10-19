package org.main_java.backend_springboot_aspectos.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SistemaMagicoDTO {

    private Long id;

    private List<Long> listaHechizosIds;  // IDs de hechizos asociados

    private List<Long> usuariosIds;  // IDs de usuarios asociados
}
