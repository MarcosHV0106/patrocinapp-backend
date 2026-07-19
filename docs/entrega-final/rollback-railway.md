# Rollback Railway

1. Detener promociones y conservar logs/ID del deployment fallido.
2. Si el código falla sin cambio de datos incompatible, usar Redeploy sobre el último deployment/commit verificado.
3. Ejecutar smoke de health, OpenAPI, login y lectura antes de reabrir tráfico.
4. Si Flyway ya aplicó una migración, **no** borrar filas de `flyway_schema_history` ni usar `clean`. Crear una migración forward correctiva o restaurar un backup PostgreSQL probado según impacto.
5. Rotar secretos si el incidente los expuso y actualizar Vercel si cambia el origen/API.

Antes de desplegar V2: snapshot/backup, ensayo de restauración y ventana de cambio. La estrategia de rollback de base es restauración/forward-fix coordinado; no existe down migration automática.
