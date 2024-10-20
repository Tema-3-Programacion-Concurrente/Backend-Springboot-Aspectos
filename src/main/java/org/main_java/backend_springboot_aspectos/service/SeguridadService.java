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
}

