# Estrategia de pruebas

Pirámide aplicada: dominio puro para transiciones y dinero; aplicación para validación de archivos; integración con Spring Security/MockMvc/H2 en modo PostgreSQL para API, transacción y concurrencia; verificación de build con Maven/JaCoCo. El pipeline ejecuta `mvn -B clean verify` en Java 21 y conserva Surefire, JaCoCo y JAR.

Riesgos prioritarios: acceso a contrato ajeno, archivo malicioso o sobredimensionado, rechazo sin motivo, doble pago, saldo negativo, pérdida de atomicidad y JWT inválido/ausente. El núcleo crítico tiene regla JaCoCo de 80% por clase configurada en `pom.xml`; no se excluyen esas clases para inflar cobertura.

H2 está aislado en `src/test/resources/application-test.properties`. Docker no estaba disponible el 2026-07-13, por lo que Testcontainers/PostgreSQL real queda como validación previa al despliegue. Las migraciones PostgreSQL se inspeccionaron y el pipeline prepara su ejecución, pero no se aplicaron sobre datos reales del usuario.
