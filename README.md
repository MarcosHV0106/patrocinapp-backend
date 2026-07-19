# PatrocinApp backend

API Spring Boot 3.5 / Java 21 para contratos de patrocinio con evidencia física, revisión, fondo de garantía, pago único, notificaciones y auditoría.

## Ejecución local

Requisitos: Java 21, Maven Wrapper y PostgreSQL 16. Copia `.env.example` a `.env`, exporta las variables en tu terminal y ejecuta:

```bash
./mvnw spring-boot:run
```

En Windows: `mvnw.cmd spring-boot:run`. Swagger queda en `http://localhost:8080/swagger-ui.html` y salud en `http://localhost:8080/actuator/health`.

Para levantar PostgreSQL, semillas y backend juntos:

```bash
docker compose up --build
```

El perfil `demo` solo se activa en Compose (`local,demo`). Sus cuentas usan la contraseña `PatrocinAppDemo2026!` y no deben habilitarse en producción.

## Verificación

```bash
./mvnw clean verify
```

El informe JaCoCo se genera en `target/site/jacoco/index.html`; `verify` exige 80% de líneas en las clases del núcleo financiero y de evidencias. Las pruebas normales usan H2 aislado. Testcontainers no se ejecuta cuando Docker no está disponible.

## Producción

Usa el perfil `prod`, PostgreSQL persistente, un `JWT_SECRET` aleatorio de al menos 32 bytes y orígenes CORS explícitos. No actives `demo`. Consulta `docs/entrega-final/` antes de preparar Railway o aplicar una migración.

## Arquitectura y tecnologías

Arquitectura hexagonal con DDD: `domain` contiene modelos, invariantes y puertos; `application` implementa casos de uso; `infrastructure` aporta JPA, storage, JWT y configuración; `interfaces/rest` expone controladores y DTO. Tecnologías: Java 21, Spring Boot 3.5, Security/JWT, Data JPA, Flyway, PostgreSQL, Springdoc, Actuator, JUnit/MockMvc/H2 y JaCoCo.

## Base de datos y migraciones

Flyway aplica `V1__linea_base.sql` y `V2__evidencias_auditoria_y_consistencia.sql`; producción usa Hibernate `validate` y tiene `clean` deshabilitado. V2 agrega evidencias/archivos, notificaciones, auditoría, versiones, índices y restricciones de consistencia. Las semillas repetibles viven en `db/demo` y solo cargan con el perfil `demo`.

## Variables principales

Consulta `.env.example`. Son obligatorias en producción: `SPRING_DATASOURCE_URL`, usuario/clave PostgreSQL, `JWT_SECRET`, `CORS_ALLOWED_ORIGINS` y `SPRING_PROFILES_ACTIVE=prod`. También son configurables comisión, expiración JWT, límites/tipos de archivo, logs y `PORT`.

## API principal

- `POST /api/auth/login`; `POST /api/usuarios/negocios|deportistas`.
- `GET /api/deportistas`, `/api/plantillas`; `POST /api/contratos`; listados por negocio/deportista e historial.
- `POST /api/metas/{id}/evidencias` multipart; historial/actual, metadatos y descarga en `/api/evidencias/...`.
- `POST /api/evidencias/{id}/rechazar|aprobar`.
- `GET /api/dashboard/deportista/resumen|negocio/resumen`.
- `GET /api/notificaciones`; `PATCH /api/notificaciones/{id}/leida`.

La colección reproducible está en `postman/`; OpenAPI es la referencia completa de payloads y códigos.

## Seguridad y consistencia

La identidad/rol se obtienen del JWT, se comprueba propiedad de recursos y los binarios se validan por tamaño, MIME, firma y SHA-256. La aprobación usa una transacción con locks, unicidad por meta y rollback; impide pago doble y saldo negativo. Ver `SECURITY.md`.

## Docker, Railway y estructura de entrega

`Dockerfile` es multi-stage y ejecuta como usuario no root; `docker-compose.yml` levanta PostgreSQL/backend con healthchecks. `railway.json` deja el servicio preparado, no desplegado. Los procedimientos, diagramas, pruebas y guiones están en `docs/entrega-final/`.

## Equipo y licencia

Los roles previstos y la evidencia real se documentan en `CONTRIBUTORS.md`; no se atribuye trabajo sin commits/PR auténticos. El repositorio no declara actualmente una licencia, por lo que se mantienen todos los derechos hasta que el equipo elija una.
