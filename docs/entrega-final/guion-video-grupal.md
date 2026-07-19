# Guion de video grupal

Duración sugerida: 15–18 minutos. Cada integrante debe grabar o validar personalmente su sección; no presentar este documento como evidencia de participación.

1. **Problema y objetivos (1 min):** patrocinio deportivo con cumplimiento verificable, trazabilidad y liberación segura del fondo.
2. **Propuesta/Lean Canvas/antecedentes (1 min):** negocio reduce incertidumbre; deportista accede a patrocinio; supuestos de clientes, problema, solución, canales, costos e ingresos por 10% de comisión. Distinguir hipótesis de investigación validada.
3. **Arquitectura y DDD (2 min):** Angular → REST/JWT → puertos/casos de uso/dominio → JPA/PostgreSQL. Explicar agregados Contrato, Meta, Evidencia y Fondo; repositorio, adapter, strategy de storage e inyección.
4. **Diagramas y datos (1 min):** contexto, componentes, paquetes, ER, estados y secuencias versionadas. Flyway V1/V2, índices e idempotencia.
5. **Proceso (1 min):** Scrum como marco planificado; GitFlow real local, tag de respaldo, ramas temáticas y pendientes de PR/revisión auténticos. Event Storming: ContratoCreado → FondoCongelado → EvidenciaEnviada/Rechazada → EvidenciaReenviada → EvidenciaAprobada → PagoRegistrado → ContratoFinalizado.
6. **Lean UX y accesibilidad (1 min):** hipótesis, flujos por rol, confirmación financiera, motivo, fallback de cámara y validación posterior aún pendiente.
7. **Desarrollo y demo (5 min):** seguir `guion-demostracion-funcional.md`, incluyendo rechazo, reenvío, aprobación, fondo, transacción, historial y notificaciones.
8. **Alternativos/errores (1 min):** formato/tamaño, 401/403, rechazo vacío, doble aprobación y saldo insuficiente.
9. **Integración, QA y métricas (2 min):** 18/18 backend y 21/21 frontend en la medición previa; mostrar reportes JaCoCo/V8, no memorizar métricas si cambian en validación final.
10. **CI/despliegue (1 min):** Actions sin deploy; Docker/Railway/Vercel preparados; no afirmar entornos activos. AWS es propuesta futura.
11. **Impacto y conclusiones (1 min):** trazabilidad, seguridad financiera y siguientes pasos: PostgreSQL real, E2E/dispositivos, PR/revisiones y despliegue aprobado.

Evidencias en pantalla: historial Git real, tests ejecutados, Swagger/Postman, código/migraciones, UI y docs. No mostrar secretos, terminales con credenciales ni datos reales.
