package ar.edu.utn.tup.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class CredencialAcceso {

    private Long id;
    private boolean eliminado;
    private Long usuarioId;  // FK hacia usuario
    private String hashPassword;
    private String salt;
    private LocalDateTime ultimoCambio;
    private boolean requiereReset;
    private int intentosFallidos;
    private LocalDateTime ultimoLogin;

    // Constructor vacío
    public CredencialAcceso() {
    }

    // Constructor con parámetros básicos
    public CredencialAcceso(String hashPassword, String salt, boolean requiereReset) {
        this.hashPassword = hashPassword;
        this.salt = salt;
        this.requiereReset = requiereReset;
        this.eliminado = false;
        this.intentosFallidos = 0;
        this.ultimoCambio = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public LocalDateTime getUltimoCambio() {
        return ultimoCambio;
    }

    public void setUltimoCambio(LocalDateTime ultimoCambio) {
        this.ultimoCambio = ultimoCambio;
    }

    public boolean isRequiereReset() {
        return requiereReset;
    }

    public void setRequiereReset(boolean requiereReset) {
        this.requiereReset = requiereReset;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    // Métodos de lógica de negocio
    public void registrarIntentoFallido() {
        this.intentosFallidos++;
    }

    public void registrarLoginExitoso() {
        this.intentosFallidos = 0;
        this.ultimoLogin = LocalDateTime.now();
    }

    public boolean debeBloquearPorIntentos(int maxIntentos) {
        return this.intentosFallidos >= maxIntentos;
    }

    public void actualizarPassword(String nuevoHash, String nuevoSalt) {
        this.hashPassword = nuevoHash;
        this.salt = nuevoSalt;
        this.ultimoCambio = LocalDateTime.now();
        this.requiereReset = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredencialAcceso that = (CredencialAcceso) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CredencialAcceso{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", usuarioId=" + usuarioId +
                ", ultimoCambio=" + ultimoCambio +
                ", requiereReset=" + requiereReset +
                ", intentosFallidos=" + intentosFallidos +
                ", ultimoLogin=" + ultimoLogin +
                '}';
    }
}