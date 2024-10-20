package org.main_java.backend_springboot_aspectos.service;

import org.main_java.backend_springboot_aspectos.domain.Auditoria;
import org.main_java.backend_springboot_aspectos.domain.EventoMagico;
import org.main_java.backend_springboot_aspectos.model.AuditoriaDTO;
import org.main_java.backend_springboot_aspectos.repos.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    // Pool de hilos para manejar la concurrencia en la auditoría
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * Auditar un evento mágico.
     *
     * @param evento El evento mágico a auditar.
     */
    @Transactional
    public void auditarEvento(EventoMagico evento) {
        executorService.submit(() -> {
            Auditoria auditoria = new Auditoria();
            auditoria.setFecha(LocalDateTime.now());
            auditoria.setAccion("Evento mágico auditado");
            auditoria.setDescripcion("Se auditó el evento en el que el usuario " + evento.getLanzador().getNombre()
                    + " lanzó el hechizo " + evento.getHechizoLanzado().getNombre());
            auditoria.setUsuario(evento.getLanzador());
            auditoria.setEventoAuditable(evento);

            auditoriaRepository.save(auditoria);
            System.out.println("Auditoría registrada para el evento: " + auditoria.getDescripcion());
        });
    }

    // CRUD completo para Auditoria

    @Transactional
    public Auditoria crearAuditoria(Auditoria auditoria) {
        return auditoriaRepository.save(auditoria);
    }

    @Transactional(readOnly = true)
    public Auditoria obtenerAuditoriaPorId(Long id) {
        return auditoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auditoría no encontrada"));
    }

    @Transactional(readOnly = true)
    public List<AuditoriaDTO> obtenerTodasLasAuditorias() {
        return auditoriaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Auditoria actualizarAuditoria(Long id, Auditoria auditoriaActualizada) {
        Auditoria auditoria = auditoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auditoría no encontrada"));

        auditoria.setAccion(auditoriaActualizada.getAccion());
        auditoria.setDescripcion(auditoriaActualizada.getDescripcion());
        auditoria.setFecha(auditoriaActualizada.getFecha());
        auditoria.setUsuario(auditoriaActualizada.getUsuario());
        auditoria.setEventoAuditable(auditoriaActualizada.getEventoAuditable());

        return auditoriaRepository.save(auditoria);
    }

    @Transactional
    public void eliminarAuditoria(Long id) {
        auditoriaRepository.deleteById(id);
    }

    // Métodos de mapeo entre Auditoria y AuditoriaDTO

    public AuditoriaDTO mapToDTO(Auditoria auditoria) {
        AuditoriaDTO dto = new AuditoriaDTO();
        dto.setId(auditoria.getId());
        dto.setFecha(auditoria.getFecha());
        dto.setAccion(auditoria.getAccion());
        dto.setDescripcion(auditoria.getDescripcion());
        dto.setUsuarioId(auditoria.getUsuario().getId());
        dto.setEventoAuditableId(auditoria.getEventoAuditable().getId());
        return dto;
    }

    public Auditoria mapToEntity(AuditoriaDTO dto, Auditoria auditoria) {
        auditoria.setAccion(dto.getAccion());
        auditoria.setDescripcion(dto.getDescripcion());
        auditoria.setFecha(dto.getFecha());
        // Aquí debes mapear los objetos de usuario y evento auditable según sus repositorios
        return auditoria;
    }
}
