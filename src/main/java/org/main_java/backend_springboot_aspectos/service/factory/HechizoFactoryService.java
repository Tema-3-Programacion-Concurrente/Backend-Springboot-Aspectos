package org.main_java.backend_springboot_aspectos.service.factory;

import org.main_java.backend_springboot_aspectos.domain.hechizos.*;
import org.main_java.backend_springboot_aspectos.repos.HechizoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HechizoFactoryService {

    @Autowired
    private HechizoRepository hechizoRepository;

    public Hechizo obtenerHechizo(String tipo) {
        Optional<Hechizo> hechizo = hechizoRepository.findByNombre(tipo.toLowerCase());
        return hechizo.orElseThrow(() -> new IllegalArgumentException("No se encontró un hechizo del tipo: " + tipo));
    }

    public Hechizo crearHechizo(String tipo) {
        switch (tipo.toLowerCase()) {
            case "fuego":
                return new HechizoFuego();
            case "agua":
                return new HechizoAgua();
            case "aire":
                return new HechizoAire();
            case "roca":
                return new HechizoRoca();
            default:
                throw new IllegalArgumentException("Tipo de hechizo no válido: " + tipo);
        }
    }
}


