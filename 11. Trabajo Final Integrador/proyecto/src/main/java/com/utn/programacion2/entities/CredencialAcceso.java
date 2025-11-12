package ar.edu.utn.frbb.tup.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase que representa las credenciales de acceso de un usuario.
 *
 * Esta entidad mapea la tabla 'credencial_acceso' de la base de datos.
 * Contiene toda la información relacionada con la autenticación y seguridad
 * de la cuenta de un usuario.
 *
 * DECISIÓN DE DISEÑO:
 * Notá que esta clase NO tiene una referencia al Usuario. Aunque en la base
 * de datos credencial_acceso tiene un usuario_id, en nuestro modelo de objetos
 * la navegación es unidireccional desde Usuario hacia CredencialAcceso.
 *
 * Esto significa que:
 * - Desde un Usuario podés acceder a su CredencialAcceso
 * - Pero desde una CredencialAcceso no podés navegar hacia atrás al Usuario
 *
 * ¿Por qué? Porque desde el punto de vista del negocio, cuando trabajás con
 * un usuario necesitás acceder a sus credenciales, pero cuando trabajás con
 * una credencial aislada generalmente no necesitás el objeto Usuario completo.
 * El DAO se encarga de manejar la relación en la base de datos.
 */
public class CredencialAcceso {

    // ========================================================================
    // Atributos de la entidad
    // ========================================================================

    /**
     * Identificador único de la credencial (PK en la base de datos).
     * Es un Long para mapear BIGINT de MySQL.
     * Puede ser null cuando creamos una credencial nueva que aún no fue persistida.
     */
    private Long id;

    /**
     * Indica si la credencial fue eliminada lógicamente.
     * false = credencial activa, true = credencial eliminada
     */
    private boolean eliminado;

    /**
     * Hash de la contraseña del usuario.
     * NUNCA almacenamos contraseñas en texto plano por seguridad.
     * Este campo contiene el resultado de aplicar un algoritmo de hashing
     * seguro (como BCrypt) sobre la contraseña original.
     *
     * Ejemplo de hash BCrypt:
     * $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
     */
    private String hashPassword;

    /**
     * Salt: valor aleatorio usado en el proceso de hashing.
     * El salt hace que dos usuarios con la misma contraseña tengan hashes
     * diferentes, lo cual mejora significativamente la seguridad.
     *
     * En BCrypt el salt está incluido en el hash, pero podemos guardarlo
     * por separado si usamos otro algoritmo.
     */
    private String salt;

    /**
     * Fecha y hora del último cambio de contraseña.
     * Se usa para implementar políticas de expiración de contraseñas.
     * Por ejemplo, podríamos requerir que los usuarios cambien su contraseña
     * cada 90 días.
     */
    private LocalDateTime ultimoCambio;

    /**
     * Indica si el usuario debe cambiar su contraseña en el próximo login.
     * Casos de uso:
     * - Contraseña temporal asignada por un administrador
     * - Contraseña vencida por política de seguridad
     * - Reset de contraseña solicitado por el usuario
     */
    private boolean requiereReset;

    /**
     * Contador de intentos de login fallidos consecutivos.
     * Se usa para implementar bloqueo de cuenta tras varios intentos fallidos,
     * como protección contra ataques de fuerza bruta.
     * Se resetea a 0 tras un login exitoso.
     */
    private int intentosFallidos;

    /**
     * Fecha y hora del último login exitoso.
     * Útil para auditoría y para detectar cuentas inactivas.
     * Es null si el usuario nunca hizo login.
     */
    private LocalDateTime ultimoLogin;

    // ========================================================================
    // Constructores
    // ========================================================================

    /**
     * Constructor vacío requerido para frameworks y DAOs.
     * Permite crear instancias sin parámetros que luego se inicializan
     * usando los setters.
     */
    public CredencialAcceso() {
        // Constructor vacío intencional
    }

    /**
     * Constructor con parámetros básicos para crear una credencial nueva.
     * Los campos que se autogeneran (id) o tienen valores por defecto
     * (eliminado, intentosFallidos) no se incluyen como parámetros.
     *
     * @param hashPassword Hash de la contraseña del usuario
     * @param salt Salt usado en el proceso de hashing
     * @param requiereReset Si debe cambiar la contraseña en el próximo login
     */
    public CredencialAcceso(String hashPassword, String salt, boolean requiereReset) {
        this.hashPassword = hashPassword;
        this.salt = salt;
        this.requiereReset = requiereReset;
        this.eliminado = false;
        this.intentosFallidos = 0;
        this.ultimoCambio = LocalDateTime.now();
    }

    /**
     * Constructor completo con todos los campos.
     * Útil para cuando estamos reconstruyendo objetos desde la base de datos.
     *
     * @param id Identificador único
     * @param eliminado Si la credencial está eliminada lógicamente
     * @param hashPassword Hash de la contraseña
     * @param salt Salt usado en el hashing
     * @param ultimoCambio Fecha del último cambio de contraseña
     * @param requiereReset Si debe cambiar la contraseña
     * @param intentosFallidos Número de intentos fallidos
     * @param ultimoLogin Fecha del último login exitoso
     */
    public CredencialAcceso(Long id, boolean eliminado, String hashPassword, String salt,
                            LocalDateTime ultimoCambio, boolean requiereReset,
                            int intentosFallidos, LocalDateTime ultimoLogin) {
        this.id = id;
        this.eliminado = eliminado;
        this.hashPassword = hashPassword;
        this.salt = salt;
        this.ultimoCambio = ultimoCambio;
        this.requiereReset = requiereReset;
        this.intentosFallidos = intentosFallidos;
        this.ultimoLogin = ultimoLogin;
    }

    // ========================================================================
    // Getters y Setters
    // ========================================================================

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

    // ========================================================================
    // Métodos de lógica de negocio
    // ========================================================================

    /**
     * Registra un intento de login fallido.
     * Incrementa el contador de intentos fallidos.
     *
     * Este método encapsula la lógica de negocio en la entidad misma,
     * siguiendo el principio de que los objetos deben controlar su propio estado.
     */
    public void registrarIntentoFallido() {
        this.intentosFallidos++;
    }

    /**
     * Registra un login exitoso.
     * Resetea el contador de intentos fallidos y actualiza la fecha de último login.
     */
    public void registrarLoginExitoso() {
        this.intentosFallidos = 0;
        this.ultimoLogin = LocalDateTime.now();
    }

    /**
     * Verifica si la cuenta debe ser bloqueada por demasiados intentos fallidos.
     *
     * @param maxIntentos Número máximo de intentos permitidos
     * @return true si se debe bloquear la cuenta, false en caso contrario
     */
    public boolean debeBloquearPorIntentos(int maxIntentos) {
        return this.intentosFallidos >= maxIntentos;
    }

    /**
     * Actualiza la contraseña (el hash) de la credencial.
     * También actualiza la fecha de último cambio y resetea la flag de requiere reset.
     *
     * @param nuevoHash Nuevo hash de la contraseña
     * @param nuevoSalt Nuevo salt usado
     */
    public void actualizarPassword(String nuevoHash, String nuevoSalt) {
        this.hashPassword = nuevoHash;
        this.salt = nuevoSalt;
        this.ultimoCambio = LocalDateTime.now();
        this.requiereReset = false;
    }

    // ========================================================================
    // Métodos de Object: equals, hashCode, toString
    // ========================================================================

    /**
     * Dos credenciales son iguales si tienen el mismo ID.
     * El ID es la clave primaria, por lo que dos registros con el mismo ID
     * representan la misma credencial en la base de datos.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredencialAcceso that = (CredencialAcceso) o;
        return Objects.equals(id, that.id);
    }

    /**
     * El hashCode debe ser consistente con equals.
     * Si dos objetos son iguales según equals, deben tener el mismo hashCode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Representación en String de la credencial.
     * NO incluimos el hash de la contraseña por seguridad.
     */
    @Override
    public String toString() {
        return "CredencialAcceso{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", ultimoCambio=" + ultimoCambio +
                ", requiereReset=" + requiereReset +
                ", intentosFallidos=" + intentosFallidos +
                ", ultimoLogin=" + ultimoLogin +
                '}';
    }
}
