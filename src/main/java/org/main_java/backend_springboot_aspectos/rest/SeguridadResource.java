package org.main_java.backend_springboot_aspectos.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.main_java.backend_springboot_aspectos.model.SeguridadDTO;
import org.main_java.backend_springboot_aspectos.service.SeguridadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seguridad")
public class SeguridadResource {

    private final SeguridadService seguridadService;

    public SeguridadResource(final SeguridadService seguridadService) {
        this.seguridadService = seguridadService;
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Get all registros de seguridad")
    public ResponseEntity<List<SeguridadDTO>> getAllSeguridad() {
        return ResponseEntity.ok(seguridadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeguridadDTO> getSeguridad(@PathVariable final Long id) {
        return ResponseEntity.ok(seguridadService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Create a new registro de seguridad")
    public ResponseEntity<Long> createSeguridad(@RequestBody final SeguridadDTO seguridadDTO) {
        return new ResponseEntity<>(seguridadService.create(seguridadDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSeguridad(@PathVariable final Long id, @RequestBody final SeguridadDTO seguridadDTO) {
        seguridadService.update(id, seguridadDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Delete a registro de seguridad")
    public ResponseEntity<Void> deleteSeguridad(@PathVariable final Long id) {
        seguridadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
