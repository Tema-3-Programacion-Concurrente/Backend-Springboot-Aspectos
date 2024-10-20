package org.main_java.backend_springboot_aspectos.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Entity
@Table(name = "Rols")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "usuarios")
    private Set<Usuario> usuarios;  // Relación con Usuario (múltiples usuarios pueden tener un Rol)
}
