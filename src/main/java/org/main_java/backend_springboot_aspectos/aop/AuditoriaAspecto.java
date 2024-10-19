package org.main_java.backend_springboot_aspectos.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.main_java.backend_springboot_aspectos.domain.EventoMagico;
import org.main_java.backend_springboot_aspectos.service.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditoriaAspecto {

    @Autowired
    private AuditoriaService auditoriaService;

    @AfterReturning(pointcut = "execution(* org.main_java.backend_springboot_aspectos.service.HechizoService.lanzarHechizo(..))", returning = "eventoMagico")
    public void registrarAuditoria(EventoMagico eventoMagico) {
        auditoriaService.auditarEvento(eventoMagico);
    }
}

