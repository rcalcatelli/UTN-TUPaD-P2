package ar.edu.utn.frbb.tup.dao;

import ar.edu.utn.frbb.tup.config.DatabaseConfig;
import ar.edu.utn.frbb.tup.entities.CredencialAcceso;
import ar.edu.utn.frbb.tup.entities.Usuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la entidad Usuario.
 *
 * Esta clase maneja toda la persistencia de usuarios en la base de datos
 * e implementa la relación 1→1 con CredencialAcceso.
 *
 * IMPLEMENTACIÓN DE LA RELACIÓN 1→1:
 *
 * Esta es la parte más interesante del diseño. En nuestra base de datos,
 * la tabla credencial_acceso tiene un usuario_id que apunta a usuario.
 * Sin embargo, en nuestro código Java, Usuario tiene una referencia a
 * CredencialAcceso (la navegación es en la otra dirección).
 *
 * ¿Cómo lo resolvemos?
 *
 * Cuando CREAMOS un usuario:
 * 1. Insertamos el usuario en la tabla usuario (sin credencial todavía)
 * 2. Si recibimos una credencial, la insertamos en credencial_acceso
 *    con el usuario_id apuntando al usuario recién creado
 *
 * Cuando LEEMOS un usuario:
 * 1. Consultamos la tabla usuario para obtener los datos del usuario
 * 2. Usamos el ID del usuario para buscar su credencial en credencial_acceso
 * 3. Asignamos la credencial al objeto Usuario
 *
 * De esta forma, desde el punto de vista del código Java, navegamos
 * de Usuario → CredencialAcceso, aunque en la BD la FK esté al revés.
 *
 * COLABORACIÓN ENTRE DAOs:
 * Este DAO tiene una instancia de CredencialAccesoDAO para poder cargar
 * las credenciales cuando recupera usuarios. Esta colaboración es normal
 * y esperada cuando hay relaciones entre entidades.
 */
public class UsuarioDAO {

    // ========================================================================
    // Atributos
    // ========================================================================

    /**
     * Configuración de base de datos para obtener conexiones.
     */
    private final DatabaseConfig dbConfig;

    /**
     * DAO de credenciales para gestionar la relación 1→1.
     *
     * Necesitamos esta instancia porque cuando cargamos un usuario,
     * también necesitamos cargar su credencial. En lugar de duplicar
     * la lógica de lectura de credenciales acá, reutilizamos el
     * CredencialAccesoDAO que ya sabe cómo hacerlo correctamente.
     */
    private final CredencialAccesoDAO credencialDAO;

    // ========================================================================
    // Constructor
    // ========================================================================

    /**
     * Constructor que inicializa el DAO.
     * Crea instancias de DatabaseConfig y CredencialAccesoDAO.
     */
    public UsuarioDAO() {
        this.dbConfig = DatabaseConfig.getInstance();
        this.credencialDAO = new CredencialAccesoDAO();
    }

    // ========================================================================
    // Operación CREATE - Insertar nuevo usuario
    // ========================================================================

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * Si el usuario tiene una credencial asociada, también la inserta.
     *
     * IMPORTANTE SOBRE EL ORDEN DE INSERCIÓN:
     * 1. Primero insertamos el usuario
     * 2. Obtenemos el ID autogenerado del usuario
     * 3. Después insertamos la credencial con ese usuario_id
     *
     * Este orden es necesario porque credencial_acceso tiene una FK
     * hacia usuario. No podemos insertar una credencial que apunte
     * a un usuario que todavía no existe.
     *
     * @param usuario El usuario a insertar (puede incluir credencial)
     * @return El usuario con el ID asignado
     * @throws SQLException Si hay un error en la base de datos
     */
    public Usuario crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario " +
                "(eliminado, username, email, nombre_completo, activo, fecha_registro) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setBoolean(1, usuario.isEliminado());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getNombreCompleto());
            ps.setBoolean(5, usuario.isActivo());
            ps.setTimestamp(6, usuario.getFechaRegistro() != null
                    ? Timestamp.valueOf(usuario.getFechaRegistro())
                    : Timestamp.valueOf(LocalDateTime.now()));

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("Error al crear usuario, ninguna fila fue insertada.");
            }

            // Recuperar el ID autogenerado
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Error al crear usuario, no se obtuvo el ID generado.");
                }
            }

            // Si el usuario tiene una credencial, insertarla también
            if (usuario.getCredencialAcceso() != null) {
                // Usamos el CredencialAccesoDAO para insertar la credencial
                // Pasamos el ID del usuario como parámetro
                CredencialAcceso credencialCreada = credencialDAO.crear(
                        usuario.getCredencialAcceso(),
                        usuario.getId()
                );
                // Actualizamos la credencial en el usuario con el ID generado
                usuario.setCredencialAcceso(credencialCreada);
            }

            return usuario;
        }
    }

    // ========================================================================
    // Operación READ - Buscar usuarios
    // ========================================================================

    /**
     * Busca un usuario por su ID y carga su credencial asociada.
     *
     * @param id El ID del usuario a buscar
     * @return El usuario encontrado con su credencial, o null si no existe
     * @throws SQLException Si hay un error en la base de datos
     */
    public Usuario buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = mapearResultSetAUsuario(rs);
                    // Cargar la credencial del usuario
                    cargarCredencial(usuario);
                    return usuario;
                }
                return null;
            }
        }
    }

    /**
     * Busca un usuario por su username y carga su credencial.
     *
     * Este método es fundamental para el login, donde el usuario
     * ingresa su username y contraseña.
     *
     * @param username El username a buscar
     * @return El usuario encontrado con su credencial, o null si no existe
     * @throws SQLException Si hay un error en la base de datos
     */
    public Usuario buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE username = ? AND eliminado = false";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = mapearResultSetAUsuario(rs);
                    cargarCredencial(usuario);
                    return usuario;
                }
                return null;
            }
        }
    }

    /**
     * Busca un usuario por su email y carga su credencial.
     *
     * Útil para funcionalidades como "recuperar contraseña" donde
     * el usuario proporciona su email.
     *
     * @param email El email a buscar
     * @return El usuario encontrado con su credencial, o null si no existe
     * @throws SQLException Si hay un error en la base de datos
     */
    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ? AND eliminado = false";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = mapearResultSetAUsuario(rs);
                    cargarCredencial(usuario);
                    return usuario;
                }
                return null;
            }
        }
    }

    /**
     * Obtiene todos los usuarios que no están eliminados lógicamente.
     * También carga las credenciales de cada usuario.
     *
     * @return Lista con todos los usuarios activos
     * @throws SQLException Si hay un error en la base de datos
     */
    public List<Usuario> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM usuario WHERE eliminado = false ORDER BY id";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = mapearResultSetAUsuario(rs);
                cargarCredencial(usuario);
                usuarios.add(usuario);
            }
        }

        return usuarios;
    }

    /**
     * Obtiene todos los usuarios activos (no eliminados y habilitados).
     * También carga las credenciales de cada usuario.
     *
     * @return Lista con todos los usuarios activos
     * @throws SQLException Si hay un error en la base de datos
     */
    public List<Usuario> buscarActivos() throws SQLException {
        String sql = "SELECT * FROM usuario " +
                "WHERE eliminado = false AND activo = true " +
                "ORDER BY id";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = mapearResultSetAUsuario(rs);
                cargarCredencial(usuario);
                usuarios.add(usuario);
            }
        }

        return usuarios;
    }

    // ========================================================================
    // Operación UPDATE - Actualizar usuario existente
    // ========================================================================

    /**
     * Actualiza un usuario existente en la base de datos.
     *
     * NOTA: Este método NO actualiza la credencial del usuario.
     * Para actualizar la credencial, usá directamente el CredencialAccesoDAO.
     * Esto mantiene la separación de responsabilidades: este DAO maneja
     * usuarios, el CredencialAccesoDAO maneja credenciales.
     *
     * @param usuario El usuario con los datos actualizados (debe tener ID)
     * @return true si se actualizó, false si no se encontró el usuario
     * @throws SQLException Si hay un error en la base de datos
     * @throws IllegalArgumentException Si el usuario no tiene ID
     */
    public boolean actualizar(Usuario usuario) throws SQLException {
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("No se puede actualizar un usuario sin ID");
        }

        String sql = "UPDATE usuario SET " +
                "eliminado = ?, " +
                "username = ?, " +
                "email = ?, " +
                "nombre_completo = ?, " +
                "activo = ? " +
                "WHERE id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, usuario.isEliminado());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getNombreCompleto());
            ps.setBoolean(5, usuario.isActivo());
            ps.setLong(6, usuario.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    // ========================================================================
    // Operación DELETE - Eliminar usuario
    // ========================================================================

    /**
     * Realiza la baja lógica de un usuario (no lo borra físicamente).
     *
     * IMPORTANTE: Cuando eliminamos lógicamente un usuario, su credencial
     * también debería eliminarse lógicamente. Sin embargo, gracias a que
     * en la base de datos configuramos ON DELETE CASCADE en la FK de
     * credencial_acceso hacia usuario, si alguna vez hacemos eliminación
     * física, la credencial se eliminará automáticamente.
     *
     * Para la baja lógica, necesitamos hacerlo manualmente en ambas tablas.
     *
     * @param id El ID del usuario a eliminar lógicamente
     * @return true si se eliminó, false si no se encontró
     * @throws SQLException Si hay un error en la base de datos
     */
    public boolean eliminarLogico(Long id) throws SQLException {
        String sql = "UPDATE usuario SET eliminado = true, activo = false WHERE id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            int filasAfectadas = ps.executeUpdate();

            // Si se eliminó el usuario, también eliminar lógicamente su credencial
            if (filasAfectadas > 0) {
                CredencialAcceso credencial = credencialDAO.buscarPorUsuarioId(id);
                if (credencial != null) {
                    credencialDAO.eliminarLogico(credencial.getId());
                }
            }

            return filasAfectadas > 0;
        }
    }

    /**
     * Elimina físicamente un usuario de la base de datos.
     *
     * ADVERTENCIA: Esta operación es IRREVERSIBLE.
     *
     * Gracias al ON DELETE CASCADE que configuramos en la base de datos,
     * cuando eliminemos el usuario, su credencial se eliminará automáticamente.
     *
     * @param id El ID del usuario a eliminar físicamente
     * @return true si se eliminó, false si no se encontró
     * @throws SQLException Si hay un error en la base de datos
     */
    public boolean eliminarFisico(Long id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";

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
     * Convierte un ResultSet en un objeto Usuario (sin la credencial).
     *
     * @param rs El ResultSet posicionado en la fila a mapear
     * @return Un objeto Usuario con los datos de la fila
     * @throws SQLException Si hay un error al leer el ResultSet
     */
    private Usuario mapearResultSetAUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();

        usuario.setId(rs.getLong("id"));
        usuario.setEliminado(rs.getBoolean("eliminado"));
        usuario.setUsername(rs.getString("username"));
        usuario.setEmail(rs.getString("email"));
        usuario.setNombreCompleto(rs.getString("nombre_completo"));
        usuario.setActivo(rs.getBoolean("activo"));

        Timestamp fechaRegistro = rs.getTimestamp("fecha_registro");
        usuario.setFechaRegistro(fechaRegistro != null ? fechaRegistro.toLocalDateTime() : null);

        return usuario;
    }

    /**
     * Carga la credencial de un usuario usando el CredencialAccesoDAO.
     *
     * Este método implementa la parte "lectora" de la relación 1→1.
     * Usa el ID del usuario para buscar su credencial en la base de datos
     * y asignarla al objeto Usuario.
     *
     * Si el usuario no tiene credencial, el método termina sin hacer nada
     * (el usuario quedará con credencialAcceso = null).
     *
     * @param usuario El usuario al que cargarle la credencial
     * @throws SQLException Si hay un error en la base de datos
     */
    private void cargarCredencial(Usuario usuario) throws SQLException {
        if (usuario.getId() != null) {
            // Buscar la credencial usando el CredencialAccesoDAO
            // El método buscarPorUsuarioId usa el usuario_id de la tabla
            CredencialAcceso credencial = credencialDAO.buscarPorUsuarioId(usuario.getId());

            // Asignar la credencial al usuario (puede ser null si no tiene)
            usuario.setCredencialAcceso(credencial);
        }
    }
}