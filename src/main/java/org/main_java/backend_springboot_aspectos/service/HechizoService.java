package org.main_java.backend_springboot_aspectos.service;

import jakarta.transaction.Transactional;
import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;
import org.main_java.backend_springboot_aspectos.domain.Usuario;
import org.main_java.backend_springboot_aspectos.repos.HechizoRepository;
import org.main_java.backend_springboot_aspectos.service.factory.HechizoFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class HechizoService {

    @Autowired
    private HechizoRepository hechizoRepository;

    @Autowired
    private HechizoFactoryService hechizoFactoryService;

    @Autowired
    private EventoMagicoService eventoMagicoService;

    @Autowired
    private RegistroService registroService;

    @Autowired
    private UsuarioService usuarioService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    // CRUD

    @Transactional
    public Hechizo crearHechizo(String tipo, int poder) {
        // Crear el hechizo usando el factory
        Hechizo hechizo = hechizoFactoryService.crearHechizo(tipo);

        // Establecer el nombre del hechizo (asegúrate de que se use el nombre correcto)
        hechizo.setNombre(tipo);  // Asignar el nombre basado en el tipo

        // Asignar el poder al hechizo
        hechizo.setPoder(poder);

        // Guardar el hechizo en la base de datos
        return hechizoRepository.save(hechizo);
    }

    @Transactional
    public Hechizo obtenerHechizoPorId(Long id) {
        return hechizoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hechizo no encontrado"));
    }

    @Transactional
    public List<Hechizo> obtenerTodosLosHechizos() {
        return hechizoRepository.findAll();
    }

    @Transactional
    public Hechizo actualizarHechizo(Long id, Hechizo hechizoActualizado) {
        Hechizo hechizo = hechizoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hechizo no encontrado"));

        hechizo.setNombre(hechizoActualizado.getNombre());
        hechizo.setPoder(hechizoActualizado.getPoder());

        return hechizoRepository.save(hechizo);
    }

    @Transactional
    public void eliminarHechizo(Long id) {
        hechizoRepository.deleteById(id);
    }

    /**
     * Metodo para verificar si el usuario tiene permiso para lanzar el hechizo
     *
     * @param usuario El usuario que intenta lanzar el hechizo.
     * @param hechizo El hechizo a lanzar.
     * @return true si el usuario tiene permiso, de lo contrario lanza una excepción de seguridad.
     */
    public boolean verificarPermisos(Usuario usuario, Hechizo hechizo) {
        if (hechizo.esPermitido(usuario)) {
            return true;
        } else {
            throw new SecurityException("El usuario no tiene permisos para ejecutar este hechizo.");
        }
    }

    /**
     * Metodo para lanzar un hechizo, unifica la verificación de permisos, manejo de concurrencia y ejecución asíncrona.
     *
     * @param usuario  El usuario que lanza el hechizo.
     * @param hechizo  El hechizo a lanzar.
     * @return Un Future que contiene el resultado de la ejecución del hechizo.
     */
    @Async
    @Transactional
    public Future<String> lanzarHechizo(Usuario usuario, Hechizo hechizo) {
        // Verificar permisos antes de lanzar el hechizo
        verificarPermisos(usuario, hechizo);

        // Ejecutar el hechizo de forma concurrente, manejando la ejecución en un thread pool
        return executorService.submit(() -> {
            synchronized (this) {
                System.out.println("Lanzando hechizo: " + hechizo.getNombre());
                hechizo.ejecutar();
                eventoMagicoService.registrarEventoMagico(usuario, hechizo);

                if (hechizo.esPermitido(usuario)){
                registroService.registrarAccion(usuario, "Lanzar Hechizo", "Realizado");
                usuarioService.aumentarPoderUsuario(usuario.getId(), 1);
                }
                else {
                    registroService.registrarAccion(usuario, "Lanzar Hechizo", "Intento fallido");
                }

                return "Hechizo " + hechizo.getNombre() + " ejecutado correctamente.";
            }
        });
    }
}

