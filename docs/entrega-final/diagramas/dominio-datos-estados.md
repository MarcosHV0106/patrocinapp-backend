# Dominio, datos y estados

## Dominio

```mermaid
classDiagram
  Usuario <|-- PerfilNegocio
  Usuario <|-- PerfilDeportista
  Contrato "1" --> "1" FondoGarantia
  Contrato "1" --> "1..*" MetaContrato
  MetaContrato "1" --> "0..*" Evidencia
  MetaContrato "1" --> "0..1" Transaccion
  Usuario "1" --> "0..*" Notificacion
  Evidencia "1" --> "1" ArchivoEvidencia
  class FondoGarantia { montoInicial; montoCongelado; montoLiberado; liberar() }
  class MetaContrato { estado; montoDeportista; montoNegocio; enviarEvidencia(); pagar() }
  class Evidencia { numeroIntento; estado; aprobar(); rechazar(motivo) }
```

## Entidad-relación actualizado

```mermaid
erDiagram
  USUARIOS ||--o| PERFIL_NEGOCIO : posee
  USUARIOS ||--o| PERFIL_DEPORTISTA : posee
  USUARIOS ||--o{ CONTRATOS : negocio
  USUARIOS ||--o{ CONTRATOS : deportista
  CONTRATOS ||--|{ METAS_CONTRATO : contiene
  CONTRATOS ||--|| FONDO_GARANTIA : congela
  METAS_CONTRATO ||--o{ EVIDENCIAS : recibe
  EVIDENCIAS ||--|| ARCHIVOS_EVIDENCIA : almacena
  METAS_CONTRATO ||--o| TRANSACCIONES : liquida
  USUARIOS ||--o{ NOTIFICACIONES : recibe
  USUARIOS ||--o{ AUDITORIA_ACCIONES : ejecuta
```

Claves de consistencia: `UNIQUE(meta,intento)`, índice único parcial de evidencia `EN_REVISION`, `UNIQUE transaccion(meta)`, montos/tamaño positivos, FK y locks/versiones.

## Estado de una meta

```mermaid
stateDiagram-v2
  [*] --> PENDIENTE
  PENDIENTE --> EN_REVISION: cargar evidencia
  EN_REVISION --> RECHAZADA: rechazar con motivo
  RECHAZADA --> EN_REVISION: reenviar evidencia
  EN_REVISION --> PAGADA: aprobar + liberar fondo
  PAGADA --> [*]
```

El contrato pasa de activo a finalizado cuando todas sus metas quedan pagadas.
