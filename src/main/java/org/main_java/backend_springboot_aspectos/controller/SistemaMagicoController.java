package org.main_java.backend_springboot_aspectos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.main_java.backend_springboot_aspectos.domain.EventoMagico;
import org.main_java.backend_springboot_aspectos.domain.Usuario;
import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;
import org.main_java.backend_springboot_aspectos.model.LanzarHechizoRequest;
import org.main_java.backend_springboot_aspectos.service.SistemaMagicoService;
import org.main_java.backend_springboot_aspectos.service.factory.HechizoFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sistema-magico")
public class SistemaMagicoController {

    private final SistemaMagicoService sistemaMagicoService;


    @Autowired
    private HechizoFactoryService hechizoFactoryService;



    public SistemaMagicoController(SistemaMagicoService sistemaMagicoService) {
        this.sistemaMagicoService = sistemaMagicoService;
    }

    @PostMapping("/lanzar-hechizo")
    public ResponseEntity<String> lanzarHechizo(@RequestBody LanzarHechizoRequest request) {
        try {
            Usuario usuario = request.getUsuario();
            Hechizo hechizo = request.getHechizo();

            sistemaMagicoService.lanzarHechizo(usuario, hechizo);
            return new ResponseEntity<>("Hechizo lanzado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al lanzar el hechizo: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
