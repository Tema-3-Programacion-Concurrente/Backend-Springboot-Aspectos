package org.main_java.backend_springboot_aspectos.model;

import lombok.Getter;
import lombok.Setter;
import org.main_java.backend_springboot_aspectos.domain.Usuario;
import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;


@Getter
@Setter
public class LanzarHechizoRequest {
    private Usuario usuario;
    private Hechizo hechizo;


}
