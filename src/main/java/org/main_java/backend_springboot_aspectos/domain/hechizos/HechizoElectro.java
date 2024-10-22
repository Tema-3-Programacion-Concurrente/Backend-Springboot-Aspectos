package org.main_java.backend_springboot_aspectos.domain.hechizos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.concurrent.CompletableFuture;

@Entity
@DiscriminatorValue("Electro")
public class HechizoElectro extends Hechizo {



    @Override
    public void aplicarEfectos() {
        System.out.println("El hechizo de Electro se ejecuta: Un rayo eléctrico impacta en el objetivo.");
    }

    @Override
    public CompletableFuture<Void> ejecutar() {
        return super.ejecutar().thenRun(this::aplicarEfectos);
    }
}
