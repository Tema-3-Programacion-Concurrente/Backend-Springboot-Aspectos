package org.main_java.backend_springboot_aspectos.service;

import org.main_java.backend_springboot_aspectos.domain.Credenciales;
import org.main_java.backend_springboot_aspectos.domain.Rol;
import org.main_java.backend_springboot_aspectos.domain.Usuario;
import org.main_java.backend_springboot_aspectos.model.AuthResponseDTO;
import org.main_java.backend_springboot_aspectos.model.LoginRequestDTO;
import org.main_java.backend_springboot_aspectos.model.RegisterRequestDTO;
import org.main_java.backend_springboot_aspectos.repos.RolRepository;
import org.main_java.backend_springboot_aspectos.repos.UsuarioRepository;
import org.main_java.backend_springboot_aspectos.repos.CredencialesRepository;
import org.main_java.backend_springboot_aspectos.util.UserValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CredencialesRepository credencialesRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RegistroService registroService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ResponseEntity<AuthResponseDTO> login(LoginRequestDTO loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(loginRequest.getCorreo());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(loginRequest.getContrasena(), usuario.getUsuario().getPassword())) {
                String rol = usuario.getUsuarios().getNombre();

                registroService.registrarAccion(usuario, "Login", "EXITO");

                return ResponseEntity.ok(new AuthResponseDTO("Login exitoso", "FAKE_JWT_TOKEN", rol));
            } else {
                registroService.registrarAccion(usuario, "Login", "FALLIDO");

                return ResponseEntity.status(401).body(new AuthResponseDTO("Credenciales incorrectas", null, null));
            }
        }

        return ResponseEntity.status(404).body(new AuthResponseDTO("Usuario no encontrado", null, null));
    }

    @Transactional
    public ResponseEntity<AuthResponseDTO> register(RegisterRequestDTO registerRequest, String rolNombre) {
        // Validar entrada
        validateUserInput(registerRequest);

        // Verificar si el usuario ya existe
        if (usuarioRepository.findByCorreo(registerRequest.getCorreo()).isPresent()) {
            return ResponseEntity.status(400).body(new AuthResponseDTO("El usuario ya existe", null, null));
        }

        // Validar rol
        if (!"admin".equalsIgnoreCase(rolNombre) && !"user".equalsIgnoreCase(rolNombre) && !"researcher".equalsIgnoreCase(rolNombre)) {
            return ResponseEntity.status(400).body(new AuthResponseDTO("Rol no válido. Use 'admin' o 'user'.", null, null));
        }

        // Buscar o crear rol
        Optional<Rol> rolOpt = rolRepository.findByNombre(rolNombre);
        Rol rol = rolOpt.orElseGet(() -> {
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre(rolNombre);
            return rolRepository.save(nuevoRol);
        });

        // Crear credenciales
        Credenciales credenciales = new Credenciales();
        credenciales.setUsername(registerRequest.getCorreo());
        credenciales.setPassword(passwordEncoder.encode(registerRequest.getContrasena()));
        credencialesRepository.save(credenciales);

        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registerRequest.getNombre());
        nuevoUsuario.setApellido1(registerRequest.getApellido1());
        nuevoUsuario.setApellido2(registerRequest.getApellido2());
        nuevoUsuario.setCorreo(registerRequest.getCorreo());
        nuevoUsuario.setTelefono(registerRequest.getTelefono());
        nuevoUsuario.setDireccion(registerRequest.getDireccion());
        nuevoUsuario.setUsuario(credenciales);
        nuevoUsuario.setUsuarios(rol);
        nuevoUsuario.setPoder(registerRequest.getPoder());

        usuarioRepository.save(nuevoUsuario);

        registroService.registrarAccion(nuevoUsuario, "REGISTRO", "EXITO AL REGISTRARSE");

        return ResponseEntity.ok(new AuthResponseDTO("Usuario registrado con éxito", null, rolNombre));
    }

    private void validateUserInput(RegisterRequestDTO registerRequest) {
        // Validaciones de nombres
        if (registerRequest.getNombre() == null || !registerRequest.getNombre().matches("[A-Za-záéíóúÁÉÍÓÚñÑ]+")) {
            throw new UserValidationException("El nombre debe contener solo letras.");
        }

        if (registerRequest.getApellido1() == null || !registerRequest.getApellido1().matches("[A-Za-záéíóúÁÉÍÓÚñÑ]+")) {
            throw new UserValidationException("El primer apellido debe contener solo letras.");
        }

        if (registerRequest.getApellido2() == null || !registerRequest.getApellido2().matches("[A-Za-záéíóúÁÉÍÓÚñÑ]+")) {
            throw new UserValidationException("El segundo apellido debe contener solo letras.");
        }

        // Validaciones de correo
        if (registerRequest.getCorreo() == null || !registerRequest.getCorreo().matches("^[\\w-.]+@(gmail\\.com|hotmail\\.com|yahoo\\.com)$")) {
            throw new UserValidationException("El correo debe ser de una extensión válida (@gmail.com, @hotmail.com, @yahoo.com).");
        }

        // Validaciones de teléfono
        Integer telefono = registerRequest.getTelefono();
        if (telefono == null || !isValidPhoneNumber(telefono)) {
            throw new UserValidationException("El teléfono debe contener exactamente 9 dígitos numéricos.");
        }

        // Validaciones de contraseña
        String contrasena = registerRequest.getContrasena();
        if (!isValidPassword(contrasena)) {
            throw new UserValidationException("La contraseña debe contener al menos una letra, un número y un carácter especial.");
        }

        int poderUser = registerRequest.getPoder();
        if (poderUser < 0 ){
            throw new UserValidationException("El poder no puede ser inferior a cero.");
        }
    }

    private boolean isValidPhoneNumber(Integer phoneNumber) {
        String phoneStr = phoneNumber.toString();
        return phoneStr.length() == 9 && phoneStr.chars().allMatch(Character::isDigit);
    }

    private boolean isValidPassword(String password) {
        // Verificar que la contraseña contenga al menos una letra, un número y un carácter especial
        return password != null && password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,}$");
    }
}
