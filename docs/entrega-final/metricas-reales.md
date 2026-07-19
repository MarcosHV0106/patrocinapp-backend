# Métricas reales

Medición final: 2026-07-13, Windows, Java 25.0.1 ejecutando release 21, Maven 3.9.16. Objetivos son referencias del encargo, no resultados asumidos.

| Medida | Objetivo | Resultado medido | Herramienta / evidencia | Limitación |
|---|---:|---:|---|---|
| Pruebas backend | críticas en verde | 20/20, 0 fallos/errores/omitidas | Maven Surefire `clean verify` | H2, no PostgreSQL real |
| Cobertura global de líneas | medir, sin inflar | 79.54% (1,213/1,525) | JaCoCo CSV/HTML | global sin umbral forzado |
| Cobertura núcleo crítico | ≥80% por clase | agregado 91.79% (302/329); regla por clase aprobada | JaCoCo check | no representa E2E |
| Health/OpenAPI | disponibles | `UP` y OpenAPI 200 | MockMvc + Spring Security | contexto embebido, no red real |
| Carga básica | registrar éxito | 50/50 health secuenciales con 200 | JUnit/MockMvc | no mide throughput, percentiles ni concurrencia de red |
| Empaquetado | build reproducible | JAR 67,321,218 bytes | Maven/Spring Boot | tamaño incluye dependencias |
| Dependencias Maven | árbol resoluble | generado correctamente | `mvn dependency:tree` | no es un escáner CVE; Dependabot queda configurado |
| Docker/Compose | validar | no ejecutado | `Get-Command docker`: no instalado/PATH | bloquea PostgreSQL/Testcontainers/imagen |

No se midieron latencia HTTP real, carga concurrente distribuida, disponibilidad, migración sobre PostgreSQL ni vulnerabilidades Maven con un scanner CVE. No se afirma «cero vulnerabilidades».
