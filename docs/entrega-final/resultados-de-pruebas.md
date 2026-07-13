# Resultados de pruebas

Fecha/entorno: 2026-07-13, Windows, OpenJDK 25 ejecutando bytecode Java 21, Maven 3.9.16, H2 2.3 en modo PostgreSQL.

- Comando: `mvn clean verify`.
- Resultado final registrado: 20 pruebas, 20 aprobadas, 0 fallos, 0 errores, 0 omitidas.
- JaCoCo: líneas globales 1,213/1,525 = **79.54%**; líneas del núcleo crítico 302/329 = **91.79%**.
- Regla: mínimo 80% de línea por clase crítica; `verify` aprobó.
- Smoke: health `UP`, OpenAPI 200 y 50/50 consultas secuenciales de health con 200.
- JAR final generado: 67,321,218 bytes.

Reportes locales: `target/surefire-reports/`, `target/site/jacoco/index.html` y `target/dependency-tree.txt`.

Limitaciones: H2 no sustituye completamente PostgreSQL; no hubo Docker, navegador E2E ni infraestructura desplegada. No se afirma latencia, disponibilidad ni ausencia total de vulnerabilidades.
