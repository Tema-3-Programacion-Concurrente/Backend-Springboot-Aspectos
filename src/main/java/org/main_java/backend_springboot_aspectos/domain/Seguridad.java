package org.main_java.backend_springboot_aspectos.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Seguridad")
@Getter
@Setter
public class Seguridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean autorizado;

    @Column(nullable = false)
    private LocalDateTime fechaAutorizacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;  // Relación con Usuario

    @Column(nullable = false)
    private String accion;
}
