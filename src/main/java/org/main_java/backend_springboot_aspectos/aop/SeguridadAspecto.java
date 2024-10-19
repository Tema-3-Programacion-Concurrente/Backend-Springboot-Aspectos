package org.main_java.backend_springboot_aspectos.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;
import org.main_java.backend_springboot_aspectos.service.SeguridadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SeguridadAspecto {

    @Autowired
    private SeguridadService seguridadService;

    @Before("execution(* org.main_java.backend_springboot_aspectos.service.HechizoService.lanzarHechizo(..)) && args(usuarioId, tipoHechizo)")
    public void verificarPermisoLanzarHechizo(Long usuarioId, String tipoHechizo) {
        // Verificar si el usuario tiene permisos
        boolean autorizado = seguridadService.verificarAcceso(usuarioId, tipoHechizo);
        if (!autorizado) {
            throw new SecurityException("Usuario no autorizado para lanzar este hechizo");
        }
    }
}


