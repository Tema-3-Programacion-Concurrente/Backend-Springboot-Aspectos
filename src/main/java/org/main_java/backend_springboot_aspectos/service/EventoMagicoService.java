package org.main_java.backend_springboot_aspectos.service;

import org.main_java.backend_springboot_aspectos.domain.EventoMagico;
import org.main_java.backend_springboot_aspectos.domain.Usuario;
import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;
import org.main_java.backend_springboot_aspectos.model.EventoMagicoDTO;
import org.main_java.backend_springboot_aspectos.repos.EventoMagicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class EventoMagicoService {

    @Autowired
    private EventoMagicoRepository eventoMagicoRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    // Lock para sincronización de acceso a registros de eventos mágicos
    private final Lock lock = new ReentrantLock();

    /**
     * Crear un nuevo evento mágico de manera concurrente.
     * @param eventoMagicoDTO El DTO con la información del evento mágico.
     * @return Un CompletableFuture con el resultado.
     */
    @Async
    @Transactional
    public CompletableFuture<EventoMagicoDTO> crearEventoMagico(EventoMagicoDTO eventoMagicoDTO) {
        return CompletableFuture.supplyAsync(() -> {
            lock.lock();
            try {
                EventoMagico eventoMagico = mapToEntity(eventoMagicoDTO, new EventoMagico());
                EventoMagico eventoGuardado = eventoMagicoRepository.save(eventoMagico);
                return mapToDTO(eventoGuardado, new EventoMagicoDTO());
            } finally {
                lock.unlock();
            }
        }, executorService);
    }

    /**
     * Obtener un evento mágico por su ID de manera concurrente.
     * @param id El ID del evento mágico.
     * @return Un CompletableFuture con el DTO del evento mágico.
     */
    @Async
    @Transactional
    public CompletableFuture<EventoMagicoDTO> obtenerEventoMagicoPorId(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            lock.lock();
            try {
                EventoMagico eventoMagico = eventoMagicoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Evento mágico no encontrado"));
                return mapToDTO(eventoMagico, new EventoMagicoDTO());
            } finally {
                lock.unlock();
            }
        }, executorService);
    }

    /**
     * Obtener todos los eventos mágicos de manera concurrente.
     * @return Un CompletableFuture con la lista de DTOs de eventos mágicos.
     */
    @Async
    @Transactional
    public CompletableFuture<List<EventoMagicoDTO>> obtenerTodosLosEventosMagicos() {
        return CompletableFuture.supplyAsync(() -> {
            lock.lock();
            try {
                List<EventoMagico> eventos = eventoMagicoRepository.findAll();
                return eventos.stream()
                        .map(evento -> mapToDTO(evento, new EventoMagicoDTO()))
                        .collect(Collectors.toList());
            } finally {
                lock.unlock();
            }
        }, executorService);
    }

    /**
     * Actualizar un evento mágico existente de manera concurrente.
     * @param id El ID del evento mágico a actualizar.
     * @param eventoMagicoDTO El DTO con la nueva información del evento mágico.
     * @return Un CompletableFuture con el DTO actualizado.
     */
    @Async
    @Transactional
    public CompletableFuture<EventoMagicoDTO> actualizarEventoMagico(Long id, EventoMagicoDTO eventoMagicoDTO) {
        return CompletableFuture.supplyAsync(() -> {
            lock.lock();
            try {
                EventoMagico eventoExistente = eventoMagicoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Evento mágico no encontrado"));

                mapToEntity(eventoMagicoDTO, eventoExistente);
                EventoMagico eventoActualizado = eventoMagicoRepository.save(eventoExistente);

                return mapToDTO(eventoActualizado, new EventoMagicoDTO());
            } finally {
                lock.unlock();
            }
        }, executorService);
    }

    /**
     * Eliminar un evento mágico de manera concurrente.
     * @param id El ID del evento mágico a eliminar.
     * @return Un CompletableFuture<Void> que indica cuando la operación ha sido completada.
     */
    @Async
    @Transactional
    public CompletableFuture<Void> eliminarEventoMagico(Long id) {
        return CompletableFuture.runAsync(() -> {
            lock.lock();
            try {
                eventoMagicoRepository.deleteById(id);
            } finally {
                lock.unlock();
            }
        }, executorService);
    }

    // Métodos de mapeo entre entidad y DTO

    /**
     * Mapear entidad EventoMagico a DTO EventoMagicoDTO.
     * @param eventoMagico La entidad a mapear.
     * @param eventoMagicoDTO El DTO destino.
     * @return El DTO mapeado.
     */
    private EventoMagicoDTO mapToDTO(EventoMagico eventoMagico, EventoMagicoDTO eventoMagicoDTO) {
        eventoMagicoDTO.setId(eventoMagico.getId());
        eventoMagicoDTO.setDescripcion(eventoMagico.getDescripcion());
        eventoMagicoDTO.setFecha(eventoMagico.getFecha());
        eventoMagicoDTO.setLanzadorId(eventoMagico.getLanzador().getId());
        eventoMagicoDTO.setHechizoLanzadoId(eventoMagico.getHechizoLanzado().getId());
        return eventoMagicoDTO;
    }

    /**
     * Mapear DTO EventoMagicoDTO a entidad EventoMagico.
     * @param eventoMagicoDTO El DTO origen.
     * @param eventoMagico La entidad destino.
     * @return La entidad mapeada.
     */
    private EventoMagico mapToEntity(EventoMagicoDTO eventoMagicoDTO, EventoMagico eventoMagico) {
        eventoMagico.setDescripcion(eventoMagicoDTO.getDescripcion());
        eventoMagico.setFecha(eventoMagicoDTO.getFecha());
        // Aquí debes agregar la lógica para mapear los lanzadores y hechizos (repos o servicios adicionales).
        return eventoMagico;
    }

    /**
     * Registra un evento mágico generado por el lanzamiento de un hechizo.
     * Este metodo es transaccional, pero se ejecuta de forma concurrente utilizando un pool de hilos
     * para garantizar que varios eventos mágicos puedan registrarse simultáneamente.
     *
     * @param usuario El usuario que ha lanzado el hechizo.
     * @param hechizo El hechizo que ha sido lanzado.
     * @return Un CompletableFuture<Void> que indica el estado de la tarea asíncrona.
     */
    @Transactional
    public CompletableFuture<Void> registrarEventoMagico(Usuario usuario, Hechizo hechizo) {
        return CompletableFuture.runAsync(() -> {
            lock.lock(); // Adquirir lock para asegurar sincronización de registros concurrentes
            try {
                // Crear un nuevo evento mágico
                EventoMagico eventoMagico = new EventoMagico();
                eventoMagico.setTipoEvento("Lanzar Hechizo");
                eventoMagico.setDescripcion("El usuario " + usuario.getNombre() + " lanzó el hechizo " + hechizo.getNombre());
                eventoMagico.setFecha(java.time.LocalDateTime.now());
                eventoMagico.setLanzador(usuario);
                eventoMagico.setHechizoLanzado(hechizo);

                // Guardar el evento mágico en la base de datos
                eventoMagicoRepository.save(eventoMagico);

                System.out.println("Evento mágico registrado: " + eventoMagico.getDescripcion());
            } finally {
                lock.unlock(); // Liberar lock para permitir que otros hilos registren eventos
            }
        }, executorService);
    }
}
