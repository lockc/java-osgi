# Swagger API

This demonstrates using Spring with the Swagger API for documenting JAX-RS web services, and 
providing a cool UI to interact with your API.  The application serves both the API and the 
static documentation for accessing it.

* http://swagger.io/
* https://github.com/martypitt/swagger-springmvc
* https://github.com/swagger-api/swagger-ui

## Instructions

* Run the web app
```
mvn spring-boot:run
```
* You'll need to configure a proxy in Apache to avaoid CORS errors
```
ProxyPass / http://localhost:8080/
ProxyPassReverse / http://localhost:8080/
```
* Now go to http://localhost/dist/index.html


