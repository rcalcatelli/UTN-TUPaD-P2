-- ============================================================================
-- Script de Creación de Base de Datos - Sistema Usuario y CredencialAcceso
-- ============================================================================
-- Descripción: Este script crea la base de datos y las tablas necesarias
--              para el sistema de gestión de usuarios con credenciales de acceso.
--              
-- DECISIÓN DE DISEÑO IMPORTANTE:
-- Implementamos la relación 1→1 poniendo la FK en credencial_acceso que apunta
-- a usuario. Esto significa que:
-- 1. En la BD: credencial_acceso → usuario (tiene la FK)
-- 2. En Java: Usuario → CredencialAcceso (navegación unidireccional)
-- 
-- Esta diferencia es NORMAL y CORRECTA. El DAO hace la traducción.
-- ============================================================================

-- Eliminar la base de datos si existe (útil para desarrollo y pruebas)
DROP DATABASE IF EXISTS sistema_usuarios;

-- Crear la base de datos con codificación UTF-8 para soportar caracteres especiales
CREATE DATABASE sistema_usuarios 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos recién creada
USE sistema_usuarios;

-- ============================================================================
-- TABLA: usuario
-- ============================================================================
-- Esta tabla debe crearse PRIMERO porque credencial_acceso va a tener
-- una FK que apunta hacia ella.
-- 
-- Representa a los usuarios del sistema. Cada usuario tendrá exactamente
-- una credencial de acceso asociada (relación 1→1).
-- ============================================================================

CREATE TABLE usuario (
    -- Clave primaria autoincrementable
    -- El AUTO_INCREMENT empieza en 1 y se incrementa automáticamente
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    
    -- Baja lógica: permite "eliminar" registros sin borrarlos físicamente
    -- FALSE = usuario activo, TRUE = usuario eliminado lógicamente
    -- Esto nos permite mantener historial y recuperar usuarios si es necesario
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    
    -- Nombre de usuario para login (debe ser único en el sistema)
    -- NOT NULL porque es obligatorio para poder identificar al usuario
    username VARCHAR(50) NOT NULL,
    
    -- Correo electrónico (debe ser único en el sistema)
    -- VARCHAR(100) es suficiente para la mayoría de emails
    email VARCHAR(100) NOT NULL,
    
    -- Nombre completo del usuario
    -- Puede contener espacios, acentos, etc. (por eso usamos utf8mb4)
    nombre_completo VARCHAR(100) NOT NULL,
    
    -- Indica si el usuario está activo en el sistema
    -- TRUE = puede usar el sistema, FALSE = cuenta deshabilitada
    -- Es diferente de "eliminado": un usuario puede estar inactivo pero no eliminado
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    
    -- Fecha y hora de registro en el sistema
    -- DEFAULT CURRENT_TIMESTAMP se asigna automáticamente al insertar
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraints de unicidad
    -- Estos garantizan que no haya duplicados en campos que deben ser únicos
    CONSTRAINT uq_usuario_username UNIQUE (username),
    CONSTRAINT uq_usuario_email UNIQUE (email),
    
    -- Índices para mejorar el rendimiento de las consultas
    -- Los creamos en campos que se usan frecuentemente en WHERE, ORDER BY, etc.
    INDEX idx_usuario_username (username),
    INDEX idx_usuario_email (email),
    INDEX idx_usuario_activo (activo),
    INDEX idx_usuario_eliminado (eliminado)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLA: credencial_acceso
-- ============================================================================
-- Esta tabla se crea DESPUÉS de usuario porque tiene una FK que apunta hacia ella.
-- 
-- IMPLEMENTACIÓN DE LA RELACIÓN 1→1:
-- La columna usuario_id es:
-- 1. FOREIGN KEY hacia usuario(id) - establece la relación
-- 2. UNIQUE - garantiza que una credencial solo puede pertenecer a UN usuario
-- 3. ON DELETE CASCADE - si eliminamos un usuario, su credencial se elimina
--
-- Esta estructura garantiza que:
-- - Cada credencial pertenece exactamente a un usuario (por la FK)
-- - Un usuario puede tener máximo una credencial (por el UNIQUE)
-- ============================================================================

CREATE TABLE credencial_acceso (
    -- Clave primaria autoincrementable
    -- Esta credencial tiene su propio ID independiente del usuario
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    
    -- Baja lógica: permite "eliminar" registros sin borrarlos físicamente
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    
    -- FOREIGN KEY hacia usuario: esta es la columna que implementa la relación
    -- NOT NULL porque una credencial DEBE pertenecer a un usuario
    -- El UNIQUE garantiza que no puede haber dos credenciales para el mismo usuario
    usuario_id BIGINT NOT NULL,
    
    -- Hash de la contraseña (NUNCA almacenar contraseñas en texto plano)
    -- Se usa BCrypt, SHA-256, Argon2, o similar para generar el hash
    -- Ejemplo de hash BCrypt: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
    hash_password VARCHAR(255) NOT NULL,
    
    -- Salt: valor aleatorio usado en el proceso de hashing
    -- Mejora la seguridad al hacer que hashes iguales de la misma contraseña sean diferentes
    -- En BCrypt el salt está incluido en el hash, pero podemos guardarlo por separado
    salt VARCHAR(64),
    
    -- Fecha y hora del último cambio de contraseña
    -- Útil para implementar políticas de expiración de contraseñas
    -- NULL al principio, se actualiza cuando el usuario cambia su contraseña
    ultimo_cambio DATETIME,
    
    -- Indica si el usuario debe cambiar su contraseña en el próximo login
    -- TRUE = debe cambiar contraseña (ej: contraseña temporal o vencida)
    -- FALSE = no requiere cambio
    requiere_reset BOOLEAN NOT NULL DEFAULT FALSE,
    
    -- Número de intentos de login fallidos consecutivos
    -- Se usa para implementar bloqueo de cuenta tras muchos intentos fallidos
    -- Se resetea a 0 tras un login exitoso
    intentos_fallidos INT NOT NULL DEFAULT 0,
    
    -- Fecha y hora del último login exitoso
    -- NULL si el usuario nunca hizo login
    ultimo_login DATETIME,
    
    -- Constraint de FOREIGN KEY
    -- ON DELETE CASCADE: si eliminamos el usuario, su credencial se elimina automáticamente
    -- ON UPDATE CASCADE: si cambiamos el ID del usuario, se actualiza automáticamente aquí
    CONSTRAINT fk_credencial_usuario 
        FOREIGN KEY (usuario_id) 
        REFERENCES usuario(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    
    -- Constraint de UNIQUE: esto es lo que garantiza la relación 1→1
    -- Solo puede haber UNA credencial por usuario
    CONSTRAINT uq_credencial_usuario UNIQUE (usuario_id),
    
    -- Índices para mejorar el rendimiento
    INDEX idx_credencial_usuario (usuario_id),
    INDEX idx_credencial_eliminado (eliminado)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- Verificación de la estructura creada
-- ============================================================================
-- Estos comandos te permiten verificar que las tablas se crearon correctamente
-- Podés ejecutarlos después de correr este script

-- SHOW TABLES;
-- DESCRIBE usuario;
-- DESCRIBE credencial_acceso;
-- SHOW CREATE TABLE credencial_acceso;