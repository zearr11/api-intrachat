
-- ========================================
-- INSERCIÓN DE ARCHIVOS GENERALES
-- ========================================

INSERT INTO archivos (nombre, tamanio, tipo, url)
VALUES 
('icon-7797704_1280.png', 38450, 'image/png', 'https://res.cloudinary.com/dgsgtffmx/image/upload/v1762473469/icon-7797704_1280_gb9hon.png'),
('icon-group_iowifr.jpg', 23099, 'image/jpg', 'https://res.cloudinary.com/dgsgtffmx/image/upload/v1764307547/icon-group_iowifr.jpg');

-- ========================================
-- INSERCIÓN DE PERSONAS
-- ========================================

INSERT INTO personas (apellidos, celular, genero, informacion, nombres, numero_doc, tipo_doc)
VALUES 
('Campos Ruiz', '987654321', 'MASCULINO', 'Disponible', 'Elias', '70894563', 'DNI'),
('Salazar Peña', '945123678', 'FEMENINO', 'Disponible', 'Lucia', '71984256', 'DNI'),
('Mendoza Torres', '912345678', 'MASCULINO', 'Disponible', 'Carlos', '72894561', 'DNI'),
('Ramirez Vega', '913456789', 'FEMENINO', 'Disponible', 'Ana', 'CE100238947', 'CE'),
('Lopez Huaman', '914567890', 'MASCULINO', 'Disponible', 'Jorge', '73928456', 'DNI'),
('Paredes Núñez', '915678901', 'FEMENINO', 'Disponible', 'María', 'CE9823745612', 'CE'),
('Quispe Gamarra', '916789012', 'MASCULINO', 'Disponible', 'Luis', '74918263', 'DNI'),
('Reyes Soto', '917890123', 'FEMENINO', 'Disponible', 'Patricia', '75938264', 'DNI'),
('Gómez Aguilar', '918901234', 'MASCULINO', 'Disponible', 'Hugo', 'CE781234567', 'CE'),
('Torres Rojas', '919012345', 'FEMENINO', 'Disponible', 'Verónica', '76918234', 'DNI'),
('Martínez León', '920123456', 'MASCULINO', 'Disponible', 'Andrés', '77928345', 'DNI'),
('Fernández Cruz', '921234567', 'FEMENINO', 'Disponible', 'Elena', 'CE8839475621', 'CE'),
('Ruiz Ramos', '922345678', 'MASCULINO', 'Disponible', 'Mario', '78923465', 'DNI'),
('Pérez Mendoza', '923456789', 'FEMENINO', 'Disponible', 'Daniela', '79918273', 'DNI'),
('Cruz Poma', '924567890', 'MASCULINO', 'Disponible', 'Ricardo', 'CE100284956', 'CE'),
('Flores Arias', '925678901', 'FEMENINO', 'Disponible', 'Claudia', '80918364', 'DNI'),
('Vargas Cueva', '926789012', 'MASCULINO', 'Disponible', 'José', '81927354', 'DNI'),
('Díaz Chávez', '927890123', 'FEMENINO', 'Disponible', 'Carmen', '82917364', 'DNI'),
('Gutiérrez Peña', '928901234', 'MASCULINO', 'Disponible', 'Miguel', 'CE9123847562', 'CE'),
('Morales Rivas', '929012345', 'FEMENINO', 'Disponible', 'Laura', '83917245', 'DNI'),
('Silva Campos', '930123456', 'MASCULINO', 'Disponible', 'Oscar', '84917236', 'DNI'),
('Herrera Salas', '931234567', 'FEMENINO', 'Disponible', 'Rosa', '85927346', 'DNI');

-- ========================================
-- INSERCIÓN DE USUARIOS
-- ========================================

INSERT INTO usuarios (email, estado, fecha_creacion, password, rol, cargo, ultima_modificacion, fk_id_archivo, fk_id_persona)
VALUES
('campp123@gmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'ADMIN', 'ADMIN', NOW(), 1, 1),
('luciasp@gmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'JEFE_DE_OPERACION', NOW(), 1, 2),
('carlosmt@gmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'SUPERVISOR', NOW(), 1, 3),
('anav@hotmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'SUPERVISOR', NOW(), 1, 4),
('jorge.lh@gmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'COLABORADOR', NOW(), 1, 5),
('maria.pn@hotmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'COLABORADOR', NOW(), 1, 6),
('luis.qg@gmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'COLABORADOR', NOW(), 1, 7),
('paty.rs@hotmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'COLABORADOR', NOW(), 1, 8),
('hugo.ga@gmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'COLABORADOR', NOW(), 1, 9),
('vero.tr@hotmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'COLABORADOR', NOW(), 1, 10),
('andres.ml@gmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'ADMIN', 'ADMIN', NOW(), 1, 11),
('elena.fc@hotmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'COLABORADOR', NOW(), 1, 12),
('mario.rr@gmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'COLABORADOR', NOW(), 1, 13),
('daniela.pm@hotmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'JEFE_DE_OPERACION', NOW(), 1, 14),
('ricardo.cp@gmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'SUPERVISOR', NOW(), 1, 15),
('claudia.fa@hotmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'JEFE_DE_OPERACION', NOW(), 1, 16),
('jose.vc@gmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'SUPERVISOR', NOW(), 1, 17),
('carmen.dc@hotmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'SUPERVISOR', NOW(), 1, 18),
('miguel.gp@gmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'JEFE_DE_OPERACION', NOW(), 1, 19),
('laura.mr@hotmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'COLABORADOR', NOW(), 1, 20),
('oscar.sc@gmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'ADMIN', 'ADMIN', NOW(), 1, 21),
('rosa.hs@hotmail.com', true, NOW(), '$2a$12$BMBozxZ8c0n8RziyeY8ao.0Plu4N2VZnwUe9oGyXDrVM5mVeznJ3q', 'USUARIO', 'COLABORADOR', NOW(), 1, 22);

-- ========================================
-- INSERCIÓN DE EMPRESAS
-- ========================================

INSERT INTO empresas (razon_social, nombre_comercial, ruc, correo, telefono, estado) VALUES
('Telefónica del Perú S.A.A.', 'Movistar', '20123456781', 'contacto@movistar.pe', '014800123', TRUE),
('América Móvil Perú S.A.C.', 'Claro', '20456789123', 'corporativo@claro.com.pe', '014900456', TRUE),
('Entel Perú S.A.', 'Entel', '20678912345', 'clientes@entel.pe', '016300789', TRUE),
('Viettel Perú S.A.C.', 'Bitel', '20543219876', 'contacto@bitel.com.pe', '016700321', TRUE),
('Banco de Crédito del Perú (BCP)', 'BCP', '20111122334', 'empresas@bcp.com.pe', '014220000', TRUE),
('Interbank', 'Interbank', '20222233445', 'contacto@interbank.pe', '014400111', TRUE),
('BBVA Perú', 'BBVA', '20333344556', 'corporativo@bbva.pe', '014500222', TRUE),
('Scotiabank Perú S.A.A.', 'Scotiabank', '20444455667', 'info@scotiabank.pe', '014600333', TRUE),
('Mapfre Perú Compañía de Seguros y Reaseguros', 'Mapfre', '20555566778', 'contacto@mapfre.com.pe', '015000444', TRUE),
('Pacífico Compañía de Seguros y Reaseguros', 'Pacífico Seguros', '20666677889', 'servicios@pacifico.com.pe', '015100555', TRUE),
('Luz del Sur S.A.A.', 'Luz del Sur', '20777788990', 'corporativo@luzdelsur.pe', '016200666', TRUE),
('Enel Distribución Perú S.A.A.', 'Enel', '20888899001', 'contacto@enel.pe', '016300777', TRUE),
('Saga Falabella S.A.', 'Saga Falabella', '20999900112', 'empresas@sagafalabella.com.pe', '017000888', TRUE),
('Rímac Seguros y Reaseguros', 'Rímac Seguros', '20101010223', 'corporativo@rimac.com.pe', '017200999', TRUE),
('Clínica Internacional S.A.', 'Clínica Internacional', '20202020334', 'contacto@clinicainternacional.pe', '017301111', TRUE);

-- ========================================
-- INSERCIÓN DE CAMPAÑAS
-- ========================================

INSERT INTO campanias (
    area_atencion, estado, fecha_creacion, medio_comunicacion, 
    nombre, ultima_modificacion, fk_id_empresa
) VALUES
('ATENCION_AL_CLIENTE', 1, NOW(), 'LLAMADAS', 'Movistar Atención Al Cliente (Llamadas)', NOW(), 1),
('RETENCIONES', 1, NOW(), 'LLAMADAS', 'Claro Retenciones (Llamadas)', NOW(), 2),
('VENTAS', 1, NOW(), 'LLAMADAS', 'Entel Ventas (Llamadas)', NOW(), 3),
('SOPORTE_TECNICO', 1, NOW(), 'LLAMADAS', 'Bitel Soporte Técnico (Llamadas)', NOW(), 4),

('COBRANZAS', 1, NOW(), 'CANALES_ESCRITOS', 'Banco de Crédito del Perú Cobranzas (Canales Escritos)', NOW(), 5),
('ATENCION_AL_CLIENTE', 1, NOW(), 'CANALES_ESCRITOS', 'Interbank Atención Al Cliente (Canales Escritos)', NOW(), 6),
('VENTAS', 1, NOW(), 'CORREO', 'BBVA Perú Ventas (Correo)', NOW(), 7),
('ATENCION_AL_CLIENTE', 1, NOW(), 'LLAMADAS', 'Scotiabank Atención Al Cliente (Llamadas)', NOW(), 8),

('COBRANZAS', 1, NOW(), 'CANALES_ESCRITOS', 'Mapfre Seguros Cobranzas (Canales Escritos)', NOW(), 9),
('ATENCION_AL_CLIENTE', 1, NOW(), 'LLAMADAS', 'Pacífico Seguros Atención Al Cliente (Llamadas)', NOW(), 10),
('COBRANZAS', 1, NOW(), 'CANALES_ESCRITOS', 'Luz del Sur Cobranzas (Canales Escritos)', NOW(), 11),
('ATENCION_AL_CLIENTE', 1, NOW(), 'LLAMADAS', 'Enel Distribución Perú Atención Al Cliente (Llamadas)', NOW(), 12),

('VENTAS', 1, NOW(), 'CORREO', 'Saga Falabella Ventas (Correo)', NOW(), 13),
('ATENCION_AL_CLIENTE', 1, NOW(), 'LLAMADAS', 'Rimac Seguros Atención Al Cliente (Llamadas)', NOW(), 14),
('ATENCION_AL_CLIENTE', 1, NOW(), 'LLAMADAS', 'Clínica Internacional Atención Al Cliente (Llamadas)', NOW(), 15);

/*
INSERT INTO sedes (departamento, direccion, distrito, estado, nombre, numero_postal, provincia)
VALUES
('Lima', 'Avenida Separadora Industrial, 2089', 'Ate Vitarte', true, 'Lima Ate', 15023, 'Lima');
*/

INSERT INTO sedes (direccion, estado, nombre, numero_postal)
VALUES
('Avenida Separadora Industrial, 2089, Ate Vitarte', true, 'Lima Ate', 15023);


-- 2 es jefe de operacion
INSERT INTO operaciones (fecha_creacion, fk_id_campania, fk_id_jefe_operacion, fk_id_sede)
VALUES
(NOW(), 1, 2, 1);

INSERT INTO salas (fecha_creacion, tipo_sala)
VALUES
(NOW(), 'GRUPO'),
(NOW(), 'GRUPO');

INSERT INTO grupos (descripcion, estado, nombre, ultima_modificacion, fk_id_archivo, fk_id_sala)
VALUES
('Solo mensajes de la gestión', true, 'Grupo de trabajo 1', NOW(), 2, 1),
('Solo mensajes de la gestión', true, 'Grupo de trabajo 2', NOW(), 2, 2);

-- 3 y 4 son supervisores
INSERT INTO equipos (fecha_creacion, fk_id_grupo, fk_id_operacion, fk_id_supervisor)
VALUES 
(NOW(), 1, 1, 3),
(NOW(), 2, 1, 4);

INSERT INTO equipo_usuarios (estado, fecha_fin, fecha_inicio, permiso, fk_id_equipo, fk_id_usuario)
VALUES
(true, null, NOW(), 'ADMINISTRADOR', 1, 2), -- Jefe de Operacion
(true, null, NOW(), 'ADMINISTRADOR', 2, 2), -- Jefe de Operacion
(true, null, NOW(), 'ADMINISTRADOR', 1, 3), -- Supervisor
(true, null, NOW(), 'ADMINISTRADOR', 2, 4), -- Supervisor
(true, null, NOW(), 'USUARIO_REGULAR', 1, 5), -- Equipo 1 Usuario Regular
(true, null, NOW(), 'USUARIO_REGULAR', 1, 6), -- Equipo 1 Usuario Regular
(true, null, NOW(), 'USUARIO_REGULAR', 1, 7), -- Equipo 1 Usuario Regular
(true, null, NOW(), 'USUARIO_REGULAR', 2, 8), -- Equipo 2 Usuario Regular
(true, null, NOW(), 'USUARIO_REGULAR', 2, 9), -- Equipo 2 Usuario Regular
(true, null, NOW(), 'USUARIO_REGULAR', 2, 10); -- Equipo 2 Usuario Regular

INSERT INTO integrantes (estado, permiso, fk_id_sala, fk_id_usuario)
VALUES
(true, 'ADMINISTRADOR', 1, 2), -- Jefe de Operacion
(true, 'ADMINISTRADOR', 2, 2), -- Jefe de Operacion
(true, 'ADMINISTRADOR', 1, 3), -- Supervisor
(true, 'ADMINISTRADOR', 2, 4), -- Supervisor
(true, 'USUARIO_REGULAR', 1, 5), -- Equipo 1 Usuario Regular
(true, 'USUARIO_REGULAR', 1, 6), -- Equipo 1 Usuario Regular
(true, 'USUARIO_REGULAR', 1, 7), -- Equipo 1 Usuario Regular
(true, 'USUARIO_REGULAR', 2, 8), -- Equipo 2 Usuario Regular
(true, 'USUARIO_REGULAR', 2, 9), -- Equipo 2 Usuario Regular
(true, 'USUARIO_REGULAR', 2, 10); -- Equipo 2 Usuario Regular



































