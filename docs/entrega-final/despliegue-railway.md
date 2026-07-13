# Despliegue posterior en Railway

Estado: **preparado, no desplegado**.

1. Crear PostgreSQL administrado y un servicio desde el repositorio backend.
2. Seleccionar Dockerfile o permitir que `railway.json` ejecute `./mvnw -DskipTests package` y el JAR.
3. Configurar todas las variables de `variables-railway.md`; generar un JWT secreto nuevo.
4. Desplegar primero en un entorno de ensayo con copia anonimizada o DB vacía.
5. Verificar `/actuator/health`, `/api-docs`, login de ambos roles y flujo rechazo→reenvío→aprobación.
6. Revisar Flyway (`flyway_schema_history`), logs sin secretos y CORS con el dominio Vercel.
7. Solo después promover el mismo commit/tag aprobado.

Railway entrega `PORT`; Spring confía en cabeceras del proxy. El JAR usa usuario no root dentro de la imagen y healthcheck. Las evidencias del MVP residen en PostgreSQL `BYTEA`, no en filesystem efímero. Para escala se recomienda object storage.

No se automatizó despliegue: Actions solo integra y publica artefactos de prueba.
