-- ============================================================================
-- Script de Datos de Prueba - Sistema Usuario y CredencialAcceso
-- ============================================================================
-- Descripción: Este script inserta datos de prueba para poder probar
--              la aplicación sin necesidad de crear registros manualmente.
--
-- ORDEN CRÍTICO DE INSERCIÓN:
-- 1. Primero insertamos usuarios
-- 2. Después insertamos credenciales que referencian a esos usuarios
--
-- Si intentamos insertar credenciales antes que usuarios, MySQL nos dará
-- un error de violación de integridad referencial (foreign key constraint).
-- ============================================================================

USE sistema_usuarios;

-- ============================================================================
-- PASO 1: Insertar usuarios
-- ============================================================================
-- Insertamos 5 usuarios de prueba con diferentes características
-- Algunos estarán activos, otros inactivos, uno eliminado lógicamente

INSERT INTO usuario (username, email, nombre_completo, activo, eliminado, fecha_registro) VALUES
-- Usuario 1: Usuario activo normal
('jperez', 'juan.perez@email.com', 'Juan Pérez', TRUE, FALSE, '2024-01-15 10:30:00'),

-- Usuario 2: Usuario activo normal  
('mgarcia', 'maria.garcia@email.com', 'María García', TRUE, FALSE, '2024-02-20 14:45:00'),

-- Usuario 3: Usuario inactivo (deshabilitado pero no eliminado)
-- Este caso simula una cuenta suspendida temporalmente
('lrodriguez', 'luis.rodriguez@email.com', 'Luis Rodríguez', FALSE, FALSE, '2024-03-10 09:15:00'),

-- Usuario 4: Usuario activo normal
('alopez', 'ana.lopez@email.com', 'Ana López', TRUE, FALSE, '2024-04-05 16:20:00'),

-- Usuario 5: Usuario eliminado lógicamente (baja lógica)
-- Este usuario ya no debería aparecer en las búsquedas normales
-- pero conservamos su registro para historial
('cmartinez', 'carlos.martinez@email.com', 'Carlos Martínez', FALSE, TRUE, '2024-01-25 11:00:00');

-- ============================================================================
-- PASO 2: Insertar credenciales de acceso
-- ============================================================================
-- Ahora que los usuarios ya existen, podemos crear sus credenciales
-- 
-- IMPORTANTE SOBRE LOS HASHES:
-- En un sistema real, NUNCA guardaríamos contraseñas en texto plano.
-- Los hashes que ves acá son ejemplos simulados. En tu aplicación Java
-- vas a usar una librería como BCrypt para generar hashes reales.
--
-- Para este ejemplo, vamos a fingir que las contraseñas eran:
-- - jperez: "password123"
-- - mgarcia: "securePass456"
-- - lrodriguez: "myPassword789"
-- - alopez: "ana2024"
-- - cmartinez: "carlos123"
--
-- Los hashes son ficticios para este ejemplo, pero tienen el formato correcto.

INSERT INTO credencial_acceso 
    (usuario_id, hash_password, salt, ultimo_cambio, requiere_reset, intentos_fallidos, ultimo_login, eliminado) 
VALUES
-- Credencial para jperez (usuario_id = 1)
-- Simula un usuario que ha hecho login recientemente sin problemas
(
    1,  -- usuario_id apunta al primer usuario (jperez)
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',  -- hash ficticio
    'a1b2c3d4e5f6',  -- salt ficticio
    '2024-10-01 08:00:00',  -- último cambio de contraseña hace un mes
    FALSE,  -- no requiere reset
    0,  -- sin intentos fallidos
    '2024-11-07 09:30:00',  -- último login ayer
    FALSE  -- credencial activa
),

-- Credencial para mgarcia (usuario_id = 2)
-- Simula un usuario con una contraseña que necesita ser cambiada
(
    2,  -- usuario_id apunta al segundo usuario (mgarcia)
    '$2a$10$xYzAbC123DeFgHiJkLmNoPqRsTuVwXyZ456789aBcDeFgHiJkLmNo',  -- hash ficticio
    'x9y8z7w6v5u4',  -- salt ficticio
    '2024-06-15 14:20:00',  -- contraseña antigua (hace 5 meses)
    TRUE,  -- SÍ requiere reset (contraseña temporal o vencida)
    0,  -- sin intentos fallidos
    '2024-11-05 10:15:00',  -- último login hace 3 días
    FALSE  -- credencial activa
),

-- Credencial para lrodriguez (usuario_id = 3)
-- Usuario inactivo, pero con credencial válida
(
    3,  -- usuario_id apunta al tercer usuario (lrodriguez)
    '$2a$10$qWeRtYuIoPaSdFgHjKlZxCvBnMqWeRtYuIoPaSdFgHjKlZxCvBnM',  -- hash ficticio
    'm1n2b3v4c5x6',  -- salt ficticio
    '2024-09-20 11:45:00',  -- cambió contraseña hace 2 meses
    FALSE,  -- no requiere reset
    0,  -- sin intentos fallidos
    '2024-10-30 16:40:00',  -- último login hace una semana
    FALSE  -- credencial activa (aunque el usuario esté inactivo)
),

-- Credencial para alopez (usuario_id = 4)
-- Simula un usuario con varios intentos de login fallidos (posible ataque)
(
    4,  -- usuario_id apunta al cuarto usuario (alopez)
    '$2a$10$aSdFgHjKlQwErTyUiOpZxCvBnMaSdFgHjKlQwErTyUiOpZxCvBnM',  -- hash ficticio
    'q1w2e3r4t5y6',  -- salt ficticio
    '2024-08-12 13:30:00',  -- cambió contraseña hace 3 meses
    FALSE,  -- no requiere reset
    3,  -- 3 intentos fallidos (cerca del límite de bloqueo)
    '2024-11-06 15:20:00',  -- último login exitoso hace 2 días
    FALSE  -- credencial activa
),

-- Credencial para cmartinez (usuario_id = 5)
-- Usuario y credencial eliminados lógicamente (baja lógica en ambas tablas)
(
    5,  -- usuario_id apunta al quinto usuario (cmartinez)
    '$2a$10$zXcVbNmAsQwErTyUiOpLkJhGfDsAzXcVbNmAsQwErTyUiOpLkJhG',  -- hash ficticio
    'p9o8i7u6y5t4',  -- salt ficticio
    '2024-07-10 10:00:00',  -- cambió contraseña hace 4 meses
    FALSE,  -- no requiere reset
    0,  -- sin intentos fallidos
    '2024-09-15 12:00:00',  -- último login hace 2 meses (antes de ser eliminado)
    TRUE  -- credencial ELIMINADA lógicamente (igual que el usuario)
);

-- ============================================================================
-- Verificación de los datos insertados
-- ============================================================================
-- Estos comandos te permiten verificar que los datos se insertaron correctamente

-- Ver todos los usuarios
-- SELECT * FROM usuario;

-- Ver todas las credenciales
-- SELECT * FROM credencial_acceso;

-- Ver usuarios con sus credenciales (JOIN)
-- Esta consulta muestra cómo se relacionan las tablas
/*
SELECT 
    u.id AS usuario_id,
    u.username,
    u.nombre_completo,
    u.email,
    u.activo AS usuario_activo,
    u.eliminado AS usuario_eliminado,
    c.id AS credencial_id,
    c.requiere_reset,
    c.intentos_fallidos,
    c.ultimo_login,
    c.eliminado AS credencial_eliminada
FROM usuario u
LEFT JOIN credencial_acceso c ON c.usuario_id = u.id
ORDER BY u.id;
*/

-- Ver solo usuarios activos con credenciales activas
/*
SELECT 
    u.username,
    u.nombre_completo,
    c.ultimo_login,
    c.intentos_fallidos
FROM usuario u
INNER JOIN credencial_acceso c ON c.usuario_id = u.id
WHERE u.activo = TRUE 
  AND u.eliminado = FALSE 
  AND c.eliminado = FALSE;
*/
