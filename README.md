# Proyecto_Chakray
"Proyecto de prueba para la empresa Chakray"
# Proyecto Chakray - API de Gestión de Usuarios

## Descripción

Proyecto backend en Java con Spring Boot para gestión de usuarios con funcionalidades de registro, edición, filtrado y autenticación.

El sistema permite manejar usuarios con validaciones para RFC y celular, encriptación de contraseñas y operaciones CRUD básicas.

## Tecnologías

- Java 17+
- Spring Boot 3.x
- Maven
- Lombok
- JUnit (para pruebas)
- Postman (colección incluida para pruebas de API)

## Cómo ejecutar

1. Compilar y ejecutar con Maven:

```bash
mvn clean install
mvn spring-boot:run
````

2. La aplicación se ejecuta por defecto en `http://localhost:8080/`

## Endpoints principales

| Método | Ruta                                                  | Descripción                            |
| ------ | ----------------------------------------------------- | -------------------------------------- |
| GET    | /UsuariosWb/mostrar                                   | Lista todos los usuarios               |
| POST   | /UsuariosWb/annadirUsuario                            | Añade un nuevo usuario                 |
| PATCH  | /UsuariosWb/editar/{id}                               | Edita usuario por ID                   |
| DELETE | /UsuariosWb/eliminar/{id}                             | Elimina usuario por ID                 |
| POST   | /UsuariosWb/iniciarSesion                             | Inicia sesión con tax\_id y contraseña |
| GET    | /UsuariosWb?ordenarPor=campo\&filtro=campo+oper+valor | Filtra y ordena usuarios               |

> Nota: El filtro sigue el formato `campo+operacion+valor`
> Operaciones:
>
> * `co` Contiene
> * `eq` Igual
> * `sw` Empieza con
> * `ew` Termina con

## Pruebas

* Se incluye colección Postman para probar todos los endpoints.
* Tests unitarios con JUnit cubren las funciones principales.

## Seguridad y validaciones

* Contraseñas almacenadas encriptadas con AES.
* Validación de RFC y formato de celular.
* El campo contraseña no se expone en respuestas.

**Autor:** Carlos Alberto Salado Chavez
**Fecha:** 31 de Julio de 2025


