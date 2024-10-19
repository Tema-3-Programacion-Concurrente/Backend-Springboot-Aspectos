package org.main_java.backend_springboot_aspectos.domain.hechizos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.concurrent.CompletableFuture;

@Entity
@DiscriminatorValue("Roca")
public class HechizoRoca extends Hechizo {

    @Override
    public void aplicarEfectos() {
        System.out.println("El hechizo de Roca se ejecuta: Un terremoto sacude el terreno.");
    }

    @Override
    public CompletableFuture<Void> ejecutar() {
        return super.ejecutar().thenRun(this::aplicarEfectos);
    }
}

