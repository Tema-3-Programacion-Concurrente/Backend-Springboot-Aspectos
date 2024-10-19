package org.main_java.backend_springboot_aspectos.domain.hechizos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.concurrent.CompletableFuture;

@Entity
@DiscriminatorValue("Fuego")
public class HechizoFuego extends Hechizo {

    @Override
    public void aplicarEfectos() {
        System.out.println("El hechizo de Fuego se ejecuta: Llamas intensas envuelven al objetivo.");
    }

    @Override
    public CompletableFuture<Void> ejecutar() {
        return super.ejecutar().thenRun(this::aplicarEfectos);
    }
}

