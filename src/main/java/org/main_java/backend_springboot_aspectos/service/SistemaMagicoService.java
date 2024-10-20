package org.main_java.backend_springboot_aspectos.service;

import org.main_java.backend_springboot_aspectos.domain.EventoMagico;
import org.main_java.backend_springboot_aspectos.domain.Usuario;
import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;
import org.main_java.backend_springboot_aspectos.repos.EventoMagicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SistemaMagicoService {

    @Autowired
    private HechizoService hechizoService;

    @Autowired
    private EventoMagicoService eventoMagicoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EventoMagicoRepository eventoMagicoRepository;

    @Autowired
    private AuditoriaService auditoriaService;

    public void lanzarHechizo(Usuario usuario, Hechizo hechizo) {
        hechizoService.lanzarHechizo(usuario, hechizo);
    }

    public void registrarEventoMagico(Usuario usuario,  Hechizo hechizo) {
        eventoMagicoService.registrarEventoMagico(usuario, hechizo);
    }

    public void auditarEventoMagico(EventoMagico evento) {
        auditoriaService.auditarEvento(evento);
    }

    public EventoMagico obtenerEventoPorId(Long eventoId) {
        return eventoMagicoRepository.findById(eventoId).orElse(null);
    }

}
