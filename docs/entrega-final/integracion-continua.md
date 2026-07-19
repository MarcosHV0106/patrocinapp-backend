# Integración continua y secretos

`.github/workflows/integracion-backend.yml` se ejecuta en PR y push a `desarrollo`/principal, usa Java 21 con caché Maven, corre `clean verify`, construye JAR y almacena Surefire/JaCoCo/JAR. Un fallo impide continuar; no existe job de despliegue.

Dependabot revisa Maven y Actions. GitHub debe configurarse manualmente con aprobación cruzada y checks obligatorios; esta preparación no afirma que las protecciones ya estén activas.

Los secretos de producción pertenecen a Railway/GitHub Environments, nunca al repositorio. Separar ensayo/producción, otorgar mínimo privilegio y rotar credenciales periódicamente.
