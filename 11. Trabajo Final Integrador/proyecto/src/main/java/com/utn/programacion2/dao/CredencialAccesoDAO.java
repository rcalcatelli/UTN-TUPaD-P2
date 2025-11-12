package ar.edu.utn.frbb.tup.dao;

import ar.edu.utn.frbb.tup.config.DatabaseConfig;
import ar.edu.utn.frbb.tup.entities.CredencialAcceso;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la entidad CredencialAcceso.
 *
 * Esta clase es responsable de TODA la interacción con la tabla
 * credencial_acceso en la base de datos. Implementa las operaciones CRUD
 * (Create, Read, Update, Delete) y algunas consultas específicas.
 *
 * PRINCIPIO DE RESPONSABILIDAD ÚNICA:
 * Este DAO SOLO se encarga de persistencia de credenciales. No contiene
 * lógica de negocio - esa está en el Service. El DAO es "tonto" en el buen
 * sentido: solo sabe cómo guardar, recuperar, actualizar y eliminar datos.
 *
 * SEGURIDAD - USO DE PREPAREDSTATEMENT:
 * Todos los métodos usan PreparedStatement en lugar de Statement.
 * Esto previene ataques de SQL Injection, que son una de las
 * vulnerabilidades más peligrosas en aplicaciones web.
 *
 * ¿Qué es SQL Injection?
 * Imaginate que un usuario malicioso ingresa como username:
 *   admin' OR '1'='1
 *
 * Si usáramos concatenación de strings:
 *   String sql = "SELECT * FROM user WHERE username='" + username + "'";
 * Resultaría en:
 *   SELECT * FROM user WHERE username='admin' OR '1'='1'
 * Que devolvería TODOS los usuarios (porque '1'='1' es siempre verdadero).
 *
 * Con PreparedStatement:
 *   PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE username=?");
 *   ps.setString(1, username);
 * El valor se trata como DATOS, no como código SQL, previniendo el ataque.
 *
 * IMPORTANTE SOBRE LA RELACIÓN CON USUARIO:
 * En la base de datos, credencial_acceso tiene un usuario_id que es FK
 * hacia usuario. Pero en el código Java, CredencialAcceso NO tiene una
 * referencia a Usuario. Este DAO maneja el usuario_id directamente en las
 * operaciones SQL, haciendo la traducción entre el modelo relacional y
 * el modelo de objetos.
 */
public class CredencialAccesoDAO {

    // ========================================================================
    // Atributos
    // ========================================================================

    /**
     * Instancia de DatabaseConfig para obtener conexiones.
     * Usamos el Singleton para tener una configuración centralizada.
     */
    private final DatabaseConfig dbConfig;

    // ========================================================================
    // Constructor
    // ========================================================================

    /**
     * Constructor que inicializa el DAO con la configuración de BD.
     * Usa el patrón Singleton para obtener la configuración.
     */
    public CredencialAccesoDAO() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    // ========================================================================
    // Operación CREATE - Insertar nueva credencial
    // ========================================================================

    /**
     * Inserta una nueva credencial en la base de datos.
     *
     * IMPORTANTE: Este método necesita el usuario_id porque en la base de
     * datos credencial_acceso tiene una FK hacia usuario. Aunque en nuestro
     * modelo de objetos la relación es desde Usuario hacia CredencialAcceso,
     * en la base de datos es al revés.
     *
     * El método asigna el ID autogenerado a la credencial y lo retorna.
     *
     * @param credencial La credencial a insertar (sin ID, se autogenera)
     * @param usuarioId El ID del usuario al que pertenece esta credencial
     * @return La credencial con el ID asignado
     * @throws SQLException Si hay un error en la base de datos
     */
    public CredencialAcceso crear(CredencialAcceso credencial, Long usuarioId) throws SQLException {
        // SQL para insertar una nueva credencial
        // Los ? son marcadores de posición (placeholders) que reemplazaremos
        // con PreparedStatement, lo cual previene SQL Injection
        String sql = "INSERT INTO credencial_acceso " +
                "(eliminado, usuario_id, hash_password, salt, ultimo_cambio, " +
                "requiere_reset, intentos_fallidos, ultimo_login) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // try-with-resources: garantiza que la conexión se cierre automáticamente
        // incluso si hay una excepción
        try (Connection conn = dbConfig.getConnection();
             // Statement.RETURN_GENERATED_KEYS le dice a JDBC que queremos recuperar
             // el ID autogenerado por MySQL
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Asignamos valores a cada ? en el orden en que aparecen en el SQL
            // El índice empieza en 1, no en 0 (peculiaridad de JDBC)
            ps.setBoolean(1, credencial.isEliminado());
            ps.setLong(2, usuarioId);  // Este es el usuario_id (la FK)
            ps.setString(3, credencial.getHashPassword());
            ps.setString(4, credencial.getSalt());

            // Para los campos DATETIME de MySQL, convertimos LocalDateTime
            // Si el valor es null, insertamos null en la BD
            ps.setTimestamp(5, credencial.getUltimoCambio() != null
                    ? Timestamp.valueOf(credencial.getUltimoCambio())
                    : null);

            ps.setBoolean(6, credencial.isRequiereReset());
            ps.setInt(7, credencial.getIntentosFallidos());

            ps.setTimestamp(8, credencial.getUltimoLogin() != null
                    ? Timestamp.valueOf(credencial.getUltimoLogin())
                    : null);

            // Ejecutar el INSERT y obtener el número de filas afectadas
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("Error al crear credencial, ninguna fila fue insertada.");
            }

            // Recuperar el ID autogenerado por MySQL
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Asignamos el ID generado a nuestra credencial
                    credencial.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Error al crear credencial, no se obtuvo el ID generado.");
                }
            }

            return credencial;
        }
    }

    // ========================================================================
    // Operación READ - Buscar credenciales
    // ========================================================================

    /**
     * Busca una credencial por su ID.
     *
     * @param id El ID de la credencial a buscar
     * @return La credencial encontrada, o null si no existe
     * @throws SQLException Si hay un error en la base de datos
     */
    public CredencialAcceso buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM credencial_acceso WHERE id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetACredencial(rs);
                }
                return null;  // No se encontró ninguna credencial con ese ID
            }
        }
    }

    /**
     * Busca una credencial por el ID del usuario al que pertenece.
     *
     * Este método es fundamental para nuestra implementación de la relación 1→1.
     * Aunque en Java la navegación es Usuario → CredencialAcceso, cuando
     * cargamos un Usuario necesitamos hacer la consulta inversa en la BD
     * para encontrar su credencial.
     *
     * @param usuarioId El ID del usuario
     * @return La credencial del usuario, o null si no tiene credencial
     * @throws SQLException Si hay un error en la base de datos
     */
    public CredencialAcceso buscarPorUsuarioId(Long usuarioId) throws SQLException {
        String sql = "SELECT * FROM credencial_acceso WHERE usuario_id = ? AND eliminado = false";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetACredencial(rs);
                }
                return null;
            }
        }
    }

    /**
     * Obtiene todas las credenciales que no están eliminadas lógicamente.
     *
     * @return Lista con todas las credenciales activas
     * @throws SQLException Si hay un error en la base de datos
     */
    public List<CredencialAcceso> buscarTodas() throws SQLException {
        String sql = "SELECT * FROM credencial_acceso WHERE eliminado = false ORDER BY id";
        List<CredencialAcceso> credenciales = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                credenciales.add(mapearResultSetACredencial(rs));
            }
        }

        return credenciales;
    }

    /**
     * Busca credenciales que requieren reset de contraseña.
     * Útil para reportes o para enviar notificaciones.
     *
     * @return Lista de credenciales que requieren reset
     * @throws SQLException Si hay un error en la base de datos
     */
    public List<CredencialAcceso> buscarQueRequierenReset() throws SQLException {
        String sql = "SELECT * FROM credencial_acceso " +
                "WHERE requiere_reset = true AND eliminado = false " +
                "ORDER BY id";
        List<CredencialAcceso> credenciales = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                credenciales.add(mapearResultSetACredencial(rs));
            }
        }

        return credenciales;
    }

    // ========================================================================
    // Operación UPDATE - Actualizar credencial existente
    // ========================================================================

    /**
     * Actualiza una credencial existente en la base de datos.
     *
     * IMPORTANTE: Este método NO actualiza el usuario_id. Una vez que
     * una credencial está asociada a un usuario, no debería cambiar a otro
     * usuario. Si necesitás hacer eso, sería mejor eliminar la credencial
     * vieja y crear una nueva.
     *
     * @param credencial La credencial con los datos actualizados (debe tener ID)
     * @return true si se actualizó, false si no se encontró la credencial
     * @throws SQLException Si hay un error en la base de datos
     * @throws IllegalArgumentException Si la credencial no tiene ID
     */
    public boolean actualizar(CredencialAcceso credencial) throws SQLException {
        if (credencial.getId() == null) {
            throw new IllegalArgumentException("No se puede actualizar una credencial sin ID");
        }

        String sql = "UPDATE credencial_acceso SET " +
                "eliminado = ?, " +
                "hash_password = ?, " +
                "salt = ?, " +
                "ultimo_cambio = ?, " +
                "requiere_reset = ?, " +
                "intentos_fallidos = ?, " +
                "ultimo_login = ? " +
                "WHERE id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, credencial.isEliminado());
            ps.setString(2, credencial.getHashPassword());
            ps.setString(3, credencial.getSalt());
            ps.setTimestamp(4, credencial.getUltimoCambio() != null
                    ? Timestamp.valueOf(credencial.getUltimoCambio())
                    : null);
            ps.setBoolean(5, credencial.isRequiereReset());
            ps.setInt(6, credencial.getIntentosFallidos());
            ps.setTimestamp(7, credencial.getUltimoLogin() != null
                    ? Timestamp.valueOf(credencial.getUltimoLogin())
                    : null);
            ps.setLong(8, credencial.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    // ========================================================================
    // Operación DELETE - Eliminar credencial
    // ========================================================================

    /**
     * Realiza la baja lógica de una credencial (no la borra físicamente).
     *
     * La baja lógica es preferible a la eliminación física porque:
     * - Mantiene la integridad referencial
     * - Conserva el historial
     * - Permite recuperación si fue un error
     *
     * @param id El ID de la credencial a eliminar lógicamente
     * @return true si se eliminó, false si no se encontró
     * @throws SQLException Si hay un error en la base de datos
     */
    public boolean eliminarLogico(Long id) throws SQLException {
        String sql = "UPDATE credencial_acceso SET eliminado = true WHERE id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    /**
     * Elimina físicamente una credencial de la base de datos.
     *
     * ADVERTENCIA: Esta operación es IRREVERSIBLE. Solo deberías usarla
     * en casos muy específicos, como limpieza de datos de prueba.
     * En producción, preferí siempre la baja lógica.
     *
     * @param id El ID de la credencial a eliminar físicamente
     * @return true si se eliminó, false si no se encontró
     * @throws SQLException Si hay un error en la base de datos
     */
    public boolean eliminarFisico(Long id) throws SQLException {
        String sql = "DELETE FROM credencial_acceso WHERE id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    // ========================================================================
    // Métodos auxiliares
    // ========================================================================

    /**
     * Convierte un ResultSet en un objeto CredencialAcceso.
     *
     * Este método privado extrae los datos de una fila del ResultSet
     * y construye un objeto CredencialAcceso con esos datos.
     *
     * Lo hacemos en un método aparte porque se usa en múltiples lugares
     * (buscarPorId, buscarTodas, etc.) y queremos evitar duplicación de código.
     *
     * @param rs El ResultSet posicionado en la fila a mapear
     * @return Un objeto CredencialAcceso con los datos de la fila
     * @throws SQLException Si hay un error al leer el ResultSet
     */
    private CredencialAcceso mapearResultSetACredencial(ResultSet rs) throws SQLException {
        CredencialAcceso credencial = new CredencialAcceso();

        credencial.setId(rs.getLong("id"));
        credencial.setEliminado(rs.getBoolean("eliminado"));
        credencial.setHashPassword(rs.getString("hash_password"));
        credencial.setSalt(rs.getString("salt"));

        // Convertir Timestamp de SQL a LocalDateTime de Java
        // Manejamos el caso null explícitamente
        Timestamp ultimoCambio = rs.getTimestamp("ultimo_cambio");
        credencial.setUltimoCambio(ultimoCambio != null ? ultimoCambio.toLocalDateTime() : null);

        credencial.setRequiereReset(rs.getBoolean("requiere_reset"));
        credencial.setIntentosFallidos(rs.getInt("intentos_fallidos"));

        Timestamp ultimoLogin = rs.getTimestamp("ultimo_login");
        credencial.setUltimoLogin(ultimoLogin != null ? ultimoLogin.toLocalDateTime() : null);

        return credencial;
    }
}