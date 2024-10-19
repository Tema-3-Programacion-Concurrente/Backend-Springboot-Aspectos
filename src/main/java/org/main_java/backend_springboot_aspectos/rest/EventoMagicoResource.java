package org.main_java.backend_springboot_aspectos.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.main_java.backend_springboot_aspectos.model.EventoMagicoDTO;
import org.main_java.backend_springboot_aspectos.service.EventoMagicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoMagicoResource {

    private final EventoMagicoService eventoMagicoService;

    public EventoMagicoResource(final EventoMagicoService eventoMagicoService) {
        this.eventoMagicoService = eventoMagicoService;
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Get all eventos mágicos")
    public ResponseEntity<List<EventoMagicoDTO>> getAllEventos() {
        return ResponseEntity.ok(eventoMagicoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoMagicoDTO> getEvento(@PathVariable final Long id) {
        return ResponseEntity.ok(eventoMagicoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Create a new evento mágico")
    public ResponseEntity<Long> createEvento(@RequestBody final EventoMagicoDTO eventoMagicoDTO) {
        return new ResponseEntity<>(eventoMagicoService.create(eventoMagicoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvento(@PathVariable final Long id, @RequestBody final EventoMagicoDTO eventoMagicoDTO) {
        eventoMagicoService.update(id, eventoMagicoDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Delete an evento mágico")
    public ResponseEntity<Void> deleteEvento(@PathVariable final Long id) {
        eventoMagicoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

