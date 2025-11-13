package ar.edu.utn.tup.service;

import ar.edu.utn.tup.dao.CredencialAccesoDAO;
import ar.edu.utn.tup.entities.CredencialAcceso;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class CredencialAccesoService {

    private final CredencialAccesoDAO credencialDAO;

    public CredencialAccesoService() {
        this.credencialDAO = new CredencialAccesoDAO();
    }

    /**
     * Busca una credencial por ID de usuario
     */
    public CredencialAcceso buscarPorUsuarioId(Long usuarioId) throws SQLException {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido");
        }
        return credencialDAO.buscarPorUsuarioId(usuarioId);
    }

    /**
     * Busca una credencial por su ID
     */
    public CredencialAcceso buscarPorId(Long id) throws SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return credencialDAO.buscarPorId(id);
    }

    /**
     * Actualiza una credencial existente
     */
    public boolean actualizar(CredencialAcceso credencial) throws SQLException {
        if (credencial == null) {
            throw new IllegalArgumentException("Credencial no puede ser null");
        }
        if (credencial.getId() == null) {
            throw new IllegalArgumentException("Credencial debe tener ID");
        }
        return credencialDAO.actualizar(credencial);
    }

    /**
     * Cambia la contraseña de una credencial
     */
    public boolean cambiarPassword(Long credencialId, String nuevoPassword) throws SQLException {
        CredencialAcceso credencial = buscarPorId(credencialId);
        if (credencial == null) {
            throw new IllegalArgumentException("Credencial no encontrada");
        }

        // Hashear nueva contraseña
        String[] hashYSalt = hashearPassword(nuevoPassword);
        credencial.setHashPassword(hashYSalt[0]);
        credencial.setSalt(hashYSalt[1]);
        credencial.setUltimoCambio(LocalDateTime.now());
        credencial.setRequiereReset(false);

        return credencialDAO.actualizar(credencial);
    }

    /**
     * Registra un intento de login fallido
     */
    public void registrarIntentoFallido(Long credencialId) throws SQLException {
        CredencialAcceso credencial = buscarPorId(credencialId);
        if (credencial != null) {
            credencial.registrarIntentoFallido();
            credencialDAO.actualizar(credencial);
        }
    }

    /**
     * Registra un login exitoso
     */
    public void registrarLoginExitoso(Long credencialId) throws SQLException {
        CredencialAcceso credencial = buscarPorId(credencialId);
        if (credencial != null) {
            credencial.registrarLoginExitoso();
            credencialDAO.actualizar(credencial);
        }
    }

    // ========================================================================
    // Utilidad para hashear contraseñas
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