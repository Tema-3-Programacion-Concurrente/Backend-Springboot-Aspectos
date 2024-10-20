package org.main_java.backend_springboot_aspectos.model;

import lombok.Getter;
import lombok.Setter;
import org.main_java.backend_springboot_aspectos.domain.Usuario;

@Getter
@Setter
public class AuthResponseDTO {
    private String message;
    private String token;
    private String role;
    private Long userId; // Campo userId explícito
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String correo;
    private int telefono;
    private String direccion;
    private int poder;

    // Constructor actualizado
    public AuthResponseDTO(String message, String token, String role, Long userId, Usuario usuario) {
        this.message = message;
        this.token = token;
        this.role = role;
        this.userId = userId; // Asignar el userId directamente

        // Asignar los campos del usuario si no es nulo
        if (usuario != null) {
            this.nombre = usuario.getNombre();
            this.apellido1 = usuario.getApellido1();
            this.apellido2 = usuario.getApellido2();
            this.correo = usuario.getCorreo();
            this.telefono = usuario.getTelefono();
            this.direccion = usuario.getDireccion();
            this.poder = usuario.getPoder();
        }
    }
}
