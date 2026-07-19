# Guion individual — Marcos Vasquez, QA/base de datos

Explicar tras reproducir: matriz basada en riesgos, dominio/integración/concurrencia/rollback, cobertura sin inflación, Flyway, índices, FK, únicos y semillas demo.

- Archivos: V1/V2, `R__semillas_demo.sql`, `pom.xml`, pruebas backend/frontend y docs de resultados/defectos.
- Ejecuciones requeridas: `mvn clean verify`, `npm ci && npm run test:coverage && npm run build`, migración en PostgreSQL desechable y guion E2E.
- Preparación existente no atribuida a QA: `fd42d2a`, `828931b`, `d39bf32`.
- Commit/Issue/PR/revisión e informe personal: `[PENDIENTE: IDs reales]`.
- Decisión: unique de transacción por meta y prueba concurrente como defensa complementaria a locks.
