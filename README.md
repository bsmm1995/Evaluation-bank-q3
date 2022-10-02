### Ejercicio prácito - Java Spring Boot - 2022-Q3

## Objetivo
Crear una API Rest para administrar los préstamos de equipos a colaboradores de una institución.

## Base de datos en memoria
Al cargar la aplicación, se inicializará los datos de la base inicial, la cual servirá como insumo para desarrollar el ejercicio. 

---

## Indicaciones Generales

- Crear una rama con el nombre del participante: Ejemplo: Q32022-roman-macas-alex-patricio.
- Subir cambios a sus ramas una vez terminado el tiempo establecido.
    - Al finalizar el tiempo el repositorio no permitirá nuevos cambios.
- Elaborar al menos una prueba de integración.
- Implementar buenas prácticas de programación.
- Prueba de integración: Cargar el contexto de la aplicación.
- Pruebas unitarias: Comportamientos

---
## Ejercicio: Administración de Préstamos de equipos.

<p><strong>Con el objetivo</strong> de poder administrar  los préstamos de equipos a colaboradores de una Institución<br>
<strong>Como</strong> administrador del departamento de sistemas <br>
<strong>Quiero</strong> tener una API que me permita crear una persona, editar y generar un préstamo.</p>

#### Criterios de aceptación

- Crear Persona:

<p><strong>Dado</strong> que envío los datos de una persona<br>
<strong>Cuando</strong> consumo el servicio de creación de persona<br>
<strong>Entonces</strong> se debe crear la persona en la Institución con los siguiente campos:  identificador de usuario, nombres, tipo de usuario(INVITADO, INTERNO, EXTERNO) y estado(CREADO, ACTIVO, INACTIVO) por defecto es CREADO.</p>

- Tipo usuario no soportado
<p><strong>Dado</strong> que envío los datos de una persona con un valor en el campo tipoUsuario diferente a los permitidos<br>
<strong>Cuando</strong> consumo el servicio de creación de persona<br>
<strong>Entonces</strong> debe retornar un error HTTP 400 con el siguiente JSON:</p>
<code>{<br>  
 &nbsp; &nbsp;"mensaje" : "Tipo de usuario no existe"<br>
 &nbsp;}</code>


- Editar Persona
<p><strong>Dado</strong> que modifico los datos una persona<br>
<strong>Cuando</strong> consumo el servicio de actualización<br>
<strong>Entonces</strong> se debe actualizar la persona únicamente con los datos permitidos que son tipoUsuario y estado</p>

- Generar un préstamo.
<p><strong>Dado</strong> que quiero generar el préstamo de equipo para un nuevo usuario<br>
<strong>Cuando</strong> consumo el servicio para crear préstamo<br>
<strong>Entonces</strong> se debe generar el préstamo con los siguientes datos: identificador de prestamo, serie de equipo, fecha de prestamo y fecha de devolucion, adicional actualizar el colaborador a estado ACTIVO</p>

- Cupo máximo de préstamo.
<p><strong>Dado</strong> que un usuario EXTERNO e INVITADO solo puede tener un equipo prestado en la Institución y un usuario de tipo INTERNO máximo 2<br>
<strong>Cuando</strong> consumo el servicio para crear préstamo para un usuario que tiene el cupo máximo permitido<br>
<strong>Entonces</strong> debe retornar un error HTTP 400 con el siguiente JSON:</p>
<code>{<br>  
 &nbsp; &nbsp;"mensaje" : "El usuario con identificación xxxxxx ya tiene el cupo máximo permitido y no es posible realizar otro préstamo"<br>
 &nbsp;}</code>

 - Fecha de devolución de un usuario INVITADO.
<p><strong>Dado</strong> que si el préstamo lo hace un usuario tipo INVITADO<br>
<strong>Cuando</strong> consumo el servicio para crear préstamo para un usuario de tipo INVITADO<br>
<strong>Entonces</strong> debe calcular la fecha de devolución que es la fecha actual más 30 días sin contar sábados y domingos</p>

 - Fecha de devolución de un usuario INTERNO.
<p><strong>Dado</strong> que si el préstamo lo hace un usuario tipo INTERNO<br>
<strong>Cuando</strong> consumo el servicio para crear préstamo para un usuario de tipo INTERNO<br>
<strong>Entonces</strong>debe calcular la fecha de devolución que es la fecha actual más 365 días es decir un año calendario</p>

 - Fecha de devolución de un usuario EXTERNO.
<p><strong>Dado</strong> que si el préstamo lo hace un usuario tipo EXTERNO<br>
<strong>Cuando</strong> consumo el servicio para crear préstamo para un usuario de tipo EXTERNO<br>
<strong>Entonces</strong>debe calcular la fecha de devolución que es la fecha actual más 360 días es decir un año comercial</p>

- Error en fecha de devolución de un préstamo.
<p><strong>Dado</strong> que quiero generar el préstamo de equipo para un nuevo usuario<br>
<strong>Cuando</strong> consumo el servicio para crear préstamo y la fecha de devolución no es la correcta<br>
<strong>Entonces</strong>debe retornar un error HTTP 400 con el siguiente JSON:</p>
<code>{<br>  
 &nbsp; &nbsp;"mensaje" : "Error en la fecha de devolución para usuario de tipo XXXXX"<br>
 &nbsp;}</code>

 - Reporte de préstamos.
<p><strong>Dado</strong> que se requiere obtener un reporte de los préstamos con los respectivos responsables y con los siguientes filtros: fecha de préstamo, fecha de devolución y usuario<br>
<strong>Cuando</strong> consumo el servicio para obtener todos los prestamos con su responsable<br>
<strong>Entonces</strong> debe retornar un JSON con el siguiente detalle:</p>
<code>{<br>  
&nbsp; &nbsp;"usuario" : "Usuario 1",<br>
&nbsp; &nbsp;"tipoUsuario" : "INVITADO",<br> 
&nbsp; &nbsp;"serieEquipo" : "SSSSSS",<br>
&nbsp; &nbsp;"fechaPrestamo" : "XX/XX/XXXX",<br>
&nbsp; &nbsp;"fechaDevolución" : "XX/XX/XXXX"<br>
 &nbsp;}</code>

## Tareas
- Crear un endpoint para crear una persona.
- Crear un endpoint para actualizar una persona.
- Crear un endpoint para obtener una persona por ID.
- Crear un endpoint para crear un préstamo.
- Crear un endpoint para obtener reporte de préstamos.


## Expectativas técnicas del ejercicio
- Consultas en JPQL o Query Nativos.
- Relaciones entre entidades.
- Control de excepciones.
- Pruebas unitarias en la capa de servicio para préstamo.
- Prueba de integración para el endpoint de préstamo.
- Formato de respuesta requerida.
- Uso correcto de enumerados y constantes.
- Mapeado de entidad a un DTO (Data Transfer Object
- Buenas prácticas.