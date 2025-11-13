package ar.edu.utn.tup.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase que representa a un usuario del sistema.
 *
 * Esta entidad mapea la tabla 'usuario' de la base de datos.
 * Cada usuario tiene información básica de identificación y una relación
 * uno a uno con CredencialAcceso.
 *
 * DECISIÓN DE DISEÑO - RELACIÓN 1→1 UNIDIRECCIONAL:
 *
 * Esta clase tiene una referencia a CredencialAcceso, lo que significa que
 * desde un objeto Usuario podés acceder directamente a su credencial:
 *
 *    Usuario usuario = ...;
 *    CredencialAcceso credencial = usuario.getCredencialAcceso();
 *
 * Sin embargo, CredencialAcceso NO tiene una referencia a Usuario. Esto es
 * lo que se llama "relación unidireccional". La navegación solo funciona en
 * una dirección: de Usuario hacia CredencialAcceso.
 *
 * ¿Por qué unidireccional?
 * - Desde el punto de vista del negocio, cuando trabajás con un usuario,
 *   naturalmente necesitás acceder a sus credenciales para verificar login,
 *   cambiar contraseña, etc.
 * - En cambio, cuando trabajás con una credencial aislada, generalmente no
 *   necesitás navegar hacia atrás para obtener todo el objeto Usuario.
 * - Si en algún momento necesitás encontrar el usuario de una credencial,
 *   el DAO se encarga de hacer la consulta SQL correspondiente.
 *
 * Esta estructura simplifica el código y evita referencias circulares entre
 * objetos.
 */
public class Usuario {

    // ========================================================================
    // Atributos de la entidad
    // ========================================================================

    /**
     * Identificador único del usuario (PK en la base de datos).
     * Es un Long para mapear BIGINT de MySQL.
     * Puede ser null cuando creamos un usuario nuevo que aún no fue persistido.
     */
    private Long id;

    /**
     * Indica si el usuario fue eliminado lógicamente.
     * false = usuario activo, true = usuario eliminado
     *
     * La baja lógica nos permite "eliminar" usuarios sin perder su información,
     * lo cual es importante para:
     * - Mantener integridad referencial con otras tablas
     * - Conservar historial de operaciones
     * - Cumplir con requisitos legales de auditoría
     * - Permitir recuperación de cuentas eliminadas por error
     */
    private boolean eliminado;

    /**
     * Nombre de usuario para login.
     * Debe ser único en el sistema.
     * Generalmente se usa para identificar al usuario al hacer login,
     * junto con la contraseña.
     */
    private String username;

    /**
     * Correo electrónico del usuario.
     * Debe ser único en el sistema.
     * Se usa para comunicaciones, recuperación de contraseña, etc.
     */
    private String email;

    /**
     * Nombre completo del usuario.
     * Puede contener espacios, acentos y caracteres especiales.
     * Ejemplo: "María José García López"
     */
    private String nombreCompleto;

    /**
     * Indica si el usuario está activo en el sistema.
     * true = puede usar el sistema, false = cuenta deshabilitada
     *
     * Es diferente de "eliminado":
     * - Un usuario INACTIVO está deshabilitado temporalmente pero no eliminado
     * - Un usuario ELIMINADO tiene baja lógica y no debería aparecer en búsquedas
     *
     * Casos de uso para inactivar:
     * - Suspensión temporal por incumplimiento de normas
     * - Cuenta en mantenimiento
     * - Usuario de licencia/vacaciones prolongadas
     */
    private boolean activo;

    /**
     * Fecha y hora de registro del usuario en el sistema.
     * Se asigna automáticamente al crear el usuario.
     * Útil para análisis, reportes y auditoría.
     */
    private LocalDateTime fechaRegistro;

    /**
     * Referencia a la credencial de acceso del usuario.
     *
     * Esta es la implementación de la relación 1→1 unidireccional.
     * Cada usuario tiene exactamente una credencial asociada.
     *
     * Puede ser null si:
     * - Acabamos de crear el usuario y aún no le asignamos credenciales
     * - Estamos en un proceso de recuperación de cuenta
     *
     * En un sistema real de producción, probablemente querrías que este campo
     * nunca sea null (un usuario sin credenciales no puede hacer login).
     */
    private CredencialAcceso credencialAcceso;

    // ========================================================================
    // Constructores
    // ========================================================================

    /**
     * Constructor vacío requerido para frameworks y DAOs.
     * Permite crear instancias sin parámetros que luego se inicializan
     * usando los setters.
     */
    public Usuario() {
        // Constructor vacío intencional
    }

    /**
     * Constructor con parámetros básicos para crear un usuario nuevo.
     * Los campos que se autogeneran (id, fechaRegistro) o tienen valores por
     * defecto (eliminado, activo) no se incluyen como parámetros.
     *
     * @param username Nombre de usuario para login
     * @param email Correo electrónico
     * @param nombreCompleto Nombre completo del usuario
     */
    public Usuario(String username, String email, String nombreCompleto) {
        this.username = username;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.eliminado = false;
        this.activo = true;
        this.fechaRegistro = LocalDateTime.now();
    }

    /**
     * Constructor completo con todos los campos.
     * Útil para cuando estamos reconstruyendo objetos desde la base de datos.
     *
     * @param id Identificador único
     * @param eliminado Si el usuario está eliminado lógicamente
     * @param username Nombre de usuario
     * @param email Correo electrónico
     * @param nombreCompleto Nombre completo
     * @param activo Si el usuario está activo
     * @param fechaRegistro Fecha de registro
     * @param credencialAcceso Credencial asociada
     */
    public Usuario(Long id, boolean eliminado, String username, String email,
                   String nombreCompleto, boolean activo, LocalDateTime fechaRegistro,
                   CredencialAcceso credencialAcceso) {
        this.id = id;
        this.eliminado = eliminado;
        this.username = username;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
        this.credencialAcceso = credencialAcceso;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public CredencialAcceso getCredencialAcceso() {
        return credencialAcceso;
    }

    public void setCredencialAcceso(CredencialAcceso credencialAcceso) {
        this.credencialAcceso = credencialAcceso;
    }

    // ========================================================================
    // Métodos de lógica de negocio
    // ========================================================================

    /**
     * Verifica si el usuario puede hacer login en el sistema.
     *
     * Un usuario puede hacer login solo si:
     * - NO está eliminado lógicamente
     * - Está activo
     * - Tiene credenciales asignadas
     *
     * @return true si puede hacer login, false en caso contrario
     */
    public boolean puedeHacerLogin() {
        return !eliminado && activo && credencialAcceso != null;
    }

    /**
     * Activa el usuario (habilita su cuenta).
     * Solo se puede activar un usuario que no esté eliminado.
     *
     * @return true si se activó exitosamente, false si está eliminado
     */
    public boolean activar() {
        if (eliminado) {
            return false;
        }
        this.activo = true;
        return true;
    }

    /**
     * Desactiva el usuario (deshabilita su cuenta temporalmente).
     * Esto es diferente de eliminar: un usuario inactivo puede reactivarse
     * fácilmente, mientras que un usuario eliminado requiere un proceso
     * más complejo de recuperación.
     */
    public void desactivar() {
        this.activo = false;
    }

    /**
     * Realiza la baja lógica del usuario.
     * Esto marca al usuario como eliminado sin borrar sus datos físicamente
     * de la base de datos.
     *
     * Un usuario eliminado también debe ser desactivado automáticamente.
     */
    public void eliminarLogicamente() {
        this.eliminado = true;
        this.activo = false;
    }

    /**
     * Restaura un usuario que fue eliminado lógicamente.
     * Lo reactiva y marca como no eliminado.
     */
    public void restaurar() {
        this.eliminado = false;
        this.activo = true;
    }

    /**
     * Verifica si el usuario necesita cambiar su contraseña.
     * Delega la verificación a la credencial asociada.
     *
     * @return true si debe cambiar la contraseña, false en caso contrario
     */
    public boolean necesitaCambiarPassword() {
        return credencialAcceso != null && credencialAcceso.isRequiereReset();
    }

    // ========================================================================
    // Métodos de Object: equals, hashCode, toString
    // ========================================================================

    /**
     * Dos usuarios son iguales si tienen el mismo ID.
     * El ID es la clave primaria, por lo que dos registros con el mismo ID
     * representan el mismo usuario en la base de datos.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
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
     * Representación en String del usuario.
     * Incluye los datos principales pero NO las credenciales por seguridad.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", activo=" + activo +
                ", fechaRegistro=" + fechaRegistro +
                ", tieneCredencial=" + (credencialAcceso != null) +
                '}';
    }
}