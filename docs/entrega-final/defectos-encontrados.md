# Defectos encontrados y tratamiento

| ID | Hallazgo | Riesgo | Corrección / estado |
|---|---|---|---|
| D-01 | Credenciales y JWT con valores sensibles/default inseguros | Crítico | removidos; variables externas y fallo temprano en producción |
| D-02 | Overrides incompatibles de Spring 7 sobre Boot 3.5 | Alto | removidos; BOM de Spring Boot gobierna versiones |
| D-03 | Evidencia era texto/URL y la aprobación confiaba en IDs del request | Crítico | archivo físico, actor JWT y propiedad de recursos |
| D-04 | Aprobación no garantizaba idempotencia, locks ni rollback integral | Crítico | transacción, locks pesimistas, unique por meta y prueba de rollback/concurrencia |
| D-05 | Rechazo/reenvío/historial/notificaciones ausentes | Alto | modelo, puertos, adaptadores, endpoints y UI implementados |
| D-06 | DDL automático sin migraciones controladas | Alto | Flyway V1/V2 y producción con `validate` |
| D-07 | Hash BCrypt inicial de demo no correspondía a la clave documentada | Medio | regenerado con Spring Security y reemplazado antes de integrar |
| D-08 | Docker/Testcontainers no ejecutables en la máquina | Medio | Dockerfile/Compose preparados; validación PostgreSQL real queda bloqueada por ausencia de Docker |

No quedan pruebas fallidas conocidas. Los pendientes D-08 y E2E desplegado están declarados, no ocultos.
