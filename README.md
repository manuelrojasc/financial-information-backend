# Financial Customer information

Este proyecto consta de 3 microservicios y un microservicio central que sirve como bff.

se desarrollo bajo la arquitectura hexagonal.

Se utilizo mongo como base de datos

Se levanto una imagen de keycloak para el servicio de authorization hacia nuestros microservicios

Se debe tener instalado Docker desktop, en mi caso uso para windows

## Docker Compose
cada microservicio ya tiene connfigurado un dockerfile, que nos permitira referenciar en docker compose.
nos dirigimos a la ruta central del proyecto donde se encontrara el archivo docker-compose, abrimos la consola en esa ruta y ejecutamos lo siguiente:
```sh
docker compose up .
```
 y listo ya se podra iniciar con las configuraciones precias, mencionadas en la parte de abajo


## Prerequisitos para que el proyecto ejecute correctamente
* En estos microservicios tener en cuenta la cadena de conexion hacia mongo, reemplazarlo por el suyo  en el archivo application.yml:
  * customer
  * product

* para el servicio de autenticacion se debe configurar keycloak , crear un realm, posteriormente un client, y luego un usuario, asignarle el permiso que desee, una vez finalizado la configuracion, ir a la opcion realms setting y desplazarce hacia abajo , hasta encontrar la opcion de `OpenID Endpoint Configuration`, al dar clic se abrira una pestaña en el navegador que cargara las configuraciones, copiar el valor de `jwks_uri`, eso nos servira como servidor de autenticacion en los microservicios, el valor se reemplaza en los application.yml de cada microservicio en la opcion de   `security.oauth2.resourceserver.jwt.jwk-set-uri:`, tener en cuenta que al estar en un entorno de docker, se deben reemplazar las url de localhost por el host interno de docker.

** **
para obtener el token de authorizacion por postman, reemplazar los valor por el cliente creado en keycloak, de igual forma crear la coleecion de acuerdo a los modelos de la bd
** **
**Free Software, Hell Yeah!**

