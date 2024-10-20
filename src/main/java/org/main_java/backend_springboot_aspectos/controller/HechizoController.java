package org.main_java.backend_springboot_aspectos.controller;

import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;
import org.main_java.backend_springboot_aspectos.service.HechizoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hechizos")
public class HechizoController {

    private final HechizoService hechizoService;

    public HechizoController(HechizoService hechizoService) {
        this.hechizoService = hechizoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hechizo> obtenerHechizoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(hechizoService.obtenerHechizoPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Hechizo>> obtenerTodosLosHechizos() {
        return ResponseEntity.ok(hechizoService.obtenerTodosLosHechizos());
    }

    @PostMapping
    public ResponseEntity<Hechizo> crearHechizo(@RequestBody Hechizo hechizo) {
        return ResponseEntity.ok(hechizoService.crearHechizo(hechizo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hechizo> actualizarHechizo(@PathVariable Long id, @RequestBody Hechizo hechizoActualizado) {
        return ResponseEntity.ok(hechizoService.actualizarHechizo(id, hechizoActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHechizo(@PathVariable Long id) {
        hechizoService.eliminarHechizo(id);
        return ResponseEntity.noContent().build();
    }
}
