# Seguridad

Reportar vulnerabilidades de forma privada al responsable del repositorio; no publicar secretos ni datos personales en Issues.

- JWT requiere `JWT_SECRET` externo de al menos 32 bytes y expiración configurable.
- PostgreSQL y CORS se configuran por entorno.
- Los archivos se validan por tamaño, MIME y firma mágica; se descargan tras autorización.
- Los errores públicos no incluyen trazas ni mensajes internos.
- Producción usa `ddl-auto=validate`, Flyway y `flyway.clean-disabled=true`.
- Las evidencias no deben persistirse en el filesystem efímero de Railway; la implementación actual usa PostgreSQL `BYTEA` para el MVP.

Ante exposición de un secreto: revocarlo, rotarlo, revisar auditoría e historial, y desplegar una versión saneada. Nunca reescribir historia compartida sin coordinación.
