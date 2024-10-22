package org.main_java.backend_springboot_aspectos;

import org.main_java.backend_springboot_aspectos.model.RegisterRequestDTO;
import org.main_java.backend_springboot_aspectos.service.AuthService;
import org.main_java.backend_springboot_aspectos.service.HechizoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "org.main_java.backend_springboot_aspectos")
public class Backend_Springboot_Aspectos_main {

    public static void main(String[] args) {
        SpringApplication.run(Backend_Springboot_Aspectos_main.class, args);
    }

    @Bean
    CommandLineRunner initUsers(AuthService authService, HechizoService hechizoService) {
        return args -> {

            // Registro de usuarios
            registrarNuevoUsuario(
                    authService,
                    "Administrador", "ApellidoA", "ApellidoB", "admin@gmail.com", 123456789,
                    "Calle Principal 123", "a12345_67", "admin", 100
            );

            registrarNuevoUsuario(
                    authService,
                    "Usuario", "ApellidoCC", "ApellidoDD", "usuario@gmail.com", 555666777,
                    "Avenida Tercera 789", "a12345_679", "user", 45
            );

            // Creación de hechizos utilizando HechizoFactory
            crearHechizo(hechizoService, "fuego", 50);
            crearHechizo(hechizoService, "agua", 40);
            crearHechizo(hechizoService, "roca", 60);
            crearHechizo(hechizoService, "aire", 30);
            crearHechizo(hechizoService, "electro", 200);
        };
    }

    private void registrarNuevoUsuario(AuthService authService, String nombre, String apellido1, String apellido2,
                                       String correo, int telefono, String direccion, String contrasena, String rolNombre, int poder) {

        // Crear el objeto RegisterRequest con la información del usuario
        RegisterRequestDTO registerRequest = new RegisterRequestDTO();
        registerRequest.setNombre(nombre);
        registerRequest.setApellido1(apellido1);
        registerRequest.setApellido2(apellido2);
        registerRequest.setCorreo(correo);
        registerRequest.setTelefono(telefono);
        registerRequest.setDireccion(direccion);
        registerRequest.setContrasena(contrasena);
        registerRequest.setRole(rolNombre);
        registerRequest.setPoder(poder);

        authService.register(registerRequest, rolNombre);

        System.out.println("Usuario registrado con nombre: " + nombre + " y rol: " + rolNombre);
    }

    private void crearHechizo(HechizoService hechizoService, String tipoHechizo, int poder) {
        // Crear un hechizo utilizando el factory y el tipo de hechizo
        hechizoService.crearHechizo(tipoHechizo, poder);

        System.out.println("Hechizo de " + tipoHechizo + " creado con poder: " + poder);
    }
}
