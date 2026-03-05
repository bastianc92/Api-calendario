-- Países
INSERT INTO pais (id, codigo, nombre, descripcion)
VALUES (1, 'COL', 'Colombia', 'País Colombia');

-- Tipos de festivo
INSERT INTO tipo_festivo (id, nombre, descripcion, es_feriado) 
VALUES (1, 'Nacional', 'Festivo de carácter nacional', true);
INSERT INTO tipo_festivo (id, nombre, descripcion, es_feriado) 
VALUES (2, 'Religioso', 'Festivo religioso', true);

-- Festivos Colombia 2023 (solo los del ejemplo del examen)
INSERT INTO festivo (id, fecha, nombre, descripcion, pais_id, tipo_festivo_id)
VALUES (3, '2023-01-01', 'Año Nuevo', 'Año nuevo en Colombia', 1, 1);

INSERT INTO festivo (id, fecha, nombre, descripcion, pais_id, tipo_festivo_id)
VALUES (4, '2023-01-09', 'Santos Reyes', 'Trasladado al lunes por Ley de Puente festivo', 1, 2);

INSERT INTO festivo (id, fecha, nombre, descripcion, pais_id, tipo_festivo_id)
VALUES (5, '2023-03-20', 'San José', 'Trasladado al lunes por Ley de Puente festivo', 1, 2);

INSERT INTO festivo (id, fecha, nombre, descripcion, pais_id, tipo_festivo_id)
VALUES (6, '2023-04-06', 'Jueves Santo', 'Festivo religioso basado en Pascua', 1, 2);

-- Tipos generales
INSERT INTO tipo (id, nombre, descripcion, codigo) 
VALUES (1, 'Laboral', 'Día laboral', 'LAB');
INSERT INTO tipo (id, nombre, descripcion, codigo) 
VALUES (2, 'No Laboral', 'Día no laboral', 'NOLAB');

-- Calendario (ejemplo de carga mínima)
INSERT INTO calendario (id, fecha, es_laboral, observaciones, pais_id, tipo_id) 
VALUES (1, '2023-01-02', true, 'Día laboral normal', 1, 1);
INSERT INTO calendario (id, fecha, es_laboral, observaciones, pais_id, tipo_id) 
VALUES (2, '2023-01-08', false, 'Domingo no laboral', 1, 2);