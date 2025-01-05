# API de Gestión de Calidad Textil
Sistema de gestión y control de calidad textil que permite el registro, seguimiento y análisis de pruebas de calidad en materiales textiles.

## Características Principales

- 🔐 Autenticación y autorización basada en JWT
- 📊 Gestión de pruebas de calidad textil
- 👥 Administración de usuarios y roles
- 📈 Reportes y estadísticas de calidad
- 🔍 Trazabilidad de pruebas realizadas

## Tecnologías

- Java 17
- Spring Boot 3.x
- Spring Security
- JWT
- MySQL
- Maven

## Inicio Rápido

### Clonar el repositorio
```bash
git clone https://github.com/DavidM444/Api-Java-Tex.git
```
### Configurar base de datos

- Editar application.properties con tus credenciales

### Ejecuta la Aplicacion
```bash
./mvnw spring-boot:run
```


## API Endpoints

- `POST /login` - Autenticación de usuarios
- `GET /registro` - Listar registros de calidad
- `POST /registro` - Crear nuevo registro
- `PUT /registro` - Actualizar registro existente

## Seguridad

- Autenticación mediante JWT
- Roles: ADMIN y USER
- Endpoints protegidos por rol

## Contacto

Rodrigo David Muñoz  
📧 ngxdavid050@gmail.com  
