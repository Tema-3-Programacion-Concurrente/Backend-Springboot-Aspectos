package org.main_java.backend_springboot_aspectos.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.main_java.backend_springboot_aspectos.model.hechizos.HechizoDTO;
import org.main_java.backend_springboot_aspectos.service.HechizoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hechizos")
public class HechizoResource {

    private final HechizoService hechizoService;

    public HechizoResource(final HechizoService hechizoService) {
        this.hechizoService = hechizoService;
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Get all hechizos")
    public ResponseEntity<List<HechizoDTO>> getAllHechizos() {
        return ResponseEntity.ok(hechizoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HechizoDTO> getHechizo(@PathVariable final Long id) {
        return ResponseEntity.ok(hechizoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Create a new hechizo")
    public ResponseEntity<Long> createHechizo(@RequestBody final HechizoDTO hechizoDTO) {
        return new ResponseEntity<>(hechizoService.create(hechizoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateHechizo(@PathVariable final Long id, @RequestBody final HechizoDTO hechizoDTO) {
        hechizoService.update(id, hechizoDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Delete a hechizo")
    public ResponseEntity<Void> deleteHechizo(@PathVariable final Long id) {
        hechizoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

