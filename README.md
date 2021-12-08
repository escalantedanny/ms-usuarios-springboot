# API rest Usuarios
Tecnologías: Spring-boot + JPA (Hibernate) + H2 + REST

## Abstract
Aplicación basada en un sencillo sistema para la creacion de usuarios.
La aplicación esta construida con SpringBoot, versión 2.6.1. Se hace uso de JPA con Hibernate para conectarse a bases de datos h2. En si es una API REST con métodos para acceder al detalle
de un usuario y un CRUD para los usuario.

## Pasos para la realización de la aplicación

### Implementación del modelo
Para las distintas entidades (Usuario, Phono) tendremos un atributo id que será la clave primaria en la BD. De igual forma tendremos varios atributos correspondientes a las entidades mencionadas.

En un primer momento utilizamos una h2 desplegada en memoria, por lo que no añadimos ninguna configuracion al archivo .properties solo las configuradas a continuacion:
```sh
#Nombre de la aplicacion
spring.application.name=ms-usuarios-bci
#Puerto por defecto para levantar la API Rest
server.port=8990
#Token para la creacion de usuario
codigo.token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
#Cantidad de caracteres para validar la contraseña
cantidad.caracteres=8
```

### Implementación de los servicios
Para realizar los servicios usaremos una interface donde definiremos los métodos y su implementación, que la capa superior no tiene por que conocer. Debemos recordar que la clase implementada debe llevar la anotación @Service. Con ella decimos que esta es una clase de configuración a tener en cuenta, igual que pasaba con los repositorios.
Como los métodos necesitarán realizar llamadas al repositorio, es necesario una instancia del mismo en la clase. Para ello usaremos inyección. Gracias a haber anotado previamente el repositorio con @Repository, spring sabrá que debe inyectar. Para conseguir que funcione pondremos la anotación @Aurowired sobre el atributo del repositorio en la clase.
Los métodos a implementar son un CRUD (create, read, update & delete).
A su vez, lo métodos de este servicio son transacionales, para ello usamos la anotacion @Transactional en los métodos (menos en el GET). Con esta anotación se intenta ejecutar el código del método, si surgiese algún error o excepción se ejecutaría un rollback. Podemos probar que funciona si forzamos a incluir una excepcion despues de un save y veremos que la bd no sufre ninguna alteración.

### Controladores Rest
Los controladores Rest serán nuestro punto de acceso desde el exterior. Es necesario anotarlos con @RestController. A partir de aquí cada método anotado tendrá su propia URL, parametros, etc. La siguiente tabla muestra de que manera se anotan los métodos en función de que queremos implementar (usaremos el controlador de pedidos como ejemplo):

| HTTP Method | CRUD Method | Anotation | URL |
| ------ | ------ | ------ | ------ |
| POST | Create | @PostMapping | /save |
| GET | Read | @GetMapping | /user/{id} |
| GET | Read | @GetMapping | /users |
| PUT | Update | @PutMapping | /user |
| DELETE | Delete | @DeleteMapping | /user/{id} |

Además, estos métodos contienen parametros, ya sean por la URL o por json. Aquellos que vengan con la URL llevarán la anotacion
@PathParam.

En caso de que los métodos se ejecuten de forma incorrecta se devolverá un códido de error, se capturará y se informará al usuario.

### Primeras pruebas
Ya estamos en disposición de hacer las primeras pruebas. Si todo ha salido según lo esperado deberiamos tener la aplicación en ejecución, con conexión a la base de datos y las URL de acceso disponibles. En nuestro caso vamos a añadir un path de acesso previo a los servicios web. 

```
Ejecutamos la primera sentencia, por ejemplo para obtener el usuario con identificador a007e6d5-6d9d-42b5-8e99-40096d930ab8. Podemos usar el navegador o una herramienta. En este caso haremos uso de Postman:
- Indicamos la url: http://localhost:8990/api/v1/user/a007e6d5-6d9d-42b5-8e99-40096d930ab8
- Indicamos el Auth de typo Bearer Token y agregamos el token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
- Indicamos que es un POST
- En el Body agregamos el Json que enviaremos para la creacion del usuario sera el siguiente:

```sh
{
    "name": "Danny Ezequiel Escalante",
    "email": "danny@gmail.com",
    "password": "hunter2s",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "56"
        },
                {
            "number": "12345677",
            "citycode": "1",
            "contrycode": "57"
        },
                {
            "number": "12345678",
            "citycode": "1",
            "contrycode": "58"
        }
    ]
}
- Clicamos "Send"
Obtenemos la siguiente respuesta:
```sh
{
    "id": "a007e6d5-6d9d-42b5-8e99-40096d930ab8",
    "name": "Danny Ezequiel Escalante",
    "email": "danny@gmail.com",
    "password": "hunter2s",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "56"
        },
        {
            "number": "12345677",
            "citycode": "1",
            "contrycode": "57"
        },
        {
            "number": "12345678",
            "citycode": "1",
            "contrycode": "58"
        }
    ],
    "created": "2021/12/08",
    "modified": "2021/12/08",
    "lastLogin": "01:25:31",
    "isActive": null,
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```
A partir de aquí podemos probar todas las URL para ver como funciona el servicio web implementado. Hay que recordar que debemos 
pasar datos válidos y en todas las rutas debemos enviarle el token de validacion, de lo contrario el servicio dará un error.

### Documentando la aplicación con Swagger
Swagger es un framework que permite tanto documentar apis como crearlas. De igual forma, una API como es la nuestra, con las anotaciones de Swagger hace que el framework genere una UI accesible desde la web, capaz de explicar que hace cada método así como lanzar peticiones.

Para documentar usaremos varias anotaciones. Existen tanto para los controladores, como para las clases de modelo. Si observamos la UI, a la que podemos acceder mediante http://localhost:8990/swagger-ui.html, vemos que contiene anotaciones personalizadas tanto de lo que hace cada método como de las posibles respuestas. Además las clases de modelo que aparecen abajo, también tienen documentados sus atributos. Todo esto se logra gracias a las anotaciones @ApiOperation, @ApiResponse y @ApiModelProperty. Existen más, pero en este ejemplo son las que se han utilizado para manejar incluir Swagger.

## Construyendo la aplicación
La aplicación está lista para su despliegue y construcción. Debemos tener gradle instalado en el ordenador. Si todo es correcto, podemos ejecutar el comando que aparece debajo. Con el se construye la aplicación y se crea el .jar listo para su despliegue.
```sh
Ejecutar 
    gradle build desde la terminal
cambiar a build/libs y ejecutar
    java -jar build/libs/ms-usuario-bci-0.0.1.jar.jar
```
