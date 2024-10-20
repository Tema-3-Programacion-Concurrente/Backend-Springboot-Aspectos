package org.main_java.backend_springboot_aspectos.controller;

import org.main_java.backend_springboot_aspectos.domain.Seguridad;
import org.main_java.backend_springboot_aspectos.service.SeguridadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seguridad")
public class SeguridadController {

    private final SeguridadService seguridadService;

    public SeguridadController(SeguridadService seguridadService) {
        this.seguridadService = seguridadService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seguridad> obtenerSeguridadPorId(@PathVariable Long id) {
        return ResponseEntity.ok(seguridadService.obtenerSeguridadPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Seguridad>> obtenerTodasLasSeguridades() {
        return ResponseEntity.ok(seguridadService.obtenerTodasLasSeguridades());
    }

    @PostMapping
    public ResponseEntity<Seguridad> crearSeguridad(@RequestBody Seguridad seguridad) {
        return ResponseEntity.ok(seguridadService.crearSeguridad(seguridad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seguridad> actualizarSeguridad(@PathVariable Long id, @RequestBody Seguridad seguridad) {
        return ResponseEntity.ok(seguridadService.actualizarSeguridad(id, seguridad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSeguridad(@PathVariable Long id) {
        seguridadService.eliminarSeguridad(id);
        return ResponseEntity.noContent().build();
    }
}
