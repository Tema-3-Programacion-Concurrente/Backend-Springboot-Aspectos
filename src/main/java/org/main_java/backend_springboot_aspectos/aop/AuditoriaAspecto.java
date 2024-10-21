package org.main_java.backend_springboot_aspectos.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.main_java.backend_springboot_aspectos.domain.EventoMagico;
import org.main_java.backend_springboot_aspectos.service.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditoriaAspecto {


    @Before("execution(* org.main_java.backend_springboot_aspectos.service.AuditoriaService.auditarEvento(..)) && args(eventoMagico)")
    public void antesDeAuditarEvento(JoinPoint joinPoint, EventoMagico eventoMagico) {
        // Realizamos una acción antes de auditar el evento
        System.out.println("Preparando para auditar el evento: " + eventoMagico.getDescripcion());

        // Podrías acceder a más detalles del evento
        System.out.println("Evento mágico lanzado por: " + eventoMagico.getLanzador().getNombre());

        // Acceder al nombre del metodo que será interceptado
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Interceptando el método: " + methodName);
    }
}

