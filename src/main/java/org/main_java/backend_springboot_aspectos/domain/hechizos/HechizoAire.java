package org.main_java.backend_springboot_aspectos.domain.hechizos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;

import java.util.concurrent.CompletableFuture;

@Entity
@DiscriminatorValue("Aire")
public class HechizoAire extends Hechizo {

    @Override
    public void aplicarEfectos() {
        System.out.println("El hechizo de Aire se ejecuta: Una ráfaga de viento impacta al objetivo.");
    }

    @Override
    public CompletableFuture<Void> ejecutar() {
        return super.ejecutar().thenRun(this::aplicarEfectos);
    }
}
