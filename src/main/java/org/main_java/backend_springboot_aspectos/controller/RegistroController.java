package org.main_java.backend_springboot_aspectos.controller;

import org.main_java.backend_springboot_aspectos.model.RegistroDTO;
import org.main_java.backend_springboot_aspectos.service.RegistroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/registros")
public class RegistroController {

    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<RegistroDTO>> obtenerRegistroPorId(@PathVariable Long id) {
        return registroService.obtenerRegistroPorId(id)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<RegistroDTO>>> obtenerTodosLosRegistros() {
        return registroService.obtenerTodosLosRegistros()
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<RegistroDTO>> crearRegistro(@RequestBody RegistroDTO registroDTO) {
        return registroService.crearRegistro(registroDTO)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<RegistroDTO>> actualizarRegistro(@PathVariable Long id, @RequestBody RegistroDTO registroDTO) {
        return registroService.actualizarRegistro(id, registroDTO)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> eliminarRegistro(@PathVariable Long id) {
        return registroService.eliminarRegistro(id)
                .thenApply(result -> ResponseEntity.noContent().build());
    }
}
