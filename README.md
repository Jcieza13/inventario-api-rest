# inventario-api-rest

## Descripción

**inventario-api-rest** es una **API REST para la gestión de inventario**, desarrollada con **Spring Boot**, **JPA** y **MySQL**.  
Permite la creación, lectura, actualización y eliminación de productos, ofreciendo un backend completo para integrarse con cualquier frontend moderno (Angular, React, etc.).  

Este proyecto fue diseñado para demostrar competencias en **desarrollo backend**, **API REST**, y manejo de bases de datos relacionales, siguiendo buenas prácticas de **programación y arquitectura**.

---

## Tecnologías utilizadas

- **Lenguaje:** Java 17  
- **Framework Backend:** Spring Boot  
- **Persistencia:** JPA (Hibernate)  
- **Base de datos:** MySQL  
- **Gestión de dependencias:** Maven  
- **Control de versiones:** Git / GitHub  
- **Seguridad:** Spring Security + JWT (opcional según versión)  

---

## Funcionalidades principales

La API permite:

1. **Listar productos**
   - `GET /inventario-app/productos`
2. **Obtener producto por ID**
   - `GET /inventario-app/productos/{id}`
3. **Agregar un nuevo producto**
   - `POST /inventario-app/productos`
4. **Actualizar un producto existente**
   - `PUT /inventario-app/productos/{id}`
5. **Eliminar un producto**
   - `DELETE /inventario-app/productos/{id}`

Cada producto contiene:

| Campo        | Tipo       | Descripción                       |
|-------------|-----------|----------------------------------|
| `idProducto` | Integer   | Identificador único del producto |
| `descripcion` | String   | Nombre o descripción del producto |
| `precio`     | Double    | Precio del producto              |
| `existencia` | Integer   | Stock disponible                 |

---

## Instalación

1. Clona el repositorio:

```bash
git clone https://github.com/Jcieza13/inventario-api-rest.git
```

2. Ingresa a la carpeta del proyecto backend:
   ```bash
   cd inventario-api-rest
   ```
3. Configura tu base de datos MySQL en src/main/resources/application.properties:
   spring.datasource.url=jdbc:mysql://localhost:3306/inventario_db
   spring.datasource.username=TU_USUARIO
   spring.datasource.password=TU_CONTRASEÑA
   spring.jpa.hibernate.ddl-auto=update
   
4. Compila y ejecuta el proyecto con Maven:
   ```
   ./mvnw spring-boot:run
   ```
   Ejemplo de uso
   Obtener todos los productos
   -  `GET http://localhost:8080/inventario-app/productos`
     Respuesta:





