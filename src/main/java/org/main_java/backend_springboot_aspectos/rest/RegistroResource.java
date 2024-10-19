package org.main_java.backend_springboot_aspectos.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.main_java.backend_springboot_aspectos.model.RegistroDTO;
import org.main_java.backend_springboot_aspectos.service.RegistroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registros")
public class RegistroResource {

    private final RegistroService registroService;

    public RegistroResource(final RegistroService registroService) {
        this.registroService = registroService;
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Get all registros")
    public ResponseEntity<List<RegistroDTO>> getAllRegistros() {
        return ResponseEntity.ok(registroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroDTO> getRegistro(@PathVariable final Long id) {
        return ResponseEntity.ok(registroService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Create a new registro")
    public ResponseEntity<Long> createRegistro(@RequestBody final RegistroDTO registroDTO) {
        return new ResponseEntity<>(registroService.create(registroDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRegistro(@PathVariable final Long id, @RequestBody final RegistroDTO registroDTO) {
        registroService.update(id, registroDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Delete a registro")
    public ResponseEntity<Void> deleteRegistro(@PathVariable final Long id) {
        registroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
