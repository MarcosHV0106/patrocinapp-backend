# Matriz de pruebas

Responsable ejecutor registrado: identidad local MarcosHV0106. QA debe reproducir desde su propia cuenta.

| ID | Módulo | Tipo | Precondición / pasos resumidos | Esperado | Obtenido | Estado | Evidencia |
|---|---|---|---|---|---|---|---|
| BE-01 | Archivo | Unitario | PNG/PDF con firma real | metadatos y SHA-256 | Coincide | Pasa | `ValidadorArchivoEvidenciaTest` |
| BE-02 | Archivo | Unitario | vacío, >10 MB, MIME/firma/tamaño inconsistentes | rechazo seguro | Códigos esperados | Pasa | `ValidadorArchivoEvidenciaTest` |
| BE-03 | Evidencia | Dominio | revisar/rechazar sin motivo/revisar dos veces | invariantes | Excepciones esperadas | Pasa | `FlujoFinancieroDomainTest` |
| BE-04 | Fondo | Dominio | liberar válido e insuficiente | saldo correcto/no negativo | Coincide | Pasa | `FlujoFinancieroDomainTest` |
| BE-05 | Flujo completo | Integración | login, subir, rechazar, reenviar y aprobar | una transacción y meta pagada | Coincide | Pasa | `FlujoEvidenciasIntegrationTest` |
| BE-06 | Autorización | Integración | recurso ajeno y rol incorrecto | 403 | Coincide | Pasa | `FlujoEvidenciasIntegrationTest` |
| BE-07 | JWT | Integración | token ausente/inválido/expirado | 401 | Coincide | Pasa | `FlujoEvidenciasIntegrationTest` |
| BE-08 | Concurrencia | Integración | dos aprobaciones simultáneas | solo un pago | Coincide | Pasa | `FlujoEvidenciasIntegrationTest` |
| BE-09 | Atomicidad | Integración | excepción al final de aprobación | rollback total | Coincide | Pasa | `AprobacionRollbackIntegrationTest` |
| BE-10 | Aplicación | Smoke/carga básica | contexto, health, OpenAPI y 50 health secuenciales | arranque, documentos y 50 respuestas 200 | Coincide | Pasa | `PatrocinappApplicationTests` |
| BE-11 | PostgreSQL/Flyway | Integración real | contenedor o instancia desechable | V1/V2 aplican y constraints validan | No ejecutado: Docker ausente | Pendiente | ejecutar antes de Railway |
| BE-12 | E2E navegador/API | Sistema | ambos servicios y PostgreSQL | flujo visual completo | No ejecutado en entorno desplegado | Pendiente | guion funcional |
