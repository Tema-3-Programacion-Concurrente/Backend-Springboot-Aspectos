package org.main_java.backend_springboot_aspectos.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.main_java.backend_springboot_aspectos.domain.Usuario;
import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;
import org.main_java.backend_springboot_aspectos.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RegistroAspecto {

    @Autowired
    private RegistroService registroService;

    @AfterReturning("execution(* org.main_java.backend_springboot_aspectos.service.HechizoService.lanzarHechizo(..)) && args(usuario, hechizo)")
    public void registrarAccion(Usuario usuario, Hechizo hechizo) {
        registroService.registrarAccion(usuario, "Lanzar Hechizo", "Éxito");
        System.out.println("Hechizo lanzado: " + hechizo.getNombre() + " ha sido registrado con exito su accion[!] [ASPECTO]");
    }
}

