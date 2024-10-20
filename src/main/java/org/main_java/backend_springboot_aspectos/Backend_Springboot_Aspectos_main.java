package org.main_java.backend_springboot_aspectos;

import org.main_java.backend_springboot_aspectos.domain.hechizos.*;
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
            // Creación de hechizos concretos
            crearHechizoFuego(hechizoService, "Fuego", 50);
            crearHechizoAgua(hechizoService, "Agua", 40);
            crearHechizoRoca(hechizoService, "Roca", 60);
            crearHechizoAire(hechizoService, "Aire", 30);
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

    private void crearHechizoFuego(HechizoService hechizoService, String nombre, int poder) {
        // Crear un hechizo de fuego
        HechizoFuego hechizoFuego = new HechizoFuego();
        hechizoFuego.setNombre(nombre);
        hechizoFuego.setPoder(poder);

        // Guardar el hechizo en la base de datos a través del servicio de hechizos
        hechizoService.crearHechizo(hechizoFuego);

        System.out.println("Hechizo de fuego creado: " + nombre + " con poder: " + poder);
    }

    private void crearHechizoAgua(HechizoService hechizoService, String nombre, int poder) {
        // Crear un hechizo de hielo
        HechizoAgua hechizoAgua = new HechizoAgua();
        hechizoAgua.setNombre(nombre);
        hechizoAgua.setPoder(poder);

        // Guardar el hechizo en la base de datos a través del servicio de hechizos
        hechizoService.crearHechizo(hechizoAgua);

        System.out.println("Hechizo de Agua creado: " + nombre + " con poder: " + poder);
    }

    private void crearHechizoRoca(HechizoService hechizoService, String nombre, int poder) {
        // Crear un hechizo de relámpago
        HechizoRoca hechizoRoca = new HechizoRoca();
        hechizoRoca.setNombre(nombre);
        hechizoRoca.setPoder(poder);

        // Guardar el hechizo en la base de datos a través del servicio de hechizos
        hechizoService.crearHechizo(hechizoRoca);

        System.out.println("Hechizo de Roca creado: " + nombre + " con poder: " + poder);
    }

    private void crearHechizoAire(HechizoService hechizoService, String nombre, int poder) {
        // Crear un hechizo de curación
        HechizoAire hechizoAire = new HechizoAire();
        hechizoAire.setNombre(nombre);
        hechizoAire.setPoder(poder);

        // Guardar el hechizo en la base de datos a través del servicio de hechizos
        hechizoService.crearHechizo(hechizoAire);

        System.out.println("Hechizo de Aire creado: " + nombre + " con poder: " + poder);
    }
}

