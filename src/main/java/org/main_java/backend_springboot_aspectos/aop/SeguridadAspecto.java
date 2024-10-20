package org.main_java.backend_springboot_aspectos.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.main_java.backend_springboot_aspectos.domain.Usuario;
import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;
import org.main_java.backend_springboot_aspectos.service.SeguridadService;
import org.main_java.backend_springboot_aspectos.service.UsuarioService;
import org.main_java.backend_springboot_aspectos.service.HechizoService;
import org.main_java.backend_springboot_aspectos.service.factory.HechizoFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SeguridadAspecto {

    @Autowired
    private SeguridadService seguridadService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private HechizoFactoryService hechizoFactoryService;

    /**
     * Aspecto que verifica si un usuario tiene permisos para lanzar un hechizo antes de la ejecución.
     * Aplica la verificación de seguridad antes de que se ejecute cualquier hechizo.
     *
     * @param usuario El ID del usuario que intenta lanzar el hechizo.
     * @param hechizo El tipo de hechizo que se intenta lanzar.
     */
    @Before("execution(* org.main_java.backend_springboot_aspectos.service.HechizoService.lanzarHechizo(..)) && args(usuario, hechizo)")
    public void verificarPermisoLanzarHechizo(Usuario usuario, Hechizo hechizo) {
        // Obtener el nombre o tipo del hechizo directamente del objeto
        String tipoHechizo = hechizo.getNombre(); // o un metodo que devuelva el tipo

        // Verificar si el usuario tiene permiso para lanzar el hechizo
        boolean autorizado = seguridadService.verificarAcceso(usuario, hechizo);

        // Si el usuario no tiene permiso, lanzamos una excepción
        if (!autorizado) {
            throw new SecurityException("El usuario " + usuario.getNombre() + " no tiene permiso para lanzar el hechizo " + tipoHechizo);
        }
    }
}
