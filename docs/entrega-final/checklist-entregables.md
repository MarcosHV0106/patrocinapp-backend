# Checklist de entregables y aceptación

Estado al 2026-07-13 antes de despliegue/revisión externa.

| # | Criterio | Estado | Evidencia / limitación |
|---:|---|---|---|
| 1 | Backend compila limpio | Cumple | `mvn clean verify` |
| 2 | Frontend compila limpio | Cumple | `npm run build` |
| 3 | Sin secretos versionados | Cumple revisado | variables externas y escaneo; repetir secret scan en GitHub |
| 4–6 | Login ambos roles y autorización | Cumple automatizado | integración/JWT; E2E UI pendiente |
| 7–8 | Crear contrato y fondo | Cumple por código/pruebas previas | confirmar E2E real |
| 9 | Carga física | Cumple automatizado | multipart + BYTEA |
| 10 | Cámara con fallback | Cumple unitario | dispositivo real pendiente |
| 11–15 | Ver, rechazar, motivo, reenvío, aprobar | Cumple automatizado | API + componentes |
| 16–18 | Una transacción, fondo correcto, PAGADA | Cumple | integración/concurrencia/rollback |
| 19–21 | Dashboard, notificaciones, historial | Cumple por integración de código | E2E pendiente |
| 22–25 | Ajeno, doble aprobación, no negativo, errores | Cumple automatizado | pruebas críticas |
| 26 | Swagger documenta flujo | Preparado | OpenAPI configurado; smoke/live pendiente |
| 27 | Postman reproduce flujo | Preparado | colección válida; ejecución contra servidor pendiente |
| 28 | Migraciones funcionan | Parcial | Flyway preparado; PostgreSQL desechable no ejecutado por Docker ausente |
| 29–30 | Pruebas y cobertura reales | Cumple | JaCoCo/V8 medidos |
| 31 | GitHub Actions | Preparado | YAML; ejecución remota pendiente |
| 32 | Railway | Preparado | no desplegado |
| 33 | Vercel | Preparado | no desplegado |
| 34 | Docs coinciden con código | Cumple revisado | expediente `docs/entrega-final` |
| 35 | Sin mocks en flujo principal | Cumple | seed solo perfil demo; E2E pendiente |
| 36 | Diseño existente preservado | Cumple | evolución incremental |

Entregables adicionales: READMEs, CHANGELOG, SECURITY, CONTRIBUTING/CONTRIBUTORS, plantillas GitHub, CI, Docker/Compose, Railway/Vercel, Postman, diagramas, guiones y métricas. No se declara completado el despliegue, la revisión grupal ni las validaciones pendientes.
