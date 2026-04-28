# Prueba Técnica OrionTek - Customer Address API

API REST desarrollada en **Java con Spring Boot** para la gestión de clientes y sus direcciones, como parte de la prueba técnica de **OrionTek**.

La solución permite registrar clientes pertenecientes a la empresa y asociarles una o múltiples direcciones.

---

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.5.14
- Spring Web
- Spring Data JPA
- PostgreSQL
- Hibernate
- Jakarta Validation
- Swagger / OpenAPI
- Maven

---

## Descripción del proyecto

Este proyecto implementa una API REST para administrar clientes y direcciones.

La relación principal del sistema es:

```txt
Customer 1 ---- N Address
```

Esto significa que un cliente puede tener muchas direcciones, pero cada dirección pertenece únicamente a un cliente.

---

## Objetivo

El objetivo de esta solución es cumplir con el siguiente requerimiento:

> Se desea tener el control de todos los clientes pertenecientes a la empresa OrionTek, donde cada cliente puede tener N cantidad de direcciones.

Para resolverlo, se desarrolló una API Backend utilizando Java, Spring Boot y PostgreSQL.

---

## Arquitectura del proyecto

El proyecto está organizado bajo una arquitectura por capas, separando responsabilidades para facilitar el mantenimiento, lectura y escalabilidad del código.

```txt
src/main/java/com/oriontek/customer_api
│
├── application
│   ├── dto
│   │   ├── customer
│   │   │   └── CreateCustomerRequest.java
│   │   └── address
│   │       └── CreateAddressRequest.java
│   │
│   └── services
│       ├── CustomerService.java
│       └── AddressService.java
│
├── domain
│   └── entities
│       ├── Customer.java
│       └── Address.java
│
├── infrastructure
│   ├── config
│   │   └── OpenApiConfig.java
│   │
│   └── repositories
│       ├── CustomerRepository.java
│       └── AddressRepository.java
│
├── presentation
│   └── controllers
│       ├── CustomerController.java
│       └── AddressController.java
│
├── shared
│   └── exceptions
│       ├── ResourceNotFoundException.java
│       └── GlobalExceptionHandler.java
│
└── CustomerApiApplication.java
```

---

## Capas de la aplicación

### Presentation

Contiene los controladores REST encargados de recibir las peticiones HTTP.

Ejemplo:

- `CustomerController`
- `AddressController`

Responsabilidades:

- Exponer endpoints.
- Recibir requests.
- Validar entradas.
- Delegar operaciones a los servicios.

---

### Application

Contiene la lógica principal de negocio.

Ejemplo:

- `CustomerService`
- `AddressService`

Responsabilidades:

- Crear clientes.
- Actualizar clientes.
- Crear direcciones.
- Validar que una dirección pertenezca a un cliente.
- Coordinar operaciones con los repositorios.

---

### Domain

Contiene las entidades principales del negocio.

Ejemplo:

- `Customer`
- `Address`

Responsabilidades:

- Representar el modelo de datos.
- Definir relaciones entre entidades.
- Mantener propiedades principales del dominio.

---

### Infrastructure

Contiene configuraciones técnicas y acceso a datos.

Ejemplo:

- `CustomerRepository`
- `AddressRepository`
- `OpenApiConfig`

Responsabilidades:

- Acceso a base de datos.
- Configuración de Swagger/OpenAPI.
- Persistencia con Spring Data JPA.

---

### Shared

Contiene componentes reutilizables y transversales.

Ejemplo:

- `ResourceNotFoundException`
- `GlobalExceptionHandler`

Responsabilidades:

- Manejo de excepciones.
- Respuestas de error centralizadas.

---

## Patrones y buenas prácticas aplicadas

### Repository Pattern

Se utiliza Spring Data JPA para abstraer el acceso a datos.

```java
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    boolean existsByEmail(String email);
    boolean existsByDocumentNumber(String documentNumber);
}
```

---

### DTO Pattern

Se utilizan DTOs para recibir datos desde la API, evitando exponer directamente las entidades como modelos de entrada.

Ejemplo:

- `CreateCustomerRequest`
- `CreateAddressRequest`

---

### Dependency Injection

Las dependencias se inyectan mediante constructores, favoreciendo el desacoplamiento entre clases.

```java
public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
}
```

---

### Global Exception Handling

El manejo de errores está centralizado mediante `@RestControllerAdvice`.

Esto permite que la API devuelva respuestas consistentes cuando ocurre una excepción.

---

### Validation

Se utilizan anotaciones de Jakarta Validation para validar los campos requeridos.

```java
@NotBlank(message = "El nombre es obligatorio")
private String firstName;

@Email(message = "El correo no tiene un formato válido")
@NotBlank(message = "El correo es obligatorio")
private String email;
```

---

### UUID como identificador

Las entidades utilizan `UUID` como identificador principal en lugar de IDs numéricos secuenciales.

Ventajas:

- Evita exponer secuencias internas.
- Es útil en sistemas distribuidos.
- Permite identificadores únicos globales.

---

## Modelo de dominio

### Customer

Representa un cliente dentro del sistema.

| Campo | Tipo | Descripción |
|---|---|---|
| id | UUID | Identificador único |
| firstName | String | Nombre |
| lastName | String | Apellido |
| documentNumber | String | Documento |
| email | String | Correo electrónico |
| phone | String | Teléfono |
| isActive | Boolean | Estado del cliente |
| createdAt | LocalDateTime | Fecha de creación |
| updatedAt | LocalDateTime | Fecha de actualización |
| addresses | List Address | Direcciones asociadas |

---

### Address

Representa una dirección asociada a un cliente.

| Campo | Tipo | Descripción |
|---|---|---|
| id | UUID | Identificador único |
| street | String | Calle |
| city | String | Ciudad |
| province | String | Provincia |
| country | String | País |
| postalCode | String | Código postal |
| isPrimary | Boolean | Dirección principal |
| createdAt | LocalDateTime | Fecha de creación |
| updatedAt | LocalDateTime | Fecha de actualización |
| customer | Customer | Cliente dueño de la dirección |

---

## Relación entre entidades

La relación entre `Customer` y `Address` es de uno a muchos.

### Customer

```java
@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Address> addresses = new ArrayList<>();
```

### Address

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "customer_id", nullable = false)
private Customer customer;
```

---

## Configuración de base de datos

El proyecto utiliza PostgreSQL.

Crear la base de datos:

```sql
CREATE DATABASE oriontek_customers_db;
```

Archivo de configuración:

```txt
src/main/resources/application.properties
```

Ejemplo:

```properties
spring.application.name=customer-api

spring.datasource.url=jdbc:postgresql://localhost:5432/oriontek_customers_db
spring.datasource.username=postgres
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false

server.port=8081
```

> Cambiar `TU_PASSWORD` por la contraseña real de PostgreSQL.

---

## Ejecución del proyecto

Clonar el repositorio:

```bash
git clone https://github.com/TU_USUARIO/oriontek-customer-address-api.git
```

Entrar al proyecto:

```bash
cd oriontek-customer-address-api
```

Ejecutar en Windows:

```bash
.\mvnw.cmd spring-boot:run
```

Ejecutar en Linux/Mac:

```bash
./mvnw spring-boot:run
```

La API quedará disponible en:

```txt
http://localhost:8081
```

---

## Swagger / OpenAPI

La documentación interactiva está disponible en:

```txt
http://localhost:8081/swagger-ui/index.html
```

Desde Swagger se pueden visualizar y probar todos los endpoints de la API.

---

## Endpoints disponibles

### Customers

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/customers` | Crear cliente |
| GET | `/api/customers` | Listar clientes |
| GET | `/api/customers/{id}` | Obtener cliente por ID |
| PUT | `/api/customers/{id}` | Actualizar cliente |
| DELETE | `/api/customers/{id}` | Eliminar cliente |

---

### Addresses

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/customers/{customerId}/addresses` | Crear dirección para un cliente |
| GET | `/api/customers/{customerId}/addresses` | Listar direcciones de un cliente |
| PUT | `/api/customers/{customerId}/addresses/{addressId}` | Actualizar dirección |
| DELETE | `/api/customers/{customerId}/addresses/{addressId}` | Eliminar dirección |

---

## Ejemplos de uso

### Crear cliente

```http
POST /api/customers
Content-Type: application/json
```

Body:

```json
{
  "firstName": "Junior",
  "lastName": "Carpenter",
  "documentNumber": "00112345678",
  "email": "junior@example.com",
  "phone": "8298056075"
}
```

Respuesta esperada:

```json
{
  "id": "f4772d4c-61cd-4175-8fc5-9b564a82fadf",
  "firstName": "Junior",
  "lastName": "Carpenter",
  "documentNumber": "00112345678",
  "email": "junior@example.com",
  "phone": "8298056075",
  "isActive": true,
  "createdAt": "2026-04-28T16:30:00",
  "updatedAt": null,
  "addresses": []
}
```

---

### Listar clientes

```http
GET /api/customers
```

---

### Obtener cliente por ID

```http
GET /api/customers/{id}
```

Ejemplo:

```txt
GET /api/customers/f4772d4c-61cd-4175-8fc5-9b564a82fadf
```

---

### Actualizar cliente

```http
PUT /api/customers/{id}
Content-Type: application/json
```

Body:

```json
{
  "firstName": "Junior Rafael",
  "lastName": "Carpenter",
  "documentNumber": "00112345678",
  "email": "junior.rafael@example.com",
  "phone": "8298056075"
}
```

---

### Eliminar cliente

```http
DELETE /api/customers/{id}
```

---

### Crear dirección

```http
POST /api/customers/{customerId}/addresses
Content-Type: application/json
```

Body:

```json
{
  "street": "Calle Principal #10",
  "city": "Santo Domingo Este",
  "province": "Santo Domingo",
  "country": "República Dominicana",
  "postalCode": "11500",
  "isPrimary": true
}
```

---

### Listar direcciones de un cliente

```http
GET /api/customers/{customerId}/addresses
```

---

### Actualizar dirección

```http
PUT /api/customers/{customerId}/addresses/{addressId}
Content-Type: application/json
```

Body:

```json
{
  "street": "Avenida Las Américas #25",
  "city": "Santo Domingo Este",
  "province": "Santo Domingo",
  "country": "República Dominicana",
  "postalCode": "11501",
  "isPrimary": true
}
```

---

### Eliminar dirección

```http
DELETE /api/customers/{customerId}/addresses/{addressId}
```

---

## Validaciones implementadas

### Customer

| Campo | Validación |
|---|---|
| firstName | Obligatorio |
| lastName | Obligatorio |
| documentNumber | Obligatorio |
| email | Obligatorio y formato válido |
| phone | Opcional |

---

### Address

| Campo | Validación |
|---|---|
| street | Obligatorio |
| city | Obligatorio |
| province | Obligatorio |
| country | Obligatorio |
| postalCode | Opcional |
| isPrimary | Opcional |

---

## Reglas de negocio

- Un cliente puede tener múltiples direcciones.
- Una dirección pertenece únicamente a un cliente.
- No se puede crear una dirección para un cliente inexistente.
- No se puede actualizar una dirección inexistente.
- No se puede eliminar una dirección inexistente.
- Al actualizar o eliminar una dirección, se valida que pertenezca al cliente indicado.
- No se permite crear clientes con correos duplicados.
- No se permite crear clientes con documentos duplicados.

---

## Manejo de errores

El proyecto implementa manejo centralizado de errores.

### Cliente no encontrado

```json
{
  "message": "Cliente no encontrado."
}
```

### Dirección no encontrada

```json
{
  "message": "Dirección no encontrada."
}
```

### Dirección no pertenece al cliente

```json
{
  "message": "La dirección no pertenece a este cliente."
}
```

### Error de validación

```json
{
  "firstName": "El nombre es obligatorio",
  "email": "El correo no tiene un formato válido"
}
```

---

## Flujo general de una petición

Ejemplo: creación de dirección para un cliente.

```txt
Cliente HTTP
    |
    v
AddressController
    |
    v
AddressService
    |
    v
CustomerRepository valida existencia del cliente
    |
    v
AddressRepository guarda la dirección
    |
    v
PostgreSQL
```

---

## Comandos útiles

Compilar el proyecto:

```bash
.\mvnw.cmd clean install
```

Ejecutar el proyecto:

```bash
.\mvnw.cmd spring-boot:run
```

Ejecutar limpio:

```bash
.\mvnw.cmd clean spring-boot:run
```

Ver rama actual:

```bash
git branch
```

Subir rama actual:

```bash
git push -u origin NOMBRE_RAMA
```

---

## Estrategia de ramas

El repositorio puede manejarse con la siguiente estructura:

```txt
main
develop
feat/customer-address-crud
```

### main

Rama principal y estable del proyecto.

### develop

Rama de integración para cambios antes de pasar a producción.

### feat/customer-address-crud

Rama de desarrollo de la funcionalidad principal de clientes y direcciones.

---

## Mejoras futuras

Algunas mejoras que podrían agregarse:

- Paginación en listado de clientes.
- Filtros por nombre, documento o correo.
- Soft delete para clientes.
- Pruebas unitarias con JUnit y Mockito.
- Pruebas de integración.
- Docker Compose para PostgreSQL.
- MapStruct para mapear entidades y DTOs.
- CQRS.
- Seguridad con JWT.
- Auditoría de cambios.
- DTOs de respuesta personalizados.
- Manejo más detallado de errores de base de datos.

---

## Estado del proyecto

El proyecto incluye:

- API REST funcional.
- CRUD de clientes.
- CRUD de direcciones por cliente.
- Relación uno a muchos.
- PostgreSQL como base de datos.
- UUID como identificador.
- Validaciones con Jakarta Validation.
- Manejo centralizado de errores.
- Swagger/OpenAPI.
- Arquitectura organizada por capas.

---

## Autor

**Junior Rafael Carpenter**

Backend / Full Stack Developer

---

## Nota final

Esta solución fue desarrollada como prueba técnica para OrionTek, enfocada en Backend utilizando Java, Spring Boot y PostgreSQL.

El objetivo fue entregar una solución funcional, organizada, clara y extensible, aplicando buenas prácticas de desarrollo backend.
