package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.dao.CredencialAccesoDAO;
import ar.edu.utn.frbb.tup.entities.CredencialAcceso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

/**
 * Service para la entidad CredencialAcceso.
 *
 * Esta clase contiene la lógica de negocio relacionada con credenciales,
 * autenticación y seguridad.
 *
 * RESPONSABILIDADES PRINCIPALES:
 * 1. Crear y gestionar credenciales de acceso
 * 2. Implementar funciones de hashing de contraseñas
 * 3. Verificar contraseñas durante el login
 * 4. Gestionar intentos fallidos de login
 * 5. Implementar políticas de seguridad (cambio de contraseña, etc.)
 *
 * SEGURIDAD DE CONTRASEÑAS:
 *
 * NUNCA almacenamos contraseñas en texto plano. En su lugar, usamos
 * una función de hashing unidireccional. El proceso es:
 *
 * 1. Usuario crea cuenta con contraseña "miPassword123"
 * 2. Generamos un salt aleatorio: "a1b2c3d4"
 * 3. Combinamos salt + contraseña: "a1b2c3d4miPassword123"
 * 4. Aplicamos SHA-256: "5f4dcc3b5aa765d61d8327deb882cf99..."
 * 5. Guardamos en BD: hash + salt (NO la contraseña original)
 *
 * Cuando el usuario hace login:
 * 1. Ingresa su contraseña: "miPassword123"
 * 2. Recuperamos el salt de la BD: "a1b2c3d4"
 * 3. Combinamos salt + contraseña ingresada: "a1b2c3d4miPassword123"
 * 4. Aplicamos SHA-256: "5f4dcc3b5aa765d61d8327deb882cf99..."
 * 5. Comparamos con el hash guardado
 * 6. Si coinciden → contraseña correcta, si no → incorrecta
 *
 * ¿Por qué usar un salt?
 * Sin salt, dos usuarios con la misma contraseña tendrían el mismo hash.
 * Un atacante podría:
 * - Precalcular hashes de contraseñas comunes (rainbow table)
 * - Ver qué usuarios tienen hashes iguales
 * Con salt, cada hash es único incluso con la misma contraseña.
 *
 * NOTA SOBRE PRODUCCIÓN:
 * En un sistema de producción real, usarías BCrypt o Argon2 en lugar
 * de SHA-256. Estos algoritmos están diseñados específicamente para
 * contraseñas y son más seguros. Para este TFI educativo, usamos SHA-256
 * para que entiendas los conceptos fundamentales.
 */
public class CredencialAccesoService {

    // ========================================================================
    // Constantes de configuración
    // ========================================================================

    /**
     * Número máximo de intentos de login fallidos antes de bloquear la cuenta.
     */
    private static final int MAX_INTENTOS_LOGIN = 5;

    /**
     * Algoritmo de hashing a usar para las contraseñas.
     * SHA-256 produce un hash de 256 bits (32 bytes).
     */
    private static final String ALGORITMO_HASH = "SHA-256";

    // ========================================================================
    // Atributos
    // ========================================================================

    /**
     * DAO para acceder a la capa de persistencia.
     */
    private final CredencialAccesoDAO credencialDAO;

    /**
     * Generador de números aleatorios criptográficamente seguros.
     * Se usa para generar salts.
     */
    private final SecureRandom secureRandom;

    // ========================================================================
    // Constructor
    // ========================================================================

    /**
     * Constructor que inicializa el service.
     */
    public CredencialAccesoService() {
        this.credencialDAO = new CredencialAccesoDAO();
        this.secureRandom = new SecureRandom();
    }

    // ========================================================================
    // Operaciones de negocio - CREATE
    // ========================================================================

    /**
     * Crea una nueva credencial de acceso para un usuario.
     *
     * Este método:
     * 1. Valida que la contraseña cumpla con los requisitos de seguridad
     * 2. Genera un salt aleatorio
     * 3. Calcula el hash de la contraseña con el salt
     * 4. Crea la credencial en la base de datos
     *
     * @param usuarioId El ID del usuario para quien crear la credencial
     * @param passwordPlainText La contraseña en texto plano (se hasheará antes de guardar)
     * @param requiereReset Si el usuario debe cambiar la contraseña en el próximo login
     * @return La credencial creada
     * @throws IllegalArgumentException Si la contraseña no cumple los requisitos
     * @throws SQLException Si hay un error en la base de datos
     */
    public CredencialAcceso crearCredencial(Long usuarioId, String passwordPlainText,
                                            boolean requiereReset) throws SQLException {
        // Validación 1: Verificar que el usuario ID no sea nulo
        if (usuarioId == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }

        // Validación 2: Verificar que la contraseña no esté vacía
        if (passwordPlainText == null || passwordPlainText.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }

        // Validación 3: Verificar que la contraseña cumpla requisitos mínimos de seguridad
        validarFortalezaPassword(passwordPlainText);

        // Validación 4: Verificar que el usuario no tenga ya una credencial
        CredencialAcceso credencialExistente = credencialDAO.buscarPorUsuarioId(usuarioId);
        if (credencialExistente != null) {
            throw new IllegalArgumentException("El usuario ya tiene una credencial asociada");
        }

        // Paso 1: Generar un salt aleatorio
        String salt = generarSalt();

        // Paso 2: Calcular el hash de la contraseña
        String hashPassword = calcularHash(passwordPlainText, salt);

        // Paso 3: Crear el objeto CredencialAcceso
        CredencialAcceso credencial = new CredencialAcceso();
        credencial.setHashPassword(hashPassword);
        credencial.setSalt(salt);
        credencial.setRequiereReset(requiereReset);
        credencial.setIntentosFallidos(0);
        credencial.setUltimoCambio(LocalDateTime.now());
        credencial.setEliminado(false);

        // Paso 4: Guardar en la base de datos
        return credencialDAO.crear(credencial, usuarioId);
    }

    // ========================================================================
    // Operaciones de negocio - READ
    // ========================================================================

    /**
     * Busca una credencial por su ID.
     *
     * @param id El ID de la credencial
     * @return La credencial encontrada, o null si no existe
     * @throws SQLException Si hay un error en la base de datos
     */
    public CredencialAcceso buscarCredencialPorId(Long id) throws SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo");
        }
        return credencialDAO.buscarPorId(id);
    }

    /**
     * Busca la credencial de un usuario específico.
     *
     * @param usuarioId El ID del usuario
     * @return La credencial del usuario, o null si no tiene
     * @throws SQLException Si hay un error en la base de datos
     */
    public CredencialAcceso buscarCredencialPorUsuarioId(Long usuarioId) throws SQLException {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("El ID de usuario debe ser un número positivo");
        }
        return credencialDAO.buscarPorUsuarioId(usuarioId);
    }

    /**
     * Lista todas las credenciales activas.
     *
     * @return Lista de credenciales
     * @throws SQLException Si hay un error en la base de datos
     */
    public List<CredencialAcceso> listarTodasLasCredenciales() throws SQLException {
        return credencialDAO.buscarTodas();
    }

    /**
     * Lista todas las credenciales que requieren reset de contraseña.
     *
     * @return Lista de credenciales que requieren reset
     * @throws SQLException Si hay un error en la base de datos
     */
    public List<CredencialAcceso> listarCredencialesQueRequierenReset() throws SQLException {
        return credencialDAO.buscarQueRequierenReset();
    }

    // ========================================================================
    // Operaciones de negocio - UPDATE
    // ========================================================================

    /**
     * Cambia la contraseña de una credencial.
     *
     * Este método:
     * 1. Valida que la nueva contraseña sea fuerte
     * 2. Genera un nuevo salt
     * 3. Calcula el nuevo hash
     * 4. Actualiza la credencial en la base de datos
     * 5. Resetea los intentos fallidos
     * 6. Quita el flag de requiere reset
     *
     * @param credencialId El ID de la credencial a actualizar
     * @param nuevaPasswordPlainText La nueva contraseña en texto plano
     * @return La credencial actualizada
     * @throws IllegalArgumentException Si la contraseña no cumple los requisitos
     * @throws SQLException Si hay un error en la base de datos
     */
    public CredencialAcceso cambiarPassword(Long credencialId, String nuevaPasswordPlainText)
            throws SQLException {
        // Validación 1: Verificar que la credencial existe
        CredencialAcceso credencial = credencialDAO.buscarPorId(credencialId);
        if (credencial == null) {
            throw new IllegalArgumentException("No existe una credencial con ID " + credencialId);
        }

        // Validación 2: Verificar que la nueva contraseña no esté vacía
        if (nuevaPasswordPlainText == null || nuevaPasswordPlainText.isEmpty()) {
            throw new IllegalArgumentException("La nueva contraseña no puede estar vacía");
        }

        // Validación 3: Verificar que la nueva contraseña sea fuerte
        validarFortalezaPassword(nuevaPasswordPlainText);

        // Generar nuevo salt y calcular nuevo hash
        String nuevoSalt = generarSalt();
        String nuevoHash = calcularHash(nuevaPasswordPlainText, nuevoSalt);

        // Actualizar la credencial usando el método de la entidad
        credencial.actualizarPassword(nuevoHash, nuevoSalt);

        // Resetear intentos fallidos (por si estaba cerca del bloqueo)
        credencial.setIntentosFallidos(0);

        // Guardar cambios en la base de datos
        boolean actualizado = credencialDAO.actualizar(credencial);
        if (!actualizado) {
            throw new SQLException("No se pudo actualizar la credencial");
        }

        return credencialDAO.buscarPorId(credencialId);
    }

    // ========================================================================
    // Operaciones de negocio - DELETE
    // ========================================================================

    /**
     * Elimina lógicamente una credencial.
     *
     * @param id El ID de la credencial a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Si hay un error en la base de datos
     */
    public boolean eliminarCredencial(Long id) throws SQLException {
        CredencialAcceso credencial = credencialDAO.buscarPorId(id);
        if (credencial == null) {
            throw new IllegalArgumentException("No existe una credencial con ID " + id);
        }

        if (credencial.isEliminado()) {
            throw new IllegalArgumentException("La credencial ya está eliminada");
        }

        return credencialDAO.eliminarLogico(id);
    }

    // ========================================================================
    // Operaciones de autenticación
    // ========================================================================

    /**
     * Verifica si una contraseña es correcta para una credencial dada.
     *
     * Este método implementa la verificación de login:
     * 1. Toma la contraseña ingresada
     * 2. Obtiene el salt de la credencial
     * 3. Calcula el hash de la contraseña ingresada con ese salt
     * 4. Compara con el hash guardado
     *
     * IMPORTANTE: Este método NO actualiza los intentos fallidos ni el último login.
     * Esas operaciones deben hacerse explícitamente con otros métodos.
     *
     * @param credencial La credencial contra la que verificar
     * @param passwordPlainText La contraseña a verificar
     * @return true si la contraseña es correcta, false en caso contrario
     */
    public boolean verificarPassword(CredencialAcceso credencial, String passwordPlainText) {
        if (credencial == null || passwordPlainText == null) {
            return false;
        }

        // Calcular el hash de la contraseña ingresada usando el salt guardado
        String hashCalculado = calcularHash(passwordPlainText, credencial.getSalt());

        // Comparar con el hash guardado
        return hashCalculado.equals(credencial.getHashPassword());
    }

    /**
     * Registra un intento de login exitoso.
     *
     * Esto:
     * - Resetea el contador de intentos fallidos a 0
     * - Actualiza la fecha de último login
     *
     * @param credencialId El ID de la credencial
     * @return La credencial actualizada
     * @throws SQLException Si hay un error en la base de datos
     */
    public CredencialAcceso registrarLoginExitoso(Long credencialId) throws SQLException {
        CredencialAcceso credencial = credencialDAO.buscarPorId(credencialId);
        if (credencial == null) {
            throw new IllegalArgumentException("No existe una credencial con ID " + credencialId);
        }

        // Usar el método de la entidad para registrar el login exitoso
        credencial.registrarLoginExitoso();

        // Guardar cambios
        credencialDAO.actualizar(credencial);

        return credencialDAO.buscarPorId(credencialId);
    }

    /**
     * Registra un intento de login fallido.
     *
     * Esto incrementa el contador de intentos fallidos.
     * Si se alcanza el máximo, la cuenta debería bloquearse (lo maneja el Service de Usuario).
     *
     * @param credencialId El ID de la credencial
     * @return La credencial actualizada
     * @throws SQLException Si hay un error en la base de datos
     */
    public CredencialAcceso registrarLoginFallido(Long credencialId) throws SQLException {
        CredencialAcceso credencial = credencialDAO.buscarPorId(credencialId);
        if (credencial == null) {
            throw new IllegalArgumentException("No existe una credencial con ID " + credencialId);
        }

        // Usar el método de la entidad para registrar el intento fallido
        credencial.registrarIntentoFallido();

        // Guardar cambios
        credencialDAO.actualizar(credencial);

        return credencialDAO.buscarPorId(credencialId);
    }

    /**
     * Verifica si una credencial debe ser bloqueada por demasiados intentos fallidos.
     *
     * @param credencial La credencial a verificar
     * @return true si debe bloquearse, false en caso contrario
     */
    public boolean debeBloquearPorIntentos(CredencialAcceso credencial) {
        return credencial != null && credencial.debeBloquearPorIntentos(MAX_INTENTOS_LOGIN);
    }

    // ========================================================================
    // Métodos auxiliares - Criptografía
    // ========================================================================

    /**
     * Genera un salt aleatorio criptográficamente seguro.
     *
     * El salt es un valor aleatorio que se combina con la contraseña antes
     * de aplicar el hash. Esto garantiza que dos usuarios con la misma
     * contraseña tengan hashes diferentes.
     *
     * @return Un string en Base64 representando 32 bytes aleatorios
     */
    private String generarSalt() {
        byte[] salt = new byte[32];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Calcula el hash SHA-256 de una contraseña con su salt.
     *
     * El proceso es:
     * 1. Combinar salt + contraseña
     * 2. Aplicar SHA-256
     * 3. Convertir el resultado a Base64 para almacenamiento
     *
     * @param passwordPlainText La contraseña en texto plano
     * @param salt El salt a usar
     * @return El hash en Base64
     * @throws RuntimeException Si el algoritmo SHA-256 no está disponible
     */
    private String calcularHash(String passwordPlainText, String salt) {
        try {
            // Obtener una instancia del algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance(ALGORITMO_HASH);

            // Combinar salt y contraseña
            String combinado = salt + passwordPlainText;

            // Calcular el hash
            byte[] hashBytes = digest.digest(combinado.getBytes());

            // Convertir a Base64 para almacenamiento
            return Base64.getEncoder().encodeToString(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            // Esto no debería pasar nunca en una JVM moderna
            throw new RuntimeException("Algoritmo de hash no disponible: " + ALGORITMO_HASH, e);
        }
    }

    // ========================================================================
    // Métodos auxiliares - Validación
    // ========================================================================

    /**
     * Valida que una contraseña cumpla con los requisitos mínimos de seguridad.
     *
     * Requisitos:
     * - Mínimo 8 caracteres
     * - Al menos una letra mayúscula
     * - Al menos una letra minúscula
     * - Al menos un número
     *
     * En un sistema real, estos requisitos vendrían de una configuración
     * y podrían ser más estrictos (requerir caracteres especiales, etc.).
     *
     * @param password La contraseña a validar
     * @throws IllegalArgumentException Si la contraseña no cumple los requisitos
     */
    private void validarFortalezaPassword(String password) {
        // Requisito 1: Longitud mínima
        if (password.length() < 8) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos 8 caracteres");
        }

        // Requisito 2: Al menos una letra mayúscula
        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            }
            if (Character.isLowerCase(c)) {
                tieneMinuscula = true;
            }
            if (Character.isDigit(c)) {
                tieneNumero = true;
            }
        }

        if (!tieneMayuscula) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos una letra mayúscula");
        }

        if (!tieneMinuscula) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos una letra minúscula");
        }

        if (!tieneNumero) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos un número");
        }
    }
}