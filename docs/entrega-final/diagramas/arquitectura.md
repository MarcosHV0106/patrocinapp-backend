# Diagramas de arquitectura

## Contexto

```mermaid
C4Context
  title PatrocinApp - contexto
  Person(deportista, "Deportista", "Cumple metas y envía evidencias")
  Person(negocio, "Negocio", "Patrocina y revisa evidencias")
  System(app, "PatrocinApp", "Contratos, fondos, evidencias y pagos")
  SystemDb(pg, "PostgreSQL", "Datos y binarios del MVP")
  Rel(deportista, app, "HTTPS/JWT")
  Rel(negocio, app, "HTTPS/JWT")
  Rel(app, pg, "JDBC/Flyway")
```

## Componentes ejecutables

```mermaid
flowchart LR
  U[Browser] --> NG[Angular 22 SPA]
  NG -->|REST JSON/multipart + JWT| RC[Spring REST controllers]
  RC --> SEC[Spring Security + JWT]
  RC --> IN[Input ports]
  IN --> APP[Application services]
  APP --> DOM[Domain models/invariants]
  APP --> OUT[Output ports]
  OUT --> JPA[JPA adapters]
  OUT --> BIN[BYTEA evidence adapter]
  JPA --> PG[(PostgreSQL)]
  BIN --> PG
  FLY[Flyway V1/V2] --> PG
```

## Paquetes backend

```mermaid
flowchart TD
  REST[interfaces.rest] --> PI[domain.ports.input]
  CFG[infrastructure.config/security] --> REST
  PI --> APP[application.service]
  APP --> MODEL[domain.model/enums/exceptions]
  APP --> PO[domain.ports.output]
  AD[infrastructure.persistence/security] --> PO
  AD --> MODEL
```

La dependencia apunta hacia el dominio; Spring ensambla implementaciones en infraestructura.

## Despliegue objetivo preparado

```mermaid
flowchart LR
  USER[Browser HTTPS] --> V[Vercel: Angular + runtime-config]
  V --> R[Railway: contenedor Spring Boot]
  R --> H[/actuator/health]
  R --> P[(Railway PostgreSQL)]
  R --> LOG[Logs Railway]
```

Este despliegue corresponde a los manifiestos preparados, pero **no fue ejecutado** al 2026-07-13.
