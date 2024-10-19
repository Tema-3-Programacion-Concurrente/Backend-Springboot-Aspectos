package org.main_java.backend_springboot_aspectos.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SistemasMagicos")
@Getter
@Setter
public class SistemaMagico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "sistema_magico_id")
    private List<Hechizo> listaHechizos = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "sistema_magico_id")
    private List<Usuario> usuarios = new ArrayList<>();
}

