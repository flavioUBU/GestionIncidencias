# Gestión de Incidencias

Aplicación web desarrollada para la asignatura de **Sistemas Distribuidos**.  
El proyecto permite autenticación segura, gestión de usuarios, gestión de incidencias, integración con una API externa en Flask, chat en tiempo real con WebSocket, mensajería asíncrona con RabbitMQ y despliegue con Docker.

---

## Tecnologías utilizadas

- **Java 24**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **Thymeleaf**
- **Bootstrap**
- **MySQL**
- **Python / Flask**
- **RabbitMQ**
- **WebSocket + STOMP + SockJS**
- **Docker / Docker Compose**
- **GitHub Actions**
- **SonarQube Cloud**

---

## Funcionalidades principales

### 1. Gestión de usuarios
La aplicación permite:
- crear usuarios
- listar usuarios
- editar usuarios
- borrar usuarios

Los usuarios tienen roles:
- `ROLE_ADMIN`
- `ROLE_USER`

Además, las contraseñas se almacenan cifradas con **BCrypt**.

### 2. Gestión de incidencias
La aplicación permite:
- crear incidencias
- listar incidencias
- editar incidencias
- borrar incidencias

Cada incidencia se asocia automáticamente al usuario autenticado.

### 3. Seguridad
Se ha implementado seguridad con **Spring Security**:
- login
- logout
- control de acceso según rol
- cifrado de contraseñas con **BCrypt**

### 4. API externa con Flask
La aplicación Spring se comunica con una API desarrollada en Flask para probar:
- llamada simple
- error de archivo
- error de base de datos
- consulta correcta a base de datos
- error real de base de datos
- consulta a la API de Pokémon
- error de Pokémon no encontrado

### 5. Chat en tiempo real
Se ha implementado un chat en tiempo real mediante:
- **WebSocket**
- **STOMP**
- **SockJS**

El nombre del usuario se obtiene automáticamente del usuario autenticado en la sesión.

### 6. RabbitMQ
Cuando se crea una incidencia:
- se envía un mensaje a una cola RabbitMQ
- un consumidor recibe ese mensaje y lo procesa

Esto permite demostrar mensajería asíncrona entre componentes.

### 7. Interfaz web
La interfaz se ha desarrollado con:
- **Thymeleaf**
- **Bootstrap**

Se han mejorado:
- página principal
- formularios
- listados
- login
- pantalla de pruebas de API
- chat

---

## Estructura del proyecto

```text
gestion-incidencias/
├── api_flask/                  # API externa en Flask
│   ├── app.py
│   ├── Dockerfile
│   └── requirements.txt
├── src/
│   ├── main/
│   │   ├── java/com/sistemasdistr/basico/
│   │   │   ├── config/         # Seguridad, WebSocket, RabbitMQ
│   │   │   ├── controller/     # Controladores web
│   │   │   ├── dto/            # DTOs
│   │   │   ├── model/          # Entidades
│   │   │   ├── repository/     # Acceso a datos
│   │   │   └── service/        # Lógica de negocio
│   │   └── resources/
│   │       ├── templates/      # Vistas Thymeleaf
│   │       └── application.properties
├── .github/
│   └── workflows/              # GitHub Actions
├── Dockerfile                  # Dockerfile de Spring Boot
├── docker-compose.yml
├── pom.xml
└── README.md
