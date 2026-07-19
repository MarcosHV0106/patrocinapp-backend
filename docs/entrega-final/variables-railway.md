# Variables Railway

| Variable | Obligatoria | Ejemplo no secreto / criterio |
|---|---:|---|
| `SPRING_PROFILES_ACTIVE` | Sí | `prod` |
| `SPRING_DATASOURCE_URL` | Sí | JDBC PostgreSQL proporcionado por Railway |
| `SPRING_DATASOURCE_USERNAME` | Sí | usuario administrado |
| `SPRING_DATASOURCE_PASSWORD` | Sí | secreto Railway |
| `JWT_SECRET` | Sí | aleatorio, mínimo 32 bytes; no reutilizar demo |
| `JWT_EXPIRATION` | No | `86400000` |
| `CORS_ALLOWED_ORIGINS` | Sí | URL HTTPS exacta de Vercel; lista separada por comas |
| `PLATFORM_COMMISSION_PERCENT` | No | `10.00` |
| `EVIDENCE_MAX_FILE_SIZE_BYTES` | No | `10485760` |
| `EVIDENCE_ALLOWED_TYPES` | No | lista MIME prevista |
| `MAX_FILE_SIZE` / `MAX_REQUEST_SIZE` | No | `10MB` / `12MB` |
| `ROOT_LOG_LEVEL` / `APP_LOG_LEVEL` | No | `INFO` |
| `PORT` | Railway | inyectada por plataforma |

No configurar `JPA_DDL_AUTO=create/update`; producción mantiene `validate`. `FLYWAY_ENABLED` debe permanecer `true` salvo intervención de recuperación documentada.
