package org.main_java.backend_springboot_aspectos.domain.hechizos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.concurrent.CompletableFuture;

@Entity
@DiscriminatorValue("Agua")
public class HechizoAgua extends Hechizo {

    @Override
    public void aplicarEfectos() {
        System.out.println("El hechizo de Agua se ejecuta: Un torrente de agua fluye hacia el objetivo.");
    }

    @Override
    public CompletableFuture<Void> ejecutar() {
        return super.ejecutar().thenRun(this::aplicarEfectos);
    }
}
