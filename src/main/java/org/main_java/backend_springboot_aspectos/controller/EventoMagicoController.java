package org.main_java.backend_springboot_aspectos.controller;

import org.main_java.backend_springboot_aspectos.model.EventoMagicoDTO;
import org.main_java.backend_springboot_aspectos.service.EventoMagicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/eventos-magicos")
public class EventoMagicoController {

    private final EventoMagicoService eventoMagicoService;

    public EventoMagicoController(EventoMagicoService eventoMagicoService) {
        this.eventoMagicoService = eventoMagicoService;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<EventoMagicoDTO>> obtenerEventoMagicoPorId(@PathVariable Long id) {
        return eventoMagicoService.obtenerEventoMagicoPorId(id)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/getAll")
    public CompletableFuture<ResponseEntity<List<EventoMagicoDTO>>> obtenerTodosLosEventosMagicos() {
        return eventoMagicoService.obtenerTodosLosEventosMagicos()
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<EventoMagicoDTO>> crearEventoMagico(@RequestBody EventoMagicoDTO eventoMagicoDTO) {
        return eventoMagicoService.crearEventoMagico(eventoMagicoDTO)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<EventoMagicoDTO>> actualizarEventoMagico(@PathVariable Long id, @RequestBody EventoMagicoDTO eventoMagicoDTO) {
        return eventoMagicoService.actualizarEventoMagico(id, eventoMagicoDTO)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> eliminarEventoMagico(@PathVariable Long id) {
        return eventoMagicoService.eliminarEventoMagico(id)
                .thenApply(result -> ResponseEntity.noContent().build());
    }
}
