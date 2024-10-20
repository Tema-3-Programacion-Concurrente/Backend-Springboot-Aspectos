package org.main_java.backend_springboot_aspectos.controller;

import org.main_java.backend_springboot_aspectos.domain.Auditoria;
import org.main_java.backend_springboot_aspectos.domain.EventoMagico;
import org.main_java.backend_springboot_aspectos.model.AuditoriaDTO;
import org.main_java.backend_springboot_aspectos.service.AuditoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditorias")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    // CRUD
    @PostMapping
    public ResponseEntity<Long> crearAuditoria(@RequestBody AuditoriaDTO auditoriaDTO) {
        Auditoria auditoria = new Auditoria();
        auditoriaService.mapToEntity(auditoriaDTO, auditoria);
        Long createdId = auditoriaService.crearAuditoria(auditoria).getId();
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaDTO> obtenerAuditoriaPorId(@PathVariable Long id) {
        Auditoria auditoria = auditoriaService.obtenerAuditoriaPorId(id);
        AuditoriaDTO auditoriaDTO = auditoriaService.mapToDTO(auditoria);
        return ResponseEntity.ok(auditoriaDTO);
    }

    @GetMapping
    public ResponseEntity<List<AuditoriaDTO>> obtenerTodasLasAuditorias() {
        List<AuditoriaDTO> auditorias = auditoriaService.obtenerTodasLasAuditorias();
        return ResponseEntity.ok(auditorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarAuditoria(@PathVariable Long id,
                                                    @RequestBody AuditoriaDTO auditoriaDTO) {
        Auditoria auditoria = auditoriaService.obtenerAuditoriaPorId(id);
        auditoriaService.mapToEntity(auditoriaDTO, auditoria);
        auditoriaService.actualizarAuditoria(id, auditoria);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAuditoria(@PathVariable Long id) {
        auditoriaService.eliminarAuditoria(id);
        return ResponseEntity.noContent().build();
    }
}
