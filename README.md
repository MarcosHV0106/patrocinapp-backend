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
