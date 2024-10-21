# Backend-Springboot-Aspectos

# LINKS

FRONTEND: https://github.com/Tema-3-Programacion-Concurrente/Frontend-React-Practica_3.git

BACKEND: https://github.com/Tema-3-Programacion-Concurrente/Backend-Springboot-Aspectos.git


# PARTICIPANTES

Jaime López Díaz

Nicolás Jiménez

Marcos García Benito Bilbao


# Sistema de Gestión Mágica
Un sistema avanzado para la gestión de hechizos y eventos mágicos utilizando AOP con Spring Boot.

## Tabla de Contenidos
- Introducción
- Descripción General de los Servicios
  - Funcionalidad
  - Características Clave
- Implementación de Concurrencia
  - Tipos de Concurrencia Utilizados
  - Razones para Implementar Concurrencia
  - Concurrencia en los Servicios
- Descripción General de los Aspectos
- Patrones Utilizados

## Introducción
El Ministerio de Magia está desarrollando un sistema avanzado para la gestión de hechizos y eventos mágicos utilizando la Programación Orientada a Aspectos (AOP). Este sistema maneja diversas preocupaciones transversales, como la seguridad, la auditoría y la gestión de transacciones, asegurando la eficiencia y la capacidad de respuesta. Utilizando AOP con Spring, se ha implementado un sistema modular que permite la integración y gestión de diferentes módulos de manera eficiente y segura.

## Extras Importantes
- **Usuarios Predefinidos**: El sistema incluye usuarios y administradores predefinidos con sus propias credenciales para facilitar las pruebas en el frontend.
- **Casos de Uso**: Los casos de uso de cada página se reflejan en el frontend, proporcionando una interfaz intuitiva para los usuarios.

## Descripción General de los Servicios

### Funcionalidad
- **Autenticación y Autorización**: Gestión de usuarios y roles, asegurando que solo usuarios autorizados puedan acceder a ciertas funcionalidades.
- **Gestión de Hechizos**: Creación, actualización, eliminación y ejecución de diferentes tipos de hechizos.
- **Registro de Eventos Mágicos**: Registro de eventos mágicos generados por los usuarios.
- **Auditoría**: Auditoría de eventos mágicos para asegurar la transparencia y seguridad del sistema.

### Características Clave
- **UsuarioService**:
  - **Funcionalidad**: Gestión completa de usuarios, incluyendo creación, actualización, eliminación y obtención de usuarios.
  - **Características Clave**: Aumento del poder del usuario, mapeo entre entidades y DTOs, y verificación de existencia de usuarios.

- **SistemaMagicoService**:
  - **Funcionalidad**: Gestión de hechizos, registro de eventos mágicos y auditoría de eventos.
  - **Características Clave**: Integración con otros servicios para lanzar hechizos, registrar y auditar eventos mágicos.

- **SeguridadService**:
  - **Funcionalidad**: Verificación de permisos de usuarios para ejecutar hechizos.
  - **Características Clave**: Uso de `Lock` para asegurar la concurrencia en la verificación de acceso.

- **RegistroService**:
  - **Funcionalidad**: Registro de acciones de usuarios de manera concurrente y asíncrona.
  - **Características Clave**: Uso de `CompletableFuture`, `ExecutorService` y `Lock` para manejar la concurrencia y sincronización.

- **HechizoService**:
  - **Funcionalidad**: Gestión completa de hechizos, incluyendo creación, obtención, actualización y eliminación.
  - **Características Clave**: Verificación de permisos, ejecución de hechizos de manera concurrente y registro de acciones.

- **EventoMagicoService**:
  - **Funcionalidad**: Gestión de eventos mágicos, incluyendo creación, obtención, actualización y eliminación.
  - **Características Clave**: Registro de eventos mágicos de manera concurrente y mapeo entre entidades y DTOs.

- **AuthService**:
  - **Funcionalidad**: Manejo de autenticación y registro de usuarios.
  - **Características Clave**: Validación de entrada de usuarios, verificación de credenciales y registro de acciones de autenticación.

- **AuditoriaService**:
  - **Funcionalidad**: Auditoría de eventos mágicos.
  - **Características Clave**: Registro de auditorías de manera concurrente y mapeo entre entidades y DTOs.

## Implementación de Concurrencia

### Tipos de Concurrencia Utilizados
- **Programación Asíncrona con @Async**: Permite ejecutar métodos de forma asíncrona en un hilo separado.
- **CompletableFuture**: Facilita el manejo de tareas asíncronas y la composición de operaciones.
- **ExecutorService y ThreadPool**: Gestionan un pool de hilos para ejecutar tareas concurrentes.
- **Sincronización con synchronized**: Asegura que solo un hilo acceda a un bloque de código crítico a la vez.

### Razones para Implementar Concurrencia
- **Mejorar el Rendimiento**: Procesar múltiples muestras y experimentos en paralelo reduce el tiempo total de ejecución.
- **Escalabilidad**: La aplicación puede manejar grandes volúmenes de datos sin afectar la capacidad de respuesta.
- **Eficiencia en el Uso de Recursos**: Aprovecha al máximo los recursos del sistema al distribuir la carga de trabajo entre múltiples hilos.
- **Respuesta Rápida al Usuario**: Las operaciones intensivas se ejecutan en segundo plano, manteniendo la interfaz de usuario receptiva.

### Concurrencia en los Servicios
- **UsuarioService**:
  - **Aumento del Poder del Usuario**: Utiliza métodos sincronizados para asegurar que solo un hilo pueda modificar el poder del usuario a la vez, evitando inconsistencias en los datos.

- **SistemaMagicoService**:
  - **Lanzar Hechizos y Registrar Eventos**: Utiliza `ExecutorService` para manejar la ejecución concurrente de hechizos y el registro de eventos mágicos, asegurando que múltiples hechizos puedan ser lanzados y registrados simultáneamente sin bloquear el sistema.

- **SeguridadService**:
  - **Verificación de Permisos**: Utiliza un `Lock` para asegurar que la verificación de permisos se realice de manera segura y concurrente, evitando condiciones de carrera.

- **RegistroService**:
  - **Registro de Acciones**: Utiliza `CompletableFuture` y `ExecutorService` para registrar acciones de manera asíncrona, permitiendo que las acciones de los usuarios se registren sin bloquear el flujo principal de la aplicación.
  - **Sincronización**: Utiliza `Lock` para asegurar que solo un hilo pueda registrar una acción a la vez, evitando inconsistencias en los registros.

- **HechizoService**:
  - **Ejecución de Hechizos**: Utiliza `ExecutorService` para ejecutar hechizos de manera concurrente, permitiendo que múltiples hechizos se ejecuten simultáneamente.
  - **Verificación de Permisos**: Utiliza métodos sincronizados para asegurar que la verificación de permisos se realice de manera segura y concurrente.

- **EventoMagicoService**:
  - **Registro de Eventos Mágicos**: Utiliza `CompletableFuture` y `ExecutorService` para registrar eventos mágicos de manera concurrente, asegurando que múltiples eventos puedan ser registrados simultáneamente sin bloquear el sistema.
  - **Sincronización**: Utiliza `Lock` para asegurar que solo un hilo pueda registrar un evento a la vez, evitando inconsistencias en los registros.

- **AuthService**:
  - **Registro de Usuarios**: Utiliza métodos sincronizados para asegurar que el registro de usuarios se realice de manera segura y concurrente, evitando condiciones de carrera.

- **AuditoriaService**:
  - **Auditoría de Eventos**: Utiliza `ExecutorService` para auditar eventos de manera concurrente, permitiendo que múltiples auditorías se realicen simultáneamente sin bloquear el sistema.
  - **Sincronización**: Utiliza `Lock` para asegurar que solo un hilo pueda auditar un evento a la vez, evitando inconsistencias en los registros de auditoría.

## Descripción General de los Aspectos
- **Seguridad**: Verificación de permisos antes de ejecutar hechizos utilizando aspectos.
  - **Antes de Lanzar un Hechizo**: Se verifica si el usuario tiene permiso para lanzar el hechizo mediante el aspecto `SeguridadAspecto`.
- **Auditoría**: Registro de eventos mágicos y acciones de usuarios utilizando aspectos.
  - **Después de Lanzar un Hechizo**: Se registra la acción de lanzar el hechizo mediante el aspecto `RegistroAspecto`.
  - **Antes de Auditar un Hechizo**: Se realiza una acción preparatoria antes de auditar el hechizo mediante el aspecto `AuditoriaAspecto`.
- **Registro de Acciones**: Utilización de aspectos para registrar las acciones de los usuarios.
  - **Después de Lanzar un Hechizo**: Se registra la acción de lanzar el hechizo utilizando `RegistroAspecto`.
- **Gestión de Transacciones**: Manejo de transacciones alrededor de métodos críticos para asegurar la consistencia de los datos.
  - **Interceptar Métodos en Servicios**: Se interceptan métodos dentro del paquete de servicios para realizar acciones antes y después de la ejecución de los métodos, gestionando las transacciones de manera eficiente mediante el aspecto `TransaccionAspecto`.

## Patrones Utilizados
- **Data Transfer Object (DTO)**: Separa la capa de presentación de la lógica de negocio y las entidades de dominio.
- **Repository Pattern**: Aísla la capa de acceso a datos, permitiendo intercambiar fácilmente la implementación de persistencia.
- **Service Layer**: Centraliza la lógica de negocio y coordina las operaciones entre repositorios y controladores.
- **Singleton**: Implementado implícitamente en algunos servicios para asegurar una única instancia gestionada por el contenedor de Spring.
- **Factory Pattern**: Utilizado en `HechizoFactoryService` para crear instancias de diferentes tipos de hechizos.
  - **HechizoFactoryService**:
    - **obtenerHechizo**: Busca un hechizo por su nombre en el repositorio. Si no se encuentra, lanza una excepción.
    - **crearHechizo**: Crea una instancia de un hechizo basado en su tipo utilizando un `switch` para determinar el tipo de hechizo a crear. Este método asegura que se cree el tipo correcto de hechizo según el nombre proporcionado, encapsulando la lógica de creación y simplificando la gestión de diferentes tipos de hechizos.

