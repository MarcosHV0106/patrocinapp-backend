-- Datos exclusivamente para el perfil demo. IDs altos y ON CONFLICT hacen el script repetible.
INSERT INTO usuarios (id_usuario, correo, password_hash, rol, fecha_registro) VALUES
  (9001, 'marca.demo@patrocinapp.local', '$2a$10$MLuXC/k2zl7GwKKejcv3j.AfikV7oQxvtFdqr0iUzo35uexDLK1QO', 'NEGOCIO', CURRENT_TIMESTAMP),
  (9002, 'atleta.demo@patrocinapp.local', '$2a$10$MLuXC/k2zl7GwKKejcv3j.AfikV7oQxvtFdqr0iUzo35uexDLK1QO', 'DEPORTISTA', CURRENT_TIMESTAMP)
ON CONFLICT (id_usuario) DO NOTHING;

INSERT INTO perfil_negocio (id_usuario, ruc, razon_social)
VALUES (9001, '20999999991', 'Marca Demo PatrocinApp') ON CONFLICT (id_usuario) DO NOTHING;
INSERT INTO perfil_deportista (id_usuario, nombre_completo, dni, disciplina, biografia)
VALUES (9002, 'Valeria Demo', '79999991', 'Atletismo', 'Perfil local para demostrar evidencias y pagos.')
ON CONFLICT (id_usuario) DO NOTHING;

INSERT INTO plantilla_metas (id_plantilla, nombre_meta, descripcion_sugerida, tipo_entregable, precio_sugerido) VALUES
  (9101, 'Publicación de lanzamiento', 'Publicar fotografía con el logotipo visible.', 'FOTOGRAFIA', 100.00),
  (9102, 'Video de entrenamiento', 'Publicar un video corto durante el entrenamiento.', 'VIDEO', 100.00),
  (9103, 'Mención de campaña', 'Publicar una mención de la campaña.', 'FOTOGRAFIA', 100.00)
ON CONFLICT (id_plantilla) DO NOTHING;

INSERT INTO contratos (id_contrato, id_negocio, id_deportista, monto_total, estado, fecha_creacion, version)
VALUES (9201, 9001, 9002, 330.00, 'ACTIVO', CURRENT_TIMESTAMP, 0)
ON CONFLICT (id_contrato) DO NOTHING;

INSERT INTO metas_contrato (id_meta_contrato, id_contrato, id_plantilla, descripcion_acordada,
  monto_deportista, monto_negocio, comentario_deportista, estado, fecha_actualizacion, version) VALUES
  (9301, 9201, 9101, 'Publicar fotografía con el logotipo visible.', 100.00, 110.00, 'Entrega aprobada de demostración.', 'PAGADA', CURRENT_TIMESTAMP, 0),
  (9302, 9201, 9102, 'Publicar un video corto durante el entrenamiento.', 100.00, 110.00, 'Entrega pendiente de revisión.', 'EN_REVISION', CURRENT_TIMESTAMP, 0),
  (9303, 9201, 9103, 'Publicar una mención de la campaña.', 100.00, 110.00, 'Primera versión enviada.', 'RECHAZADA', CURRENT_TIMESTAMP, 0)
ON CONFLICT (id_meta_contrato) DO NOTHING;

INSERT INTO fondo_garantia (id_contrato, monto_inicial, monto_congelado, monto_liberado, ultima_actualizacion, version)
VALUES (9201, 330.00, 220.00, 110.00, CURRENT_TIMESTAMP, 0)
ON CONFLICT (id_contrato) DO NOTHING;

INSERT INTO evidencias (id_evidencia, id_meta_contrato, numero_intento, nombre_original, tipo_mime,
  tamanio_bytes, hash_sha256, comentario_deportista, estado, motivo_rechazo, fecha_carga,
  fecha_revision, id_usuario_revisor, fecha_actualizacion, version) VALUES
  (9401, 9301, 1, 'meta-pagada.png', 'image/png', 68, '431ced6916a2a21a156e38701afe55bbd7f88969fbbfc56d7fe099d47f265460', 'Entrega aprobada.', 'APROBADA', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 9001, CURRENT_TIMESTAMP, 0),
  (9402, 9302, 1, 'meta-revision.png', 'image/png', 68, '431ced6916a2a21a156e38701afe55bbd7f88969fbbfc56d7fe099d47f265460', 'Por favor revisar.', 'EN_REVISION', NULL, CURRENT_TIMESTAMP, NULL, NULL, CURRENT_TIMESTAMP, 0),
  (9403, 9303, 1, 'meta-rechazada.png', 'image/png', 68, '431ced6916a2a21a156e38701afe55bbd7f88969fbbfc56d7fe099d47f265460', 'Primera versión.', 'RECHAZADA', 'El logotipo no se distingue con claridad.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 9001, CURRENT_TIMESTAMP, 0)
ON CONFLICT (id_evidencia) DO NOTHING;

INSERT INTO archivos_evidencia (id_evidencia, contenido) VALUES
  (9401, decode('89504e470d0a1a0a0000000d4948445200000001000000010804000000b51c0c020000000b4944415478da6364f80f00010501012718e3660000000049454e44ae426082', 'hex')),
  (9402, decode('89504e470d0a1a0a0000000d4948445200000001000000010804000000b51c0c020000000b4944415478da6364f80f00010501012718e3660000000049454e44ae426082', 'hex')),
  (9403, decode('89504e470d0a1a0a0000000d4948445200000001000000010804000000b51c0c020000000b4944415478da6364f80f00010501012718e3660000000049454e44ae426082', 'hex'))
ON CONFLICT (id_evidencia) DO NOTHING;

INSERT INTO transacciones (id_transaccion, id_contrato, id_meta_contrato, monto_neto, comision_plataforma, fecha_ejecucion)
VALUES (9501, 9201, 9301, 100.00, 10.00, CURRENT_TIMESTAMP)
ON CONFLICT (id_transaccion) DO NOTHING;

INSERT INTO notificaciones (id_notificacion, id_usuario, tipo, mensaje, entidad_relacionada, id_entidad, fecha, leida)
VALUES
  (9601, 9002, 'EVIDENCIA_RECHAZADA', 'La evidencia de la tercera meta requiere una corrección.', 'EVIDENCIA', '9403', CURRENT_TIMESTAMP, FALSE),
  (9602, 9001, 'EVIDENCIA_ENVIADA', 'Hay una evidencia pendiente de revisión.', 'EVIDENCIA', '9402', CURRENT_TIMESTAMP, FALSE)
ON CONFLICT (id_notificacion) DO NOTHING;
