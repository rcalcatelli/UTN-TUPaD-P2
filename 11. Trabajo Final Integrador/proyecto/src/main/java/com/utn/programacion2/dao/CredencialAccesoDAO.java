package ar.edu.utn.tup.dao;

import ar.edu.utn.tup.config.DatabaseConfig;
import ar.edu.utn.tup.entities.CredencialAcceso;

import java.sql.*;

public class CredencialAccesoDAO {

    // ========================================================================
    // Métodos simples (crean su propia conexión)
    // ========================================================================

    public CredencialAcceso crear(CredencialAcceso credencial) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection()) {
            return crear(credencial, conn);
        }
    }

    public boolean actualizar(CredencialAcceso credencial) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection()) {
            return actualizar(credencial, conn);
        }
    }

    // ========================================================================
    // Métodos transaccionales (reciben Connection externa)
    // ========================================================================

    public CredencialAcceso crear(CredencialAcceso credencial, Connection conn) throws SQLException {
        String sql = "INSERT INTO credencial_acceso (usuario_id, hash_password, salt, " +
                "ultimo_cambio, requiere_reset, intentos_fallidos, eliminado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, credencial.getUsuarioId());
            ps.setString(2, credencial.getHashPassword());
            ps.setString(3, credencial.getSalt());
            ps.setTimestamp(4, credencial.getUltimoCambio() != null ?
                    Timestamp.valueOf(credencial.getUltimoCambio()) : null);
            ps.setBoolean(5, credencial.isRequiereReset());
            ps.setInt(6, credencial.getIntentosFallidos());
            ps.setBoolean(7, credencial.isEliminado());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo crear la credencial");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    credencial.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado");
                }
            }

            return credencial;
        }
    }

    public boolean actualizar(CredencialAcceso credencial, Connection conn) throws SQLException {
        String sql = "UPDATE credencial_acceso SET hash_password = ?, salt = ?, " +
                "ultimo_cambio = ?, requiere_reset = ?, intentos_fallidos = ?, " +
                "eliminado = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, credencial.getHashPassword());
            ps.setString(2, credencial.getSalt());
            ps.setTimestamp(3, credencial.getUltimoCambio() != null ?
                    Timestamp.valueOf(credencial.getUltimoCambio()) : null);
            ps.setBoolean(4, credencial.isRequiereReset());
            ps.setInt(5, credencial.getIntentosFallidos());
            ps.setBoolean(6, credencial.isEliminado());
            ps.setLong(7, credencial.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    // ========================================================================
    // Métodos de consulta
    // ========================================================================

    public CredencialAcceso buscarPorUsuarioId(Long usuarioId) throws SQLException {
        String sql = "SELECT * FROM credencial_acceso WHERE usuario_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCredencial(rs);
                }
            }
        }
        return null;
    }

    public CredencialAcceso buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM credencial_acceso WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCredencial(rs);
                }
            }
        }
        return null;
    }

    // ========================================================================
    // Método auxiliar para mapear ResultSet
    // ========================================================================

    private CredencialAcceso mapearCredencial(ResultSet rs) throws SQLException {
        CredencialAcceso credencial = new CredencialAcceso();

        credencial.setId(rs.getLong("id"));
        credencial.setUsuarioId(rs.getLong("usuario_id"));
        credencial.setHashPassword(rs.getString("hash_password"));
        credencial.setSalt(rs.getString("salt"));
        credencial.setIntentosFallidos(rs.getInt("intentos_fallidos"));
        credencial.setRequiereReset(rs.getBoolean("requiere_reset"));
        credencial.setEliminado(rs.getBoolean("eliminado"));

        Timestamp ultimoCambio = rs.getTimestamp("ultimo_cambio");
        if (ultimoCambio != null) {
            credencial.setUltimoCambio(ultimoCambio.toLocalDateTime());
        }

        Timestamp ultimoLogin = rs.getTimestamp("ultimo_login");
        if (ultimoLogin != null) {
            credencial.setUltimoLogin(ultimoLogin.toLocalDateTime());
        }

        return credencial;
    }
}