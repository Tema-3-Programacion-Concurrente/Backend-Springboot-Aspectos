package org.main_java.backend_springboot_aspectos.service;

import jakarta.transaction.Transactional;
import org.main_java.backend_springboot_aspectos.domain.Registro;
import org.main_java.backend_springboot_aspectos.domain.Usuario;
import org.main_java.backend_springboot_aspectos.model.RegistroDTO;
import org.main_java.backend_springboot_aspectos.repos.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;

    // Pool de hilos para manejar concurrencia
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    // Lock para sincronización
    private final Lock lock = new ReentrantLock();

    // CRUD con concurrencia y conversión DTO

    /**
     * Crear un nuevo registro de manera concurrente.
     * @param registroDTO El DTO con la información del registro.
     * @return Un CompletableFuture con el resultado.
     */
    @Async
    @Transactional
    public CompletableFuture<RegistroDTO> crearRegistro(RegistroDTO registroDTO) {
        return CompletableFuture.supplyAsync(() -> {
            lock.lock();
            try {
                Registro registro = mapToEntity(registroDTO, new Registro());
                Registro registroGuardado = registroRepository.save(registro);
                return mapToDTO(registroGuardado, new RegistroDTO());
            } finally {
                lock.unlock();
            }
        }, executorService);
    }

    /**
     * Obtener un registro por su ID de manera concurrente.
     * @param id El ID del registro.
     * @return Un CompletableFuture con el DTO del registro.
     */
    @Async
    @Transactional
    public CompletableFuture<RegistroDTO> obtenerRegistroPorId(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            lock.lock();
            try {
                Registro registro = registroRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Registro no encontrado"));
                return mapToDTO(registro, new RegistroDTO());
            } finally {
                lock.unlock();
            }
        }, executorService);
    }

    /**
     * Obtener todos los registros de manera concurrente.
     * @return Un CompletableFuture con la lista de DTOs de registros.
     */
    @Async
    @Transactional
    public CompletableFuture<List<RegistroDTO>> obtenerTodosLosRegistros() {
        return CompletableFuture.supplyAsync(() -> {
            lock.lock();
            try {
                List<Registro> registros = registroRepository.findAll();
                return registros.stream()
                        .map(registro -> mapToDTO(registro, new RegistroDTO()))
                        .collect(Collectors.toList());
            } finally {
                lock.unlock();
            }
        }, executorService);
    }

    /**
     * Actualizar un registro existente de manera concurrente.
     * @param id El ID del registro a actualizar.
     * @param registroDTO El DTO con la nueva información del registro.
     * @return Un CompletableFuture con el DTO actualizado.
     */
    @Async
    @Transactional
    public CompletableFuture<RegistroDTO> actualizarRegistro(Long id, RegistroDTO registroDTO) {
        return CompletableFuture.supplyAsync(() -> {
            lock.lock();
            try {
                Registro registroExistente = registroRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

                mapToEntity(registroDTO, registroExistente);
                Registro registroActualizado = registroRepository.save(registroExistente);

                return mapToDTO(registroActualizado, new RegistroDTO());
            } finally {
                lock.unlock();
            }
        }, executorService);
    }

    /**
     * Eliminar un registro de manera concurrente.
     * @param id El ID del registro a eliminar.
     * @return Un CompletableFuture<Void> que indica cuando la operación ha sido completada.
     */
    @Async
    @Transactional
    public CompletableFuture<Void> eliminarRegistro(Long id) {
        return CompletableFuture.runAsync(() -> {
            lock.lock();
            try {
                registroRepository.deleteById(id);
            } finally {
                lock.unlock();
            }
        }, executorService);
    }

    // Métodos de mapeo entre entidad y DTO

    /**
     * Mapear entidad Registro a DTO RegistroDTO.
     * @param registro La entidad a mapear.
     * @param registroDTO El DTO destino.
     * @return El DTO mapeado.
     */
    private RegistroDTO mapToDTO(Registro registro, RegistroDTO registroDTO) {
        registroDTO.setId(registro.getId());
        registroDTO.setUsuarioId(registro.getUsuario().getId());
        registroDTO.setAccion(registro.getAccion());
        registroDTO.setFecha(registro.getFecha());
        registroDTO.setResultado(registro.getResultado());
        return registroDTO;
    }

    /**
     * Mapear DTO RegistroDTO a entidad Registro.
     * @param registroDTO El DTO origen.
     * @param registro La entidad destino.
     * @return La entidad mapeada.
     */
    private Registro mapToEntity(RegistroDTO registroDTO, Registro registro) {
        registro.setAccion(registroDTO.getAccion());
        registro.setFecha(registroDTO.getFecha());
        registro.setResultado(registroDTO.getResultado());
        // Aquí puedes mapear el usuario y otros atributos según sea necesario.
        return registro;
    }


    /**
     * Registra una acción del usuario de manera concurrente y asíncrona.
     *
     * @param usuario  El usuario que realiza la acción.
     * @param accion   La acción realizada por el usuario.
     * @param resultado El resultado de la acción.
     * @return Un CompletableFuture<Void> que indica cuando la operación ha sido completada.
     */
    @Async
    @Transactional
    public CompletableFuture<Void> registrarAccion(Usuario usuario, String accion, String resultado) {
        return CompletableFuture.runAsync(() -> {
            lock.lock(); // Bloqueamos para asegurar que solo un hilo escriba en la base de datos a la vez
            try {
                // Crear una nueva instancia de Registro
                Registro registro = new Registro();
                registro.setUsuario(usuario);
                registro.setAccion(accion);
                registro.setFecha(java.time.LocalDateTime.now());
                registro.setResultado(resultado);

                // Guardar el registro en la base de datos
                registroRepository.save(registro);

                System.out.println("Acción registrada: " + accion + " por el usuario: " + usuario.getNombre());
            } finally {
                lock.unlock(); // Liberamos el lock para que otros hilos puedan continuar
            }
        }, executorService); // Ejecutamos esta tarea de forma asíncrona utilizando el pool de hilos
    }
}
