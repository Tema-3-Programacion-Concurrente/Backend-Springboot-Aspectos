package org.main_java.backend_springboot_aspectos.service;

import org.main_java.backend_springboot_aspectos.domain.Seguridad;
import org.main_java.backend_springboot_aspectos.domain.Usuario;
import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;
import org.main_java.backend_springboot_aspectos.model.SeguridadDTO;
import org.main_java.backend_springboot_aspectos.repos.SeguridadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class SeguridadService {

    @Autowired
    private SeguridadRepository seguridadRepository;

    private final Lock lock = new ReentrantLock();  // Lock para concurrencia en verificación de acceso

    /**
     * Verifica si un usuario tiene permiso para ejecutar un hechizo.
     *
     * @param usuario El usuario que intenta ejecutar el hechizo.
     * @param hechizo El hechizo que se quiere lanzar.
     * @return true si el usuario tiene permiso, false en caso contrario.
     */
    @Transactional(readOnly = true)
    public boolean verificarAcceso(Usuario usuario, Hechizo hechizo) {
        lock.lock();  // Bloqueamos la sección para asegurar concurrencia
        try {
            // Verificamos si el hechizo está permitido para este usuario
            return hechizo.esPermitido(usuario);
        } finally {
            lock.unlock();  // Liberamos el lock
        }
    }

    // CRUD completo para Seguridad

    /**
     * Crea un nuevo registro de seguridad.
     *
     * @param seguridad El objeto Seguridad a crear.
     * @return El objeto Seguridad creado.
     */
    @Transactional
    public Seguridad crearSeguridad(Seguridad seguridad) {
        lock.lock();  // Bloqueamos para evitar que varios hilos creen registros simultáneamente
        try {
            return seguridadRepository.save(seguridad);
        } finally {
            lock.unlock();  // Liberamos el lock después de la operación
        }
    }

    /**
     * Obtiene un registro de seguridad por su ID.
     *
     * @param id El ID del registro de seguridad.
     * @return El registro de seguridad correspondiente.
     */
    @Transactional(readOnly = true)
    public Seguridad obtenerSeguridadPorId(Long id) {
        lock.lock();
        try {
            return seguridadRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Registro de seguridad no encontrado"));
        } finally {
            lock.unlock();
        }
    }

    /**
     * Obtiene todos los registros de seguridad.
     *
     * @return Una lista de todos los registros de seguridad.
     */
    @Transactional(readOnly = true)
    public List<Seguridad> obtenerTodasLasSeguridades() {
        lock.lock();
        try {
            return seguridadRepository.findAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Actualiza un registro de seguridad.
     *
     * @param id El ID del registro de seguridad a actualizar.
     * @param seguridadActualizada El objeto Seguridad actualizado.
     * @return El objeto Seguridad actualizado.
     */
    @Transactional
    public Seguridad actualizarSeguridad(Long id, Seguridad seguridadActualizada) {
        lock.lock();
        try {
            Seguridad seguridad = seguridadRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Registro de seguridad no encontrado"));

            seguridad.setAccion(seguridadActualizada.getAccion());
            seguridad.setAutorizado(seguridadActualizada.isAutorizado());
            seguridad.setFechaAutorizacion(seguridadActualizada.getFechaAutorizacion());
            seguridad.setUsuario(seguridadActualizada.getUsuario());

            return seguridadRepository.save(seguridad);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Elimina un registro de seguridad por su ID.
     *
     * @param id El ID del registro de seguridad a eliminar.
     */
    @Transactional
    public void eliminarSeguridad(Long id) {
        lock.lock();
        try {
            seguridadRepository.deleteById(id);
        } finally {
            lock.unlock();
        }
    }

    // Métodos de mapeo entre Seguridad y SeguridadDTO

    /**
     * Mapea un objeto Seguridad a un objeto SeguridadDTO.
     *
     * @param seguridad El objeto Seguridad a mapear.
     * @return El objeto SeguridadDTO mapeado.
     */
    public SeguridadDTO mapToDTO(Seguridad seguridad) {
        SeguridadDTO dto = new SeguridadDTO();
        dto.setId(seguridad.getId());
        dto.setAccion(seguridad.getAccion());
        dto.setAutorizado(seguridad.isAutorizado());
        dto.setFechaAutorizacion(seguridad.getFechaAutorizacion());
        dto.setUsuarioId(seguridad.getUsuario() != null ? seguridad.getUsuario().getId() : null);
        return dto;
    }

    /**
     * Mapea un objeto SeguridadDTO a un objeto Seguridad.
     *
     * @param dto El objeto SeguridadDTO a mapear.
     * @param seguridad El objeto Seguridad existente.
     * @return El objeto Seguridad mapeado.
     */
    public Seguridad mapToEntity(SeguridadDTO dto, Seguridad seguridad) {
        seguridad.setAccion(dto.getAccion());
        seguridad.setAutorizado(dto.isAutorizado());
        seguridad.setFechaAutorizacion(dto.getFechaAutorizacion());
        // Aquí suponemos que el Usuario ya se ha recuperado por otro metodo, y lo asignamos
        return seguridad;
    }
}

