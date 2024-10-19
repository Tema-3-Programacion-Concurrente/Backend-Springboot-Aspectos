package org.main_java.backend_springboot_aspectos.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;

import java.time.LocalDateTime;

@Entity
@Table(name = "EventosMagicos")
@Getter
@Setter
public class EventoMagico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String tipoEvento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario lanzador;  // Relación con Usuario

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hechizo_id", nullable = false)
    private Hechizo hechizoLanzado;  // Relación con Hechizo
}
