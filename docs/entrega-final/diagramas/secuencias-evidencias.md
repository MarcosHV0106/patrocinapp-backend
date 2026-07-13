# Secuencias de evidencias

## Carga

```mermaid
sequenceDiagram
  actor D as Deportista
  participant UI as Angular
  participant API as EvidenciaController
  participant S as EnviarEvidenciaService
  participant DB as PostgreSQL
  D->>UI: selecciona/captura y confirma
  UI->>API: POST /metas/{id}/evidencias multipart + JWT
  API->>S: actor autenticado + archivo
  S->>S: valida propiedad, estado, tamaño, MIME, firma y hash
  S->>DB: evidencia + BYTEA + auditoría + notificación
  DB-->>S: intento persistido
  S-->>UI: 201 evidencia EN_REVISION
  UI-->>D: progreso, historial y estado
```

## Rechazo y reenvío

```mermaid
sequenceDiagram
  actor N as Negocio
  actor D as Deportista
  participant API as API
  participant DB as PostgreSQL
  N->>API: POST /evidencias/{id}/rechazar {motivo}
  API->>DB: autoriza propietario y marca RECHAZADA
  API->>DB: auditoría + notificación al deportista
  D->>API: GET historial/actual
  API-->>D: motivo obligatorio visible
  D->>API: POST nueva evidencia multipart
  API->>DB: intento N+1 EN_REVISION
  API->>DB: notificación al negocio
```

## Aprobación y liberación

```mermaid
sequenceDiagram
  actor N as Negocio
  participant API as API
  participant TX as AprobarEvidenciaService
  participant DB as PostgreSQL
  N->>API: POST /evidencias/{id}/aprobar + JWT
  API->>TX: actor propietario
  TX->>DB: BEGIN + locks meta/fondo/contrato
  TX->>DB: verifica EN_REVISION y transacción inexistente
  TX->>DB: evidencia APROBADA + meta PAGADA
  TX->>DB: fondo congelado -= monto; liberado += monto
  TX->>DB: transacción (deportista + comisión)
  TX->>DB: finalizar contrato si todas pagadas
  TX->>DB: notificaciones + auditoría + COMMIT
  DB-->>API: resultado único
  API-->>N: 200; segundo intento responde conflicto
```
