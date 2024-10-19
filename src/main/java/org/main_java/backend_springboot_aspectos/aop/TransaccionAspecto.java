package org.main_java.backend_springboot_aspectos.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransaccionAspecto {

    @Before("execution(* org.main_java.backend_springboot_aspectos.service.EventoMagicoService.registrarEventoMagico(..))")
    public void comenzarTransaccion() {
        System.out.println("Iniciando transacción...");
    }

    @After("execution(* org.main_java.backend_springboot_aspectos.service.EventoMagicoService.registrarEventoMagico(..))")
    public void terminarTransaccion() {
        System.out.println("Transacción completada.");
    }

    @Around("execution(* org.main_java.backend_springboot_aspectos.service.*.*(..))")
    public Object gestionarTransaccion(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Gestionando transacción: " + joinPoint.getSignature());
        Object result;
        try {
            result = joinPoint.proceed();
            System.out.println("Transacción exitosa.");
        } catch (Exception e) {
            System.out.println("Error en la transacción: " + e.getMessage());
            throw e;
        }
        return result;
    }
}

