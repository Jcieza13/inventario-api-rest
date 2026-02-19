# Inventory System API

Backend REST API desarrollada como proyecto de portafolio orientado a demostrar competencias profesionales en desarrollo backend con Java y Spring Boot.

El sistema simula un entorno real de producción incluyendo autenticación JWT, control de roles, persistencia en base de datos relacional y testing (unitario e integración).

El enfoque principal del proyecto es aplicar buenas prácticas de arquitectura limpia, separación de responsabilidades y seguridad en APIs.


---

## 🚀 Características

* Autenticación y autorización con JWT
* Registro de usuarios y administradores
* Control de roles (ROLE_ADMIN / ROLE_USER)
* CRUD completo de productos
* Búsqueda de productos por nombre
* Validaciones con Jakarta Validation
* Arquitectura por capas
* Manejo de DTOs para desacoplar entidades
* Pruebas unitarias con Mockito
* Pruebas de integración con SpringBootTest
* Persistencia en MySQL
* Colección pública de Postman para pruebas en vivo

---

## 🧱 Arquitectura

El proyecto sigue arquitectura en capas:

Controller → Service → Repository → Database

Separación de responsabilidades:

* **Controller:** Expone endpoints REST
* **Service:** Contiene la lógica de negocio
* **Repository:** Acceso a datos con JPA
* **DTO:** Transferencia segura de datos
* **Security:** Manejo de autenticación y autorización

Estructura principal:

```
application
 ├── dto
 ├── service
domain
 ├── model
 ├── service
infrastructure
 ├── repository
 ├── security
 ├── exception
```

---

## 🛠️ Tecnologías utilizadas

* Java 17
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* MySQL
* Lombok
* Mockito
* JUnit 5
* Maven

---

## 🔐 Autenticación

El sistema utiliza JWT para proteger los endpoints.

Flujo:

1. Usuario inicia sesión
2. El servidor genera token
3. El cliente envía token en cada request
4. Spring Security valida permisos

Header requerido:

```
Authorization: Bearer {token}
```

---

## 📦 Endpoints principales

### Auth

| Método | Endpoint           | Descripción       |
| ------ | ------------------ | ----------------- |
| POST   | /api/auth/register | Registrar usuario |
| POST   | /api/auth/login    | Iniciar sesión    |

### Productos

| Método | Endpoint                   | Descripción         |
| ------ | -------------------------- | ------------------- |
| GET    | /api/products              | Obtener todos       |
| GET    | /api/products/{id}         | Obtener por id      |
| GET    | /api/products/search?name= | Buscar por nombre   |
| POST   | /api/products              | Crear producto      |
| PUT    | /api/products/{id}         | Actualizar producto |
| DELETE | /api/products/{id}         | Eliminar producto   |

---

## 🌐 Probar la API (Postman)

Puedes probar todos los endpoints directamente desde la colección pública de Postman:

🔗 **Colección pública:**
[https://www.postman.co/workspace/My-Workspace~9c519b4c-0445-4870-8b3a-9f7df90ac94c/collection/25646862-71998931-ff1a-49b0-8736-306e93d6cccc?action=share&creator=25646862](https://www.postman.co/workspace/My-Workspace~9c519b4c-0445-4870-8b3a-9f7df90ac94c/collection/25646862-71998931-ff1a-49b0-8736-306e93d6cccc?action=share&creator=25646862)

### Flujo recomendado de pruebas

1. Registrar usuario
2. Iniciar sesión
3. Copiar el token JWT generado
4. Probar endpoints protegidos de productos

Si ejecutas el proyecto localmente, asegúrate de que la variable:

```
base_url = http://localhost:8080
```

---

## 🧪 Testing

### Pruebas Unitarias

Validan la lógica de negocio aislada:

* ProductService
* AuthService

Ubicación de evidencias:

```
doc/images/unit-tests/
```

### Pruebas de Integración

Validan el flujo completo:

Controller → Service → Repository → Database

Ubicación de evidencias:

```
doc/images/integration-tests/
```

Ejecutar pruebas:

```bash
mvn test
```

---

## 🗄️ Base de Datos

La API se conecta a MySQL y persiste datos reales.

Capturas disponibles en:

```
doc/images/database/
```

---

## ▶️ Ejecución del Proyecto

```bash
mvn clean install
mvn spring-boot:run
```

La aplicación inicia en:

```
http://localhost:8080
```

---

## 📚 Lo aprendido

Durante este proyecto se aplicaron conceptos fundamentales de backend profesional:

* Diseño de APIs REST seguras
* Manejo de autenticación con tokens
* Separación de capas
* Testing automatizado
* Manejo de excepciones
* Validación de datos
* Persistencia con JPA
* Estructuración profesional para portafolio

El objetivo principal fue construir un backend realista similar a uno utilizado en entornos empresariales.

---

## 👨‍💻 Autor

Desarrollado por Jen Pierr

Proyecto orientado a demostrar habilidades backend en construcción de APIs seguras, arquitectura en capas y testing utilizando el ecosistema Spring.








