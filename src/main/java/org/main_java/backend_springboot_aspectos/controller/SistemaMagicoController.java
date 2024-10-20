package org.main_java.backend_springboot_aspectos.controller;

import org.main_java.backend_springboot_aspectos.domain.EventoMagico;
import org.main_java.backend_springboot_aspectos.domain.Usuario;
import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;
import org.main_java.backend_springboot_aspectos.service.SistemaMagicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sistema-magico")
public class SistemaMagicoController {

    private final SistemaMagicoService sistemaMagicoService;

    public SistemaMagicoController(SistemaMagicoService sistemaMagicoService) {
        this.sistemaMagicoService = sistemaMagicoService;
    }

    @PostMapping("/lanzar-hechizo")
    public ResponseEntity<String> lanzarHechizo(@RequestBody Usuario usuario, @RequestBody Hechizo hechizo) {
        sistemaMagicoService.lanzarHechizo(usuario, hechizo);
        return new ResponseEntity<>("Hechizo lanzado con éxito", HttpStatus.OK);
    }

    @PostMapping("/registrar-evento-magico")
    public ResponseEntity<String> registrarEventoMagico(@RequestBody Usuario usuario, @RequestBody Hechizo hechizo) {
        sistemaMagicoService.registrarEventoMagico(usuario, hechizo);
        return new ResponseEntity<>("Evento mágico registrado", HttpStatus.OK);
    }

    @PostMapping("/auditar-evento-magico")
    public ResponseEntity<String> auditarEventoMagico(@RequestBody EventoMagico evento) {
        sistemaMagicoService.auditarEventoMagico(evento);
        return new ResponseEntity<>("Evento mágico auditado", HttpStatus.OK);
    }
}
