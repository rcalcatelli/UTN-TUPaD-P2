package ar.edu.utn.tup.dao;

import ar.edu.utn.tup.config.DatabaseConfig;
import ar.edu.utn.tup.entities.CredencialAcceso;
import ar.edu.utn.tup.entities.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Métodos simples (crean su propia conexión)
    public Usuario crear(Usuario usuario) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection()) {
            return crear(usuario, conn);
        }
    }

    public boolean actualizar(Usuario usuario) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection()) {
            return actualizar(usuario, conn);
        }
    }

    // Métodos transaccionales (reciben Connection externa)
    public Usuario crear(Usuario usuario, Connection conn) throws SQLException {
        String sql = "INSERT INTO usuario (username, email, nombre_completo, activo, " +
                "eliminado, fecha_registro) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getNombreCompleto());
            ps.setBoolean(4, usuario.isActivo());
            ps.setBoolean(5, usuario.isEliminado());
            ps.setTimestamp(6, Timestamp.valueOf(usuario.getFechaRegistro()));

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo crear el usuario");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado");
                }
            }

            return usuario;
        }
    }

    public boolean actualizar(Usuario usuario, Connection conn) throws SQLException {
        String sql = "UPDATE usuario SET username = ?, email = ?, nombre_completo = ?, " +
                "activo = ?, eliminado = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getNombreCompleto());
            ps.setBoolean(4, usuario.isActivo());
            ps.setBoolean(5, usuario.isEliminado());
            ps.setLong(6, usuario.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    // Métodos de lectura
    public Usuario buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    public Usuario buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    public List<Usuario> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM usuario WHERE eliminado = FALSE ORDER BY id";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        }
        return usuarios;
    }

    public List<Usuario> obtenerActivos() throws SQLException {
        String sql = "SELECT * FROM usuario WHERE activo = TRUE AND eliminado = FALSE ORDER BY id";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        }
        return usuarios;
    }

    public boolean eliminarLogicamente(Long id) throws SQLException {
        String sql = "UPDATE usuario SET eliminado = TRUE, activo = FALSE WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    // Método auxiliar para mapear ResultSet
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setEliminado(rs.getBoolean("eliminado"));
        usuario.setUsername(rs.getString("username"));
        usuario.setEmail(rs.getString("email"));
        usuario.setNombreCompleto(rs.getString("nombre_completo"));
        usuario.setActivo(rs.getBoolean("activo"));

        Timestamp timestamp = rs.getTimestamp("fecha_registro");
        if (timestamp != null) {
            usuario.setFechaRegistro(timestamp.toLocalDateTime());
        }

        return usuario;
    }

    // Método especial: busca usuario con su credencial (hace JOIN)
    public Usuario buscarConCredencial(Long id) throws SQLException {
        String sql = "SELECT u.*, c.id as cred_id, c.eliminado as cred_eliminado, " +
                "c.usuario_id, c.hash_password, c.salt, c.ultimo_cambio, c.requiere_reset, " +
                "c.intentos_fallidos, c.ultimo_login " +
                "FROM usuario u " +
                "LEFT JOIN credencial_acceso c ON c.usuario_id = u.id " +
                "WHERE u.id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = mapearUsuario(rs);

                    // Si tiene credencial, mapearla también
                    Long credId = rs.getLong("cred_id");
                    if (!rs.wasNull()) {
                        CredencialAcceso credencial = new CredencialAcceso();
                        credencial.setId(credId);
                        credencial.setUsuarioId(usuario.getId());
                        credencial.setEliminado(rs.getBoolean("cred_eliminado"));
                        credencial.setHashPassword(rs.getString("hash_password"));
                        credencial.setSalt(rs.getString("salt"));
                        credencial.setIntentosFallidos(rs.getInt("intentos_fallidos"));
                        credencial.setRequiereReset(rs.getBoolean("requiere_reset"));

                        Timestamp ultimoCambio = rs.getTimestamp("ultimo_cambio");
                        if (ultimoCambio != null) {
                            credencial.setUltimoCambio(ultimoCambio.toLocalDateTime());
                        }

                        Timestamp ultimoLogin = rs.getTimestamp("ultimo_login");
                        if (ultimoLogin != null) {
                            credencial.setUltimoLogin(ultimoLogin.toLocalDateTime());
                        }

                        usuario.setCredencialAcceso(credencial);
                    }

                    return usuario;
                }
            }
        }
        return null;
    }
}