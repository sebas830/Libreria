# 📚 Librería API

API REST desarrollada con **Spring Boot** para la gestión de libros,
usuarios y préstamos.

## 🚀 Tecnologías

-   Java 17+
-   Spring Boot 3.x
-   Spring Data JPA
-   Spring Security (con roles)
-   H2 / MySQL
-   Maven
-   Lombok

## 🧩 Módulos principales

### 1. Usuarios (`/api/users`)

-   **GET** `/api/users` → Lista todos los usuarios.
-   **POST** `/api/users` → Crea un nuevo usuario.
-   **PUT** `/api/users/{id}` → Actualiza un usuario.
-   **DELETE** `/api/users/{id}` → Elimina un usuario.

Roles disponibles: - `ROLE_USER` - `ROLE_LIBRARIAN`

### 2. Libros (`/api/books`)

-   **GET** `/api/books` → Lista todos los libros.
-   **POST** `/api/books` → Crea un libro (solo usuarios autenticados
    con rol LIBRARIAN).
-   **PUT** `/api/books/{id}` → Actualiza un libro.
-   **DELETE** `/api/books/{id}` → Elimina un libro.

Ejemplo de creación:

``` json
{
  "title": "El Principito",
  "author": "Antoine de Saint-Exupéry",
  "available": true
}
```

### 3. Préstamos (`/api/loans`)

-   **POST** `/api/loans?bookId=1&userId=1` → Registra un préstamo.
-   **GET** `/api/loans` → Lista todos los préstamos.
-   **PUT** `/api/loans/{id}/return` → Marca un préstamo como devuelto.

## 🔐 Seguridad

-   Se utiliza **Spring Security** con roles (`ROLE_USER`,
    `ROLE_LIBRARIAN`).
-   Los **librarian** pueden crear, editar y eliminar libros.
-   Los **usuarios** pueden realizar préstamos y ver libros.

## 🧪 Ejemplo de autenticación

Para realizar peticiones protegidas:

``` bash
curl -u librarian:librarian http://localhost:8080/api/books
```

## 🗄️ Base de datos

Por defecto, usa H2 en memoria.\
Acceso web:

    http://localhost:8080/h2-console
    JDBC URL: jdbc:h2:mem:testdb
    User: sa
    Password: (vacío)

## 🧠 Desarrollo

Desarrollado como práctica de **Spring Boot con seguridad y manejo de
roles**.

## 📜 Licencia

Se permite solo para uso educativo. No puede ser vendido; sin embargo, puede ser usado externamente y modificado.


## 👥 Autor

📧 **sebasramirez830@gmail.com**  
🐙 GitHub: [@sebas830](https://github.com/sebas830)
