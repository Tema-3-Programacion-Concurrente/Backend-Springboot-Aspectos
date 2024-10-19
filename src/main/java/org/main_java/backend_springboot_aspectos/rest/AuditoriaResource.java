package org.main_java.backend_springboot_aspectos.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.main_java.backend_springboot_aspectos.model.AuditoriaDTO;
import org.main_java.backend_springboot_aspectos.service.AuditoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditorias")
public class AuditoriaResource {

    private final AuditoriaService auditoriaService;

    public AuditoriaResource(final AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Get all auditorías")
    public ResponseEntity<List<AuditoriaDTO>> getAllAuditorias() {
        return ResponseEntity.ok(auditoriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaDTO> getAuditoria(@PathVariable final Long id) {
        return ResponseEntity.ok(auditoriaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Create a new auditoría")
    public ResponseEntity<Long> createAuditoria(@RequestBody final AuditoriaDTO auditoriaDTO) {
        return new ResponseEntity<>(auditoriaService.create(auditoriaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAuditoria(@PathVariable final Long id, @RequestBody final AuditoriaDTO auditoriaDTO) {
        auditoriaService.update(id, auditoriaDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Delete an auditoría")
    public ResponseEntity<Void> deleteAuditoria(@PathVariable final Long id) {
        auditoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

