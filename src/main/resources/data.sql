-- Países
INSERT INTO pais (id, codigo, nombre, descripcion)
VALUES (1, 'COL', 'Colombia', 'País Colombia');

INSERT INTO pais (id, codigo, nombre, descripcion)
VALUES (2, 'ESP', 'España', 'País España');

-- Tipos de festivo
INSERT INTO tipo_festivo (id, nombre, descripcion, es_feriado) 
VALUES (1, 'Nacional', 'Festivo de carácter nacional', true);
INSERT INTO tipo_festivo (id, nombre, descripcion, es_feriado) 
VALUES (2, 'Religioso', 'Festivo religioso', true);

-- Festivos
INSERT INTO festivo (id, fecha, nombre, descripcion, pais_id, tipo_festivo_id) 
VALUES (1, '2026-12-25', 'Navidad', 'Navidad en Colombia', 1, 1);

INSERT INTO festivo (id, fecha, nombre, descripcion, pais_id, tipo_festivo_id) 
VALUES (2, '2026-01-01', 'Año Nuevo', 'Año nuevo en España', 2, 1);

-- Tipos generales
INSERT INTO tipo (id, nombre, descripcion, codigo) 
VALUES (1, 'Laboral', 'Día laboral', 'LAB');
INSERT INTO tipo (id, nombre, descripcion, codigo) 
VALUES (2, 'No Laboral', 'Día no laboral', 'NOLAB');

-- Calendario
INSERT INTO calendario (id, fecha, es_laboral, observaciones, pais_id, tipo_id) 
VALUES (1, '2026-03-03', true, 'Día laboral normal', 1, 1);
INSERT INTO calendario (id, fecha, es_laboral, observaciones, pais_id, tipo_id) 
VALUES (2, '2026-03-01', false, 'Domingo no laboral', 1, 2);