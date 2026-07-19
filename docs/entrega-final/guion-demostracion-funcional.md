# Guion de demostración funcional

Preparación: PostgreSQL desechable con perfiles `local,demo`, backend `:8080`, Angular `:4200` y un PNG/JPEG propio menor de 10 MB con logotipo visible. Reiniciar la DB demo para una repetición determinista.

1. **Cuentas:** negocio `marca.demo@patrocinapp.local`; deportista `atleta.demo@patrocinapp.local`; clave local común `PatrocinAppDemo2026!` (prohibida en producción).
2. **Contrato:** abrir `9201`, Marca Demo ↔ Valeria Demo, monto inicial S/ 330, congelado S/ 220, liberado S/ 110.
3. **Evidencia inicial:** como negocio, revisar la evidencia `9402` de la meta `9302` (video de entrenamiento), actualmente `EN_REVISION`.
4. **Rechazo:** seleccionar Rechazar, demostrar que vacío no continúa y usar «El logotipo no aparece durante los primeros segundos».
5. **Reenvío:** ingresar como deportista, leer el motivo, seleccionar/capturar el archivo preparado, preview, comentario y progreso; se crea intento 2.
6. **Aprobación:** volver al negocio, abrir el nuevo intento, confirmar la advertencia financiera y aprobar una vez; repetir la llamada/botón para evidenciar idempotencia/conflicto.
7. **Tablas que cambian:** `evidencias`, `archivos_evidencia`, `metas_contrato`, `fondo_garantia`, `transacciones`, `notificaciones`, `auditoria_acciones`; `contratos` solo finaliza cuando todas las metas están pagadas.
8. **Transacción:** una fila única por `id_meta_contrato=9302`, neto S/ 100 y comisión S/ 10.
9. **Fondo:** congelado pasa de S/ 220 a S/ 110; liberado de S/ 110 a S/ 220; inicial queda S/ 330.
10. **Notificaciones:** negocio recibe envío; deportista recibe rechazo y luego aprobación/pago. Marcar una como leída.
11. **Alternativos:** archivo vacío/no permitido/>10 MB, token ausente/expirado, contrato ajeno, rechazo sin motivo, aprobación doble y saldo insuficiente (estos últimos pueden mostrarse con pruebas automatizadas para no alterar la demo).

Consultas de apoyo, únicamente en DB demo: filtrar por IDs `9201`, `9302` y ordenar evidencias/notificaciones/auditoría por fecha. No editar manualmente los saldos durante la presentación.
