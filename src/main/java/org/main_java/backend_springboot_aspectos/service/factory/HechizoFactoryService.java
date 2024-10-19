package org.main_java.backend_springboot_aspectos.service.factory;

import org.main_java.backend_springboot_aspectos.domain.hechizos.*;
import org.springframework.stereotype.Service;

@Service
public class HechizoFactoryService {

    // Metodo que utiliza el patrón Factory para crear diferentes tipos de hechizos
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

