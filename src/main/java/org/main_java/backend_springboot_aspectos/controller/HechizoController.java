package org.main_java.backend_springboot_aspectos.controller;

import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;
import org.main_java.backend_springboot_aspectos.service.HechizoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


    @GetMapping("/getAll")
    public ResponseEntity<List<Hechizo>> obtenerTodosLosHechizos() {
        return ResponseEntity.ok(hechizoService.obtenerTodosLosHechizos());
    }

    @PostMapping("/crear")
    public ResponseEntity<Hechizo> crearHechizo(@RequestBody Map<String, Object> requestData) {
        // Obtener el tipo y poder desde el cuerpo de la solicitud
        String tipoHechizo = (String) requestData.get("tipo");
        int poder = (Integer) requestData.get("poder");

        // Crear el hechizo usando el servicio de hechizos y el factory
        Hechizo hechizoCreado = hechizoService.crearHechizo(tipoHechizo, poder);

        // Devolver el hechizo creado en la respuesta
        return ResponseEntity.ok(hechizoCreado);
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
