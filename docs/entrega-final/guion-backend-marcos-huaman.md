# Guion individual — Marcos Huaman, backend/liderazgo

Explicar: arquitectura hexagonal/DDD; actor derivado del JWT; almacenamiento y validación física; transacción de aprobación, locks, idempotencia, comisión y rollback; OpenAPI y errores seguros.

- Archivos: `AprobarEvidenciaService`, `EnviarEvidenciaService`, `SecurityConfig`, controladores, `V2__evidencias_auditoria_y_consistencia.sql`.
- Pruebas: `FlujoEvidenciasIntegrationTest`, `AprobacionRollbackIntegrationTest`, JaCoCo.
- Preparación local registrada: commits `3d430c2`, `828931b` de la identidad activa. Confirmar autoría real en Git antes de exponerla.
- PR propio/revisión ajena: `[PENDIENTE: URL/ID real]` / `[PENDIENTE: revisor real]`.
- Decisión central: el request nunca decide identidad/rol; la operación financiera es una sola transacción y la DB impone unicidad.

No atribuir commits a otros integrantes.
