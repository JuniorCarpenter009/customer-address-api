# Prueba Técnica OrionTek - Customer Address API

API REST desarrollada en **Java + Spring Boot** para gestionar clientes y sus direcciones, como parte de la prueba técnica de **OrionTek**.

La solución permite tener el control de todos los clientes pertenecientes a la empresa, considerando que cada cliente puede tener una o múltiples direcciones asociadas.

---

## Tabla de contenido

- [Tecnologías utilizadas](#tecnologías-utilizadas)
- [Descripción del proyecto](#descripción-del-proyecto)
- [Objetivo de la solución](#objetivo-de-la-solución)
- [Arquitectura del proyecto](#arquitectura-del-proyecto)
- [Patrones y buenas prácticas aplicadas](#patrones-y-buenas-prácticas-aplicadas)
- [Modelo de dominio](#modelo-de-dominio)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Configuración de base de datos](#configuración-de-base-de-datos)
- [Ejecución del proyecto](#ejecución-del-proyecto)
- [Documentación Swagger](#documentación-swagger)
- [Endpoints disponibles](#endpoints-disponibles)
- [Ejemplos de uso](#ejemplos-de-uso)
- [Validaciones implementadas](#validaciones-implementadas)
- [Manejo de errores](#manejo-de-errores)
- [Decisiones técnicas](#decisiones-técnicas)
- [Mejoras futuras](#mejoras-futuras)
- [Autor](#autor)

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

Este proyecto implementa una API REST para administrar clientes y sus direcciones.

La relación principal del sistema es:

```txt
Customer 1 ---- N Address