package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.dao.UsuarioDAO;
import ar.edu.utn.frbb.tup.entities.Usuario;

import java.sql.SQLException;
import java.util.List;

/**
 * Service para la entidad Usuario.
 *
 * Esta clase contiene la LÓGICA DE NEGOCIO relacionada con usuarios.
 *
 * DIFERENCIA ENTRE DAO Y SERVICE:
 *
 * El DAO (Data Access Object) es "tonto":
 * - Solo sabe cómo guardar, recuperar, actualizar y eliminar datos
 * - No tiene reglas de negocio
 * - No valida si una operación tiene sentido desde el punto de vista del negocio
 * - Ejemplo: El DAO insertará un usuario incluso si el email ya existe
 *
 * El Service es "inteligente":
 * - Contiene todas las reglas del negocio
 * - Valida que las operaciones tengan sentido
 * - Coordina múltiples operaciones de DAOs si es necesario
 * - Maneja transacciones cuando una operación involucra múltiples tablas
 * - Ejemplo: El Service verificará que el email no esté en uso antes de crear un usuario
 *
 * ¿Por qué separar DAO y Service?
 *
 * 1. Separación de responsabilidades: Cada clase tiene un propósito claro
 * 2. Reusabilidad: La misma lógica de negocio se puede usar desde diferentes lugares
 * 3. Testabilidad: Podés probar la lógica de negocio sin tocar la base de datos
 * 4. Mantenibilidad: Si cambian las reglas del negocio, solo modificás el Service
 *
 * VALIDACIONES:
 * El Service se encarga de todas las validaciones antes de delegar al DAO:
 * - Validar que los datos sean correctos (email válido, username no vacío, etc.)
 * - Validar que la operación sea permitida (no duplicar usernames, etc.)
 * - Aplicar reglas de negocio (usuario debe estar activo para hacer X, etc.)
 */
public class UsuarioService {

    // ========================================================================
    // Atributos
    // ========================================================================

    /**
     * DAO para acceder a la capa de persistencia.
     * El Service delega al DAO para las operaciones de base de datos,
     * pero agrega validaciones y lógica de negocio antes de hacerlo.
     */
    private final UsuarioDAO usuarioDAO;

    // ========================================================================
    // Constructor
    // ========================================================================

    /**
     * Constructor que inicializa el service con su DAO.
     */
    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // ========================================================================
    // Operaciones de negocio - CREATE
    // ========================================================================

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * Este método implementa todas las validaciones y reglas de negocio
     * necesarias antes de crear el usuario.
     *
     * Validaciones que realiza:
     * 1. Verifica que el username no esté vacío
     * 2. Verifica que el email no esté vacío y tenga formato válido
     * 3. Verifica que el username no esté ya en uso
     * 4. Verifica que el email no esté ya en uso
     *
     * Si todas las validaciones pasan, delega al DAO para la inserción.
     *
     * @param usuario El usuario a registrar
     * @return El usuario registrado con su ID asignado
     * @throws IllegalArgumentException Si las validaciones fallan
     * @throws SQLException Si hay un error en la base de datos
     */
    public Usuario registrarUsuario(Usuario usuario) throws SQLException {
        // Validación 1: Verificar que los campos obligatorios no estén vacíos
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El username no puede estar vacío");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }

        if (usuario.getNombreCompleto() == null || usuario.getNombreCompleto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo no puede estar vacío");
        }

        // Validación 2: Verificar formato del email
        if (!esEmailValido(usuario.getEmail())) {
            throw new IllegalArgumentException("El formato del email no es válido");
        }

        // Validación 3: Verificar que el username no esté en uso
        Usuario usuarioExistente = usuarioDAO.buscarPorUsername(usuario.getUsername());
        if (usuarioExistente != null) {
            throw new IllegalArgumentException("El username '" + usuario.getUsername() + "' ya está en uso");
        }

        // Validación 4: Verificar que el email no esté en uso
        usuarioExistente = usuarioDAO.buscarPorEmail(usuario.getEmail());
        if (usuarioExistente != null) {
            throw new IllegalArgumentException("El email '" + usuario.getEmail() + "' ya está en uso");
        }

        // Si todas las validaciones pasaron, crear el usuario
        return usuarioDAO.crear(usuario);
    }

    // ========================================================================
    // Operaciones de negocio - READ
    // ========================================================================

    /**
     * Busca un usuario por su ID.
     *
     * @param id El ID del usuario a buscar
     * @return El usuario encontrado, o null si no existe
     * @throws SQLException Si hay un error en la base de datos
     */
    public Usuario buscarUsuarioPorId(Long id) throws SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo");
        }
        return usuarioDAO.buscarPorId(id);
    }

    /**
     * Busca un usuario por su username.
     *
     * @param username El username a buscar
     * @return El usuario encontrado, o null si no existe
     * @throws SQLException Si hay un error en la base de datos
     */
    public Usuario buscarUsuarioPorUsername(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El username no puede estar vacío");
        }
        return usuarioDAO.buscarPorUsername(username);
    }

    /**
     * Busca un usuario por su email.
     *
     * @param email El email a buscar
     * @return El usuario encontrado, o null si no existe
     * @throws SQLException Si hay un error en la base de datos
     */
    public Usuario buscarUsuarioPorEmail(String email) throws SQLException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        return usuarioDAO.buscarPorEmail(email);
    }

    /**
     * Obtiene todos los usuarios no eliminados del sistema.
     *
     * @return Lista de todos los usuarios activos
     * @throws SQLException Si hay un error en la base de datos
     */
    public List<Usuario> listarTodosLosUsuarios() throws SQLException {
        return usuarioDAO.buscarTodos();
    }

    /**
     * Obtiene todos los usuarios activos (no eliminados y habilitados).
     *
     * @return Lista de usuarios activos
     * @throws SQLException Si hay un error en la base de datos
     */
    public List<Usuario> listarUsuariosActivos() throws SQLException {
        return usuarioDAO.buscarActivos();
    }

    // ========================================================================
    // Operaciones de negocio - UPDATE
    // ========================================================================

    /**
     * Actualiza la información de un usuario existente.
     *
     * Validaciones que realiza:
     * 1. Verifica que el usuario tenga ID (es una actualización, no una creación)
     * 2. Verifica que el usuario exista en la base de datos
     * 3. Si se cambia el username, verifica que el nuevo no esté en uso
     * 4. Si se cambia el email, verifica que el nuevo no esté en uso
     *
     * @param usuario El usuario con los datos actualizados
     * @return El usuario actualizado
     * @throws IllegalArgumentException Si las validaciones fallan
     * @throws SQLException Si hay un error en la base de datos
     */
    public Usuario actualizarUsuario(Usuario usuario) throws SQLException {
        // Validación 1: El usuario debe tener ID
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("No se puede actualizar un usuario sin ID");
        }

        // Validación 2: El usuario debe existir
        Usuario usuarioExistente = usuarioDAO.buscarPorId(usuario.getId());
        if (usuarioExistente == null) {
            throw new IllegalArgumentException("No existe un usuario con ID " + usuario.getId());
        }

        // Validación 3: Verificar campos obligatorios
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El username no puede estar vacío");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }

        if (!esEmailValido(usuario.getEmail())) {
            throw new IllegalArgumentException("El formato del email no es válido");
        }

        // Validación 4: Si cambió el username, verificar que el nuevo no esté en uso
        if (!usuario.getUsername().equals(usuarioExistente.getUsername())) {
            Usuario usuarioConMismoUsername = usuarioDAO.buscarPorUsername(usuario.getUsername());
            if (usuarioConMismoUsername != null) {
                throw new IllegalArgumentException("El username '" + usuario.getUsername() + "' ya está en uso");
            }
        }

        // Validación 5: Si cambió el email, verificar que el nuevo no esté en uso
        if (!usuario.getEmail().equals(usuarioExistente.getEmail())) {
            Usuario usuarioConMismoEmail = usuarioDAO.buscarPorEmail(usuario.getEmail());
            if (usuarioConMismoEmail != null) {
                throw new IllegalArgumentException("El email '" + usuario.getEmail() + "' ya está en uso");
            }
        }

        // Si todas las validaciones pasaron, actualizar
        boolean actualizado = usuarioDAO.actualizar(usuario);
        if (!actualizado) {
            throw new SQLException("No se pudo actualizar el usuario");
        }

        return usuarioDAO.buscarPorId(usuario.getId());
    }

    /**
     * Activa un usuario que estaba deshabilitado.
     *
     * Un usuario eliminado NO puede ser activado (debe restaurarse primero).
     *
     * @param id El ID del usuario a activar
     * @return El usuario activado
     * @throws IllegalArgumentException Si el usuario no existe o está eliminado
     * @throws SQLException Si hay un error en la base de datos
     */
    public Usuario activarUsuario(Long id) throws SQLException {
        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con ID " + id);
        }

        if (usuario.isEliminado()) {
            throw new IllegalArgumentException("No se puede activar un usuario eliminado. Debe restaurarse primero.");
        }

        if (usuario.isActivo()) {
            // Ya está activo, no hacer nada
            return usuario;
        }

        usuario.setActivo(true);
        usuarioDAO.actualizar(usuario);

        return usuarioDAO.buscarPorId(id);
    }

    /**
     * Desactiva un usuario (lo deshabilita temporalmente).
     *
     * Esto es diferente de eliminarlo: un usuario inactivo puede reactivarse
     * fácilmente, mientras que uno eliminado requiere restauración.
     *
     * @param id El ID del usuario a desactivar
     * @return El usuario desactivado
     * @throws IllegalArgumentException Si el usuario no existe
     * @throws SQLException Si hay un error en la base de datos
     */
    public Usuario desactivarUsuario(Long id) throws SQLException {
        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con ID " + id);
        }

        if (!usuario.isActivo()) {
            // Ya está inactivo, no hacer nada
            return usuario;
        }

        usuario.setActivo(false);
        usuarioDAO.actualizar(usuario);

        return usuarioDAO.buscarPorId(id);
    }

    // ========================================================================
    // Operaciones de negocio - DELETE
    // ========================================================================

    /**
     * Realiza la baja lógica de un usuario.
     *
     * La baja lógica marca al usuario como eliminado sin borrarlo físicamente.
     * Esto conserva el historial y permite recuperación si fue un error.
     *
     * Un usuario eliminado también se desactiva automáticamente.
     *
     * @param id El ID del usuario a eliminar
     * @return true si se eliminó correctamente
     * @throws IllegalArgumentException Si el usuario no existe
     * @throws SQLException Si hay un error en la base de datos
     */
    public boolean eliminarUsuario(Long id) throws SQLException {
        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con ID " + id);
        }

        if (usuario.isEliminado()) {
            throw new IllegalArgumentException("El usuario ya está eliminado");
        }

        return usuarioDAO.eliminarLogico(id);
    }

    /**
     * Restaura un usuario que fue eliminado lógicamente.
     *
     * @param id El ID del usuario a restaurar
     * @return El usuario restaurado
     * @throws IllegalArgumentException Si el usuario no existe o no está eliminado
     * @throws SQLException Si hay un error en la base de datos
     */
    public Usuario restaurarUsuario(Long id) throws SQLException {
        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con ID " + id);
        }

        if (!usuario.isEliminado()) {
            throw new IllegalArgumentException("El usuario no está eliminado, no hay nada que restaurar");
        }

        usuario.setEliminado(false);
        usuario.setActivo(true);
        usuarioDAO.actualizar(usuario);

        return usuarioDAO.buscarPorId(id);
    }

    // ========================================================================
    // Métodos auxiliares de validación
    // ========================================================================

    /**
     * Valida el formato de un email.
     *
     * Esta es una validación básica. En producción usarías una expresión
     * regular más completa o una librería especializada.
     *
     * @param email El email a validar
     * @return true si el formato es válido, false en caso contrario
     */
    private boolean esEmailValido(String email) {
        // Validación simple: debe contener @ y un punto después del @
        // En producción usarías una regex más robusta o una librería
        if (email == null) {
            return false;
        }

        int posicionArroba = email.indexOf('@');
        if (posicionArroba <= 0) {
            return false;  // No tiene @ o está al principio
        }

        int posicionPunto = email.indexOf('.', posicionArroba);
        if (posicionPunto <= posicionArroba + 1) {
            return false;  // No tiene punto después del @ o está inmediatamente después
        }

        return posicionPunto < email.length() - 1;  // El punto no debe estar al final
    }
}