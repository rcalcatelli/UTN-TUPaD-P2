package ar.edu.utn.tup.service;

import ar.edu.utn.tup.config.DatabaseConfig;
import ar.edu.utn.tup.dao.CredencialAccesoDAO;
import ar.edu.utn.tup.dao.UsuarioDAO;
import ar.edu.utn.tup.entities.CredencialAcceso;
import ar.edu.utn.tup.entities.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final CredencialAccesoDAO credencialDAO;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
        this.credencialDAO = new CredencialAccesoDAO();
    }

    // ========================================================================
    // MÉTODO TRANSACCIONAL PRINCIPAL
    // ========================================================================

    /**
     * Crea usuario + credencial en UNA transacción atómica.
     * Este es el método principal que demuestra el manejo de transacciones.
     */
    public Usuario crearUsuarioConCredencial(Usuario usuario, String passwordPlano)
            throws SQLException, IllegalArgumentException {

        validarDatosUsuario(usuario);
        validarPassword(passwordPlano);

        Connection conn = null;

        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);  // Iniciar transacción

            // Verificar duplicados
            if (usuarioDAO.buscarPorUsername(usuario.getUsername()) != null) {
                throw new IllegalArgumentException(
                        "El username '" + usuario.getUsername() + "' ya está en uso"
                );
            }

            if (usuarioDAO.buscarPorEmail(usuario.getEmail()) != null) {
                throw new IllegalArgumentException(
                        "El email '" + usuario.getEmail() + "' ya está registrado"
                );
            }

            // Preparar usuario
            usuario.setEliminado(false);
            usuario.setActivo(true);
            usuario.setFechaRegistro(LocalDateTime.now());

            // Insertar usuario
            Usuario usuarioCreado = usuarioDAO.crear(usuario, conn);

            // Crear credencial asociada
            CredencialAcceso credencial = new CredencialAcceso();
            credencial.setUsuarioId(usuarioCreado.getId());

            String[] hashYSalt = hashearPassword(passwordPlano);
            credencial.setHashPassword(hashYSalt[0]);
            credencial.setSalt(hashYSalt[1]);
            credencial.setUltimoCambio(LocalDateTime.now());
            credencial.setRequiereReset(false);
            credencial.setIntentosFallidos(0);
            credencial.setEliminado(false);

            // Insertar credencial
            CredencialAcceso credencialCreada = credencialDAO.crear(credencial, conn);

            // COMMIT - todo salió bien
            conn.commit();

            usuarioCreado.setCredencialAcceso(credencialCreada);
            System.out.println("✓ Usuario y credencial creados exitosamente en transacción");
            return usuarioCreado;

        } catch (SQLException | IllegalArgumentException e) {
            // ROLLBACK - algo falló
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("✗ Transacción revertida (ROLLBACK)");
                } catch (SQLException rollbackEx) {
                    System.err.println("✗ Error al hacer rollback: " + rollbackEx.getMessage());
                }
            }
            throw e;

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("✗ Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
    }

    // ========================================================================
    // MÉTODOS PARA COMPATIBILIDAD CON MAIN.JAVA
    // ========================================================================

    /**
     * Registra un usuario (alias para crearUsuarioConCredencial)
     */
    public Usuario registrarUsuario(Usuario usuario) throws SQLException {
        // Para mantener compatibilidad, creamos con password temporal
        return crearUsuarioConCredencial(usuario, "temporal123");
    }

    /**
     * Lista todos los usuarios
     */
    public List<Usuario> listarTodosLosUsuarios() throws SQLException {
        return usuarioDAO.obtenerTodos();
    }

    /**
     * Busca usuario por ID
     */
    public Usuario buscarUsuarioPorId(Long id) throws SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return usuarioDAO.buscarPorId(id);
    }

    /**
     * Busca usuario por username
     */
    public Usuario buscarUsuarioPorUsername(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username vacío");
        }
        return usuarioDAO.buscarPorUsername(username);
    }

    /**
     * Actualiza un usuario
     */
    public Usuario actualizarUsuario(Usuario usuario) throws SQLException {
        validarDatosUsuario(usuario);
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("El usuario debe tener ID");
        }
        usuarioDAO.actualizar(usuario);
        return usuario;
    }

    /**
     * Desactiva un usuario
     */
    public Usuario desactivarUsuario(Long id) throws SQLException {
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        usuario.setActivo(false);
        usuarioDAO.actualizar(usuario);
        return usuario;
    }

    /**
     * Activa un usuario
     */
    public Usuario activarUsuario(Long id) throws SQLException {
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        usuario.setActivo(true);
        usuarioDAO.actualizar(usuario);
        return usuario;
    }

    /**
     * Elimina lógicamente un usuario
     */
    public boolean eliminarUsuario(Long id) throws SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return usuarioDAO.eliminarLogicamente(id);
    }

    // ========================================================================
    // MÉTODOS ADICIONALES ÚTILES
    // ========================================================================

    public Usuario buscarPorEmail(String email) throws SQLException {
        if (!esEmailValido(email)) {
            throw new IllegalArgumentException("Email inválido");
        }
        return usuarioDAO.buscarPorEmail(email);
    }

    public List<Usuario> obtenerActivos() throws SQLException {
        return usuarioDAO.obtenerActivos();
    }

    public Usuario buscarConCredencial(Long id) throws SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return usuarioDAO.buscarConCredencial(id);
    }

    // ========================================================================
    // VALIDACIONES
    // ========================================================================

    private void validarDatosUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario null");
        }
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username obligatorio");
        }
        if (usuario.getUsername().length() < 3 || usuario.getUsername().length() > 50) {
            throw new IllegalArgumentException("Username debe tener 3-50 caracteres");
        }
        if (!esEmailValido(usuario.getEmail())) {
            throw new IllegalArgumentException("Email inválido");
        }
        if (usuario.getNombreCompleto() == null || usuario.getNombreCompleto().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre completo obligatorio");
        }
    }

    private void validarPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Contraseña obligatoria");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Contraseña debe tener mínimo 8 caracteres");
        }
    }

    private boolean esEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    // ========================================================================
    // UTILIDAD PARA HASHEAR CONTRASEÑAS
    // ========================================================================

    private String[] hashearPassword(String passwordPlano) {
        try {
            java.security.SecureRandom random = new java.security.SecureRandom();
            byte[] saltBytes = new byte[16];
            random.nextBytes(saltBytes);
            String salt = bytesToHex(saltBytes);

            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            String passwordConSalt = passwordPlano + salt;
            byte[] hashBytes = digest.digest(passwordConSalt.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            String hash = bytesToHex(hashBytes);

            return new String[]{hash, salt};

        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear contraseña", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}