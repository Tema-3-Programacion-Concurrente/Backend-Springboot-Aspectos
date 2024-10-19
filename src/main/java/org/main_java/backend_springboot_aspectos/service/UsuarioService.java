package org.main_java.backend_springboot_aspectos.service;

import org.main_java.backend_springboot_aspectos.domain.Credenciales;
import org.main_java.backend_springboot_aspectos.domain.Rol;
import org.main_java.backend_springboot_aspectos.domain.Usuario;
import org.main_java.backend_springboot_aspectos.model.UsuarioDTO;
import org.main_java.backend_springboot_aspectos.repos.UsuarioRepository;
import org.main_java.backend_springboot_aspectos.repos.RolRepository;
import org.main_java.backend_springboot_aspectos.repos.CredencialesRepository;
import org.main_java.backend_springboot_aspectos.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final CredencialesRepository credencialesRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository,
                          final RolRepository rolRepository,
                          final CredencialesRepository credencialesRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.credencialesRepository = credencialesRepository;
    }

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll(Sort.by("id")).stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .collect(Collectors.toList());
    }

    public UsuarioDTO get(final Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }

    public boolean usuarioExists(final Long id) {
        return usuarioRepository.existsById(id);
    }

    public Long create(final UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getId();
    }

    public void update(final Long id, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Long id) {
        usuarioRepository.deleteById(id);
    }

    // Metodo para incrementar el poder del usuario
    public void aumentarPoderUsuario(Long id, int incremento) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        usuario.aumentarPoder(incremento);
        usuarioRepository.save(usuario);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido1(usuario.getApellido1());
        usuarioDTO.setApellido2(usuario.getApellido2());
        usuarioDTO.setCorreo(usuario.getCorreo());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setDireccion(usuario.getDireccion());
        usuarioDTO.setRolId(usuario.getUsuarios() == null ? null : usuario.getUsuarios().getId());
        usuarioDTO.setDateCreated(usuario.getDateCreated());
        usuarioDTO.setPoder(usuario.getPoder()); // Mapear el nuevo campo de poder
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido1(usuarioDTO.getApellido1());
        usuario.setApellido2(usuarioDTO.getApellido2());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setDireccion(usuarioDTO.getDireccion());

        // Manejo del rol y credenciales
        usuario.setPoder(usuarioDTO.getPoder()); // Mapear el nuevo campo de poder
        return usuario;
    }
}
