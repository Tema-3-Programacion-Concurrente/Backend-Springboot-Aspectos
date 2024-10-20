package org.main_java.backend_springboot_aspectos.domain.hechizos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
        import lombok.Getter;
import lombok.Setter;
import org.main_java.backend_springboot_aspectos.domain.Usuario;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Entity
@Table(name = "Hechizos")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_hechizo", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipoHechizo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HechizoFuego.class, name = "fuego"),
        @JsonSubTypes.Type(value = HechizoAgua.class, name = "agua"),
        @JsonSubTypes.Type(value = HechizoAire.class, name = "aire"),
        @JsonSubTypes.Type(value = HechizoRoca.class, name = "roca")
})
public abstract class Hechizo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int poder;

    // Pool de hilos para manejar la concurrencia en la ejecución de hechizos
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * Verifica si el usuario tiene permiso para ejecutar el hechizo.
     * Este metodo es concurrente y puede ser llamado por múltiples hilos al mismo tiempo.
     *
     * @param usuario El usuario que intenta ejecutar el hechizo.
     * @return true si el usuario tiene permiso para ejecutar el hechizo, false en caso contrario.
     */
    public synchronized boolean esPermitido(Usuario usuario) {
        return usuario.getPoder() >= this.poder; // Verifica si el poder del usuario es suficiente
    }

    /**
     * Ejecuta el hechizo de manera concurrente y devuelve un CompletableFuture.
     * Este metodo puede ser sobrescrito por las subclases para definir la lógica de cada hechizo.
     *
     * @return Un CompletableFuture que representa la ejecución asincrónica del hechizo.
     */
    public CompletableFuture<Void> ejecutar() {
        return CompletableFuture.runAsync(() -> {
            synchronized (this) {
                System.out.println("Ejecutando el hechizo: " + nombre + " con poder: " + poder);
                try {
                    // Simular la demora en la ejecución del hechizo
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, executorService);
    }

    // Metodo abstracto que puede ser implementado por las subclases para personalizar la ejecución
    public abstract void aplicarEfectos();
}


