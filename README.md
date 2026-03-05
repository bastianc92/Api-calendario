# API de Festivos y Calendario Laboral

Una sencilla API REST construida con Spring Boot para gestionar países, festivos y calendarios laborales.

## Requisitos previos

- Java 21
- Maven 3+ (o el wrapper `mvnw` incluido)
- PostgreSQL (base `apicalendario` creada)
- Docker (opcional para contenedores de DB)

## Instalación y ejecución

1. Clonar el repositorio:
   ```bash
   git clone <repositorio> apicalendario
   cd apicalendario
   ```
2. Configurar la base de datos PostgreSQL y crear la base `apicalendario`.
3. Ajustar `src/main/resources/application.properties` con credenciales.
4. Ejecutar la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```
   o empaquetar con `./mvnw clean package` y correr el JAR.

Si usas Docker, puedes levantar una instancia de PostgreSQL:
```bash
docker run --name pg -e POSTGRES_PASSWORD=tuclave -e POSTGRES_DB=apicalendario -p 5432:5432 -d postgres
```

## Configuración de `application.properties`

```properties
spring.application.name=apicalendario
spring.datasource.url=jdbc:postgresql://localhost:5432/apicalendario
spring.datasource.username=postgres
spring.datasource.password='1234'

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## Endpoints disponibles

### Países
- `GET /api/pais`
  - **Response**: 200
  ```json
  [{"id":1,"nombre":"España","codigo":"ES","descripcion":""}]
  ```

### Festivos
- `GET /api/festivos/verificar?fecha=YYYY-MM-DD`
  - **Response**: 200
  ```json
  true
  ```

### Calendario
- `GET /api/calendario/laborales`
  - **Response**: 200
  ```json
  [{"id":1,"fecha":"2026-03-03","esLaboral":true,...}]
  ```
- `GET /api/calendario/no-laborales`
  - **Response**: 200
  ```json
  [{"id":2,"fecha":"2026-03-01","esLaboral":false,...}]
  ```

### TipoFestivo CRUD
- `GET /api/tipofestivo` -> lista
- `POST /api/tipofestivo` -> crea
  ```json
  {"nombre":"Navidad","descripcion":"","esFeriado":true}
  ```
- `PUT /api/tipofestivo/{id}` -> actualiza
- `DELETE /api/tipofestivo/{id}` -> borra

### Tipo CRUD
- `GET /api/tipo` -> lista
- `POST /api/tipo` -> crea
  ```json
  {"nombre":"Laboral","descripcion":"","codigo":"LAB"}
  ```
- `PUT /api/tipo/{id}` -> actualiza
- `DELETE /api/tipo/{id}` -> borra

## Ejemplos de uso

Con `curl`:
```bash
curl http://localhost:8080/api/pais
curl "http://localhost:8080/api/festivos/verificar?fecha=2026-12-25"
curl http://localhost:8080/api/calendario/laborales
curl -X POST -H "Content-Type: application/json" -d '{"nombre":"Navidad","descripcion":"","esFeriado":true}' http://localhost:8080/api/tipofestivo
```

En Postman, crea una colección con las rutas anteriores usando el método y JSON correspondiente.
