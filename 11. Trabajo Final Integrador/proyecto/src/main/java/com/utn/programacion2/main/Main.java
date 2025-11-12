package ar.edu.utn.frbb.tup.main;

import ar.edu.utn.frbb.tup.config.DatabaseConfig;
import ar.edu.utn.frbb.tup.entities.CredencialAcceso;
import ar.edu.utn.frbb.tup.entities.Usuario;
import ar.edu.utn.frbb.tup.service.CredencialAccesoService;
import ar.edu.utn.frbb.tup.service.UsuarioService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que contiene el punto de entrada de la aplicación.
 *
 * Esta clase implementa un menú interactivo por consola que permite al usuario
 * realizar operaciones CRUD sobre usuarios y credenciales de acceso.
 *
 * ARQUITECTURA EN CAPAS:
 *
 * Main (Presentación)
 *   ↓ usa
 * Services (Lógica de Negocio)
 *   ↓ usa
 * DAOs (Acceso a Datos)
 *   ↓ usa
 * Base de Datos MySQL
 *
 * Esta separación en capas tiene varios beneficios:
 * 1. Cada capa tiene una responsabilidad clara
 * 2. Podés cambiar una capa sin afectar las otras
 * 3. Podés probar cada capa independientemente
 * 4. El código es más mantenible y escalable
 *
 * Por ejemplo, podrías reemplazar este menú de consola con una interfaz
 * gráfica o una API REST sin cambiar nada en los Services o DAOs.
 */
public class Main {

    // ========================================================================
    // Atributos
    // ========================================================================

    /**
     * Scanner para leer entrada del usuario desde consola.
     * Lo declaramos como atributo para reutilizarlo en todos los métodos.
     */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Service de usuarios para realizar operaciones de negocio.
     */
    private static final UsuarioService usuarioService = new UsuarioService();

    /**
     * Service de credenciales para operaciones de autenticación y seguridad.
     */
    private static final CredencialAccesoService credencialService = new CredencialAccesoService();

    // ========================================================================
    // Método principal
    // ========================================================================

    /**
     * Punto de entrada de la aplicación.
     *
     * Primero verifica que la conexión a la base de datos funcione,
     * luego muestra el menú principal.
     */
    public static void main(String[] args) {
        imprimirEncabezado();

        // Verificar conexión a la base de datos antes de continuar
        if (!verificarConexionBD()) {
            System.err.println("\n✗ No se pudo conectar a la base de datos.");
            System.err.println("Verificá que MySQL esté corriendo y que la configuración sea correcta.");
            return;
        }

        System.out.println("\n✓ Conexión a base de datos exitosa\n");

        // Mostrar el menú principal
        mostrarMenuPrincipal();

        // Cerrar el scanner al finalizar
        scanner.close();
        System.out.println("\n¡Hasta luego!");
    }

    // ========================================================================
    // Métodos del menú principal
    // ========================================================================

    /**
     * Muestra el menú principal y procesa las opciones del usuario.
     */
    private static void mostrarMenuPrincipal() {
        boolean continuar = true;

        while (continuar) {
            imprimirMenuPrincipal();

            try {
                int opcion = leerEntero("Ingresá una opción: ");

                switch (opcion) {
                    case 1:
                        menuGestionUsuarios();
                        break;
                    case 2:
                        menuGestionCredenciales();
                        break;
                    case 3:
                        menuAutenticacion();
                        break;
                    case 0:
                        continuar = false;
                        break;
                    default:
                        System.out.println("\n✗ Opción inválida. Intentá de nuevo.\n");
                }
            } catch (Exception e) {
                System.err.println("\n✗ Error: " + e.getMessage() + "\n");
                scanner.nextLine(); // Limpiar el buffer
            }
        }
    }

    /**
     * Imprime el menú principal en pantalla.
     */
    private static void imprimirMenuPrincipal() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║         MENÚ PRINCIPAL                     ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║  1. Gestión de Usuarios                    ║");
        System.out.println("║  2. Gestión de Credenciales                ║");
        System.out.println("║  3. Autenticación / Login                  ║");
        System.out.println("║  0. Salir                                  ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }

    // ========================================================================
    // Menú de Gestión de Usuarios
    // ========================================================================

    /**
     * Menú para gestionar usuarios (CRUD completo).
     */
    private static void menuGestionUsuarios() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║       GESTIÓN DE USUARIOS                  ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║  1. Crear nuevo usuario                    ║");
            System.out.println("║  2. Listar todos los usuarios              ║");
            System.out.println("║  3. Buscar usuario por ID                  ║");
            System.out.println("║  4. Buscar usuario por username            ║");
            System.out.println("║  5. Actualizar usuario                     ║");
            System.out.println("║  6. Activar/Desactivar usuario             ║");
            System.out.println("║  7. Eliminar usuario (baja lógica)         ║");
            System.out.println("║  0. Volver al menú principal               ║");
            System.out.println("╚════════════════════════════════════════════╝");

            try {
                int opcion = leerEntero("\nIngresá una opción: ");

                switch (opcion) {
                    case 1:
                        crearUsuario();
                        break;
                    case 2:
                        listarUsuarios();
                        break;
                    case 3:
                        buscarUsuarioPorId();
                        break;
                    case 4:
                        buscarUsuarioPorUsername();
                        break;
                    case 5:
                        actualizarUsuario();
                        break;
                    case 6:
                        activarDesactivarUsuario();
                        break;
                    case 7:
                        eliminarUsuario();
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("\n✗ Opción inválida.\n");
                }
            } catch (Exception e) {
                System.err.println("\n✗ Error: " + e.getMessage() + "\n");
            }
        }
    }

    /**
     * Crea un nuevo usuario con su credencial de acceso.
     */
    private static void crearUsuario() {
        System.out.println("\n═══ CREAR NUEVO USUARIO ═══\n");

        try {
            // Solicitar datos del usuario
            String username = leerString("Username: ");
            String email = leerString("Email: ");
            String nombreCompleto = leerString("Nombre completo: ");

            // Crear el usuario
            Usuario usuario = new Usuario(username, email, nombreCompleto);

            // Preguntar si desea crear credenciales
            boolean crearCredencial = leerBoolean("¿Desea crear credenciales de acceso ahora? (S/N): ");

            if (crearCredencial) {
                String password = leerString("Contraseña: ");
                boolean requiereReset = leerBoolean("¿Debe cambiar la contraseña en el próximo login? (S/N): ");

                // Crear el usuario primero
                Usuario usuarioCreado = usuarioService.registrarUsuario(usuario);

                // Crear la credencial
                CredencialAcceso credencial = credencialService.crearCredencial(
                        usuarioCreado.getId(),
                        password,
                        requiereReset
                );

                // Asociar la credencial al usuario
                usuarioCreado.setCredencialAcceso(credencial);

                System.out.println("\n✓ Usuario y credencial creados exitosamente");
                mostrarUsuario(usuarioCreado);
            } else {
                Usuario usuarioCreado = usuarioService.registrarUsuario(usuario);
                System.out.println("\n✓ Usuario creado exitosamente (sin credenciales)");
                mostrarUsuario(usuarioCreado);
            }

        } catch (IllegalArgumentException e) {
            System.err.println("\n✗ Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Lista todos los usuarios del sistema.
     */
    private static void listarUsuarios() {
        System.out.println("\n═══ LISTA DE USUARIOS ═══\n");

        try {
            List<Usuario> usuarios = usuarioService.listarTodosLosUsuarios();

            if (usuarios.isEmpty()) {
                System.out.println("No hay usuarios registrados.");
            } else {
                for (Usuario usuario : usuarios) {
                    mostrarUsuarioResumido(usuario);
                    System.out.println("─".repeat(60));
                }
                System.out.println("\nTotal de usuarios: " + usuarios.size());
            }
        } catch (SQLException e) {
            System.err.println("\n✗ Error al listar usuarios: " + e.getMessage());
        }
    }

    /**
     * Busca y muestra un usuario por su ID.
     */
    private static void buscarUsuarioPorId() {
        System.out.println("\n═══ BUSCAR USUARIO POR ID ═══\n");

        try {
            Long id = leerLong("ID del usuario: ");
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);

            if (usuario == null) {
                System.out.println("\n✗ No se encontró un usuario con ID " + id);
            } else {
                System.out.println("\n✓ Usuario encontrado:");
                mostrarUsuario(usuario);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("\n✗ Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Busca y muestra un usuario por su username.
     */
    private static void buscarUsuarioPorUsername() {
        System.out.println("\n═══ BUSCAR USUARIO POR USERNAME ═══\n");

        try {
            String username = leerString("Username: ");
            Usuario usuario = usuarioService.buscarUsuarioPorUsername(username);

            if (usuario == null) {
                System.out.println("\n✗ No se encontró un usuario con username '" + username + "'");
            } else {
                System.out.println("\n✓ Usuario encontrado:");
                mostrarUsuario(usuario);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("\n✗ Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Actualiza los datos de un usuario existente.
     */
    private static void actualizarUsuario() {
        System.out.println("\n═══ ACTUALIZAR USUARIO ═══\n");

        try {
            Long id = leerLong("ID del usuario a actualizar: ");
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);

            if (usuario == null) {
                System.out.println("\n✗ No se encontró un usuario con ID " + id);
                return;
            }

            System.out.println("\nUsuario actual:");
            mostrarUsuario(usuario);

            System.out.println("\nIngresá los nuevos datos (dejá en blanco para no cambiar):");

            String nuevoUsername = leerStringOpcional("Nuevo username [" + usuario.getUsername() + "]: ");
            if (!nuevoUsername.isEmpty()) {
                usuario.setUsername(nuevoUsername);
            }

            String nuevoEmail = leerStringOpcional("Nuevo email [" + usuario.getEmail() + "]: ");
            if (!nuevoEmail.isEmpty()) {
                usuario.setEmail(nuevoEmail);
            }

            String nuevoNombre = leerStringOpcional("Nuevo nombre completo [" + usuario.getNombreCompleto() + "]: ");
            if (!nuevoNombre.isEmpty()) {
                usuario.setNombreCompleto(nuevoNombre);
            }

            Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuario);
            System.out.println("\n✓ Usuario actualizado exitosamente");
            mostrarUsuario(usuarioActualizado);

        } catch (IllegalArgumentException e) {
            System.err.println("\n✗ Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Activa o desactiva un usuario.
     */
    private static void activarDesactivarUsuario() {
        System.out.println("\n═══ ACTIVAR/DESACTIVAR USUARIO ═══\n");

        try {
            Long id = leerLong("ID del usuario: ");
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);

            if (usuario == null) {
                System.out.println("\n✗ No se encontró un usuario con ID " + id);
                return;
            }

            System.out.println("\nUsuario seleccionado:");
            mostrarUsuario(usuario);

            String accion = usuario.isActivo() ? "desactivar" : "activar";
            boolean confirmar = leerBoolean("\n¿Desea " + accion + " este usuario? (S/N): ");

            if (confirmar) {
                Usuario usuarioActualizado;
                if (usuario.isActivo()) {
                    usuarioActualizado = usuarioService.desactivarUsuario(id);
                    System.out.println("\n✓ Usuario desactivado exitosamente");
                } else {
                    usuarioActualizado = usuarioService.activarUsuario(id);
                    System.out.println("\n✓ Usuario activado exitosamente");
                }
                mostrarUsuario(usuarioActualizado);
            } else {
                System.out.println("\nOperación cancelada.");
            }

        } catch (IllegalArgumentException e) {
            System.err.println("\n✗ Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Elimina lógicamente un usuario.
     */
    private static void eliminarUsuario() {
        System.out.println("\n═══ ELIMINAR USUARIO (BAJA LÓGICA) ═══\n");

        try {
            Long id = leerLong("ID del usuario a eliminar: ");
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);

            if (usuario == null) {
                System.out.println("\n✗ No se encontró un usuario con ID " + id);
                return;
            }

            System.out.println("\nUsuario seleccionado:");
            mostrarUsuario(usuario);

            System.out.println("\n⚠️  ADVERTENCIA: Esta operación marcará al usuario como eliminado.");
            System.out.println("El usuario y su credencial no se borrarán físicamente de la base de datos.");

            boolean confirmar = leerBoolean("\n¿Está seguro que desea eliminar este usuario? (S/N): ");

            if (confirmar) {
                boolean eliminado = usuarioService.eliminarUsuario(id);
                if (eliminado) {
                    System.out.println("\n✓ Usuario eliminado exitosamente");
                } else {
                    System.out.println("\n✗ No se pudo eliminar el usuario");
                }
            } else {
                System.out.println("\nOperación cancelada.");
            }

        } catch (IllegalArgumentException e) {
            System.err.println("\n✗ Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    // ========================================================================
    // Menú de Gestión de Credenciales
    // ========================================================================

    /**
     * Menú para gestionar credenciales de acceso.
     */
    private static void menuGestionCredenciales() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║      GESTIÓN DE CREDENCIALES              ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║  1. Crear credencial para usuario          ║");
            System.out.println("║  2. Cambiar contraseña                     ║");
            System.out.println("║  3. Buscar credencial por ID               ║");
            System.out.println("║  4. Listar credenciales que requieren reset║");
            System.out.println("║  0. Volver al menú principal               ║");
            System.out.println("╚════════════════════════════════════════════╝");

            try {
                int opcion = leerEntero("\nIngresá una opción: ");

                switch (opcion) {
                    case 1:
                        crearCredencial();
                        break;
                    case 2:
                        cambiarPassword();
                        break;
                    case 3:
                        buscarCredencialPorId();
                        break;
                    case 4:
                        listarCredencialesQueRequierenReset();
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("\n✗ Opción inválida.\n");
                }
            } catch (Exception e) {
                System.err.println("\n✗ Error: " + e.getMessage() + "\n");
            }
        }
    }

    /**
     * Crea una credencial para un usuario que no tiene.
     */
    private static void crearCredencial() {
        System.out.println("\n═══ CREAR CREDENCIAL ═══\n");

        try {
            Long usuarioId = leerLong("ID del usuario: ");
            Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);

            if (usuario == null) {
                System.out.println("\n✗ No se encontró un usuario con ID " + usuarioId);
                return;
            }

            if (usuario.getCredencialAcceso() != null) {
                System.out.println("\n✗ El usuario ya tiene una credencial asociada");
                System.out.println("Use la opción 'Cambiar contraseña' para modificarla.");
                return;
            }

            String password = leerString("Contraseña: ");
            boolean requiereReset = leerBoolean("¿Debe cambiar la contraseña en el próximo login? (S/N): ");

            CredencialAcceso credencial = credencialService.crearCredencial(usuarioId, password, requiereReset);

            System.out.println("\n✓ Credencial creada exitosamente");
            System.out.println("ID de la credencial: " + credencial.getId());

        } catch (IllegalArgumentException e) {
            System.err.println("\n✗ Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Cambia la contraseña de una credencial.
     */
    private static void cambiarPassword() {
        System.out.println("\n═══ CAMBIAR CONTRASEÑA ═══\n");

        try {
            Long credencialId = leerLong("ID de la credencial: ");
            CredencialAcceso credencial = credencialService.buscarCredencialPorId(credencialId);

            if (credencial == null) {
                System.out.println("\n✗ No se encontró una credencial con ID " + credencialId);
                return;
            }

            System.out.println("\nCredencial encontrada (ID: " + credencial.getId() + ")");
            System.out.println("Último cambio: " + credencial.getUltimoCambio());
            System.out.println("Requiere reset: " + (credencial.isRequiereReset() ? "Sí" : "No"));

            String nuevaPassword = leerString("\nNueva contraseña: ");

            CredencialAcceso credencialActualizada = credencialService.cambiarPassword(credencialId, nuevaPassword);

            System.out.println("\n✓ Contraseña cambiada exitosamente");
            System.out.println("Nuevo último cambio: " + credencialActualizada.getUltimoCambio());

        } catch (IllegalArgumentException e) {
            System.err.println("\n✗ Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Busca una credencial por su ID.
     */
    private static void buscarCredencialPorId() {
        System.out.println("\n═══ BUSCAR CREDENCIAL POR ID ═══\n");

        try {
            Long id = leerLong("ID de la credencial: ");
            CredencialAcceso credencial = credencialService.buscarCredencialPorId(id);

            if (credencial == null) {
                System.out.println("\n✗ No se encontró una credencial con ID " + id);
            } else {
                System.out.println("\n✓ Credencial encontrada:");
                mostrarCredencial(credencial);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("\n✗ Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Lista credenciales que requieren reset de contraseña.
     */
    private static void listarCredencialesQueRequierenReset() {
        System.out.println("\n═══ CREDENCIALES QUE REQUIEREN RESET ═══\n");

        try {
            List<CredencialAcceso> credenciales = credencialService.listarCredencialesQueRequierenReset();

            if (credenciales.isEmpty()) {
                System.out.println("No hay credenciales que requieran reset.");
            } else {
                for (CredencialAcceso credencial : credenciales) {
                    mostrarCredencial(credencial);
                    System.out.println("─".repeat(60));
                }
                System.out.println("\nTotal: " + credenciales.size());
            }
        } catch (SQLException e) {
            System.err.println("\n✗ Error al listar credenciales: " + e.getMessage());
        }
    }

    // ========================================================================
    // Menú de Autenticación
    // ========================================================================

    /**
     * Menú de autenticación / login.
     */
    private static void menuAutenticacion() {
        System.out.println("\n═══ AUTENTICACIÓN / LOGIN ═══\n");

        try {
            String username = leerString("Username: ");
            String password = leerString("Contraseña: ");

            // Buscar el usuario
            Usuario usuario = usuarioService.buscarUsuarioPorUsername(username);

            if (usuario == null) {
                System.out.println("\n✗ Login fallido: Usuario no encontrado");
                return;
            }

            // Verificar que el usuario pueda hacer login
            if (!usuario.puedeHacerLogin()) {
                System.out.println("\n✗ Login fallido: Usuario inactivo o eliminado");
                return;
            }

            CredencialAcceso credencial = usuario.getCredencialAcceso();

            if (credencial == null) {
                System.out.println("\n✗ Login fallido: Usuario sin credenciales");
                return;
            }

            // Verificar si la cuenta está bloqueada
            if (credencialService.debeBloquearPorIntentos(credencial)) {
                System.out.println("\n✗ Login fallido: Cuenta bloqueada por demasiados intentos fallidos");
                System.out.println("Contacte al administrador para desbloquear la cuenta.");
                return;
            }

            // Verificar la contraseña
            boolean passwordCorrecta = credencialService.verificarPassword(credencial, password);

            if (passwordCorrecta) {
                // Login exitoso
                credencialService.registrarLoginExitoso(credencial.getId());

                System.out.println("\n✓ ¡Login exitoso!");
                System.out.println("\nBienvenido, " + usuario.getNombreCompleto());

                if (credencial.isRequiereReset()) {
                    System.out.println("\n⚠️  ATENCIÓN: Debe cambiar su contraseña");
                    boolean cambiar = leerBoolean("¿Desea cambiar la contraseña ahora? (S/N): ");
                    if (cambiar) {
                        String nuevaPassword = leerString("Nueva contraseña: ");
                        credencialService.cambiarPassword(credencial.getId(), nuevaPassword);
                        System.out.println("\n✓ Contraseña cambiada exitosamente");
                    }
                }

            } else {
                // Login fallido
                credencialService.registrarLoginFallido(credencial.getId());
                CredencialAcceso credencialActualizada = credencialService.buscarCredencialPorId(credencial.getId());

                System.out.println("\n✗ Login fallido: Contraseña incorrecta");
                System.out.println("Intentos fallidos: " + credencialActualizada.getIntentosFallidos());

                int intentosRestantes = 5 - credencialActualizada.getIntentosFallidos();
                if (intentosRestantes > 0) {
                    System.out.println("Quedan " + intentosRestantes + " intentos antes del bloqueo.");
                }
            }

        } catch (IllegalArgumentException e) {
            System.err.println("\n✗ Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    // ========================================================================
    // Métodos auxiliares de entrada/salida
    // ========================================================================

    /**
     * Lee un entero desde la consola.
     */
    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Debe ingresar un número entero. Intente nuevamente: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        return valor;
    }

    /**
     * Lee un Long desde la consola.
     */
    private static Long leerLong(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextLong()) {
            System.out.print("Debe ingresar un número. Intente nuevamente: ");
            scanner.next();
        }
        Long valor = scanner.nextLong();
        scanner.nextLine(); // Limpiar el buffer
        return valor;
    }

    /**
     * Lee un String desde la consola.
     */
    private static String leerString(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    /**
     * Lee un String opcional (puede estar vacío).
     */
    private static String leerStringOpcional(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    /**
     * Lee un boolean desde la consola (S/N).
     */
    private static boolean leerBoolean(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String respuesta = scanner.nextLine().trim().toUpperCase();
            if (respuesta.equals("S") || respuesta.equals("SI") || respuesta.equals("SÍ")) {
                return true;
            } else if (respuesta.equals("N") || respuesta.equals("NO")) {
                return false;
            } else {
                System.out.println("Por favor, ingrese S (Sí) o N (No)");
            }
        }
    }

    // ========================================================================
    // Métodos auxiliares de visualización
    // ========================================================================

    /**
     * Muestra información completa de un usuario.
     */
    private static void mostrarUsuario(Usuario usuario) {
        System.out.println("\n┌─ INFORMACIÓN DEL USUARIO ─┐");
        System.out.println("│ ID: " + usuario.getId());
        System.out.println("│ Username: " + usuario.getUsername());
        System.out.println("│ Email: " + usuario.getEmail());
        System.out.println("│ Nombre completo: " + usuario.getNombreCompleto());
        System.out.println("│ Activo: " + (usuario.isActivo() ? "Sí" : "No"));
        System.out.println("│ Eliminado: " + (usuario.isEliminado() ? "Sí" : "No"));
        System.out.println("│ Fecha de registro: " + usuario.getFechaRegistro());
        System.out.println("│ Tiene credencial: " + (usuario.getCredencialAcceso() != null ? "Sí" : "No"));
        System.out.println("└────────────────────────────┘");
    }

    /**
     * Muestra información resumida de un usuario.
     */
    private static void mostrarUsuarioResumido(Usuario usuario) {
        System.out.printf("ID: %-5d | Username: %-20s | Email: %-30s | Activo: %-3s%n",
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.isActivo() ? "Sí" : "No");
    }

    /**
     * Muestra información de una credencial.
     */
    private static void mostrarCredencial(CredencialAcceso credencial) {
        System.out.println("\n┌─ INFORMACIÓN DE LA CREDENCIAL ─┐");
        System.out.println("│ ID: " + credencial.getId());
        System.out.println("│ Último cambio: " + credencial.getUltimoCambio());
        System.out.println("│ Requiere reset: " + (credencial.isRequiereReset() ? "Sí" : "No"));
        System.out.println("│ Intentos fallidos: " + credencial.getIntentosFallidos());
        System.out.println("│ Último login: " + (credencial.getUltimoLogin() != null ? credencial.getUltimoLogin() : "Nunca"));
        System.out.println("│ Eliminado: " + (credencial.isEliminado() ? "Sí" : "No"));
        System.out.println("└─────────────────────────────────┘");
    }

    /**
     * Imprime el encabezado de la aplicación.
     */
    private static void imprimirEncabezado() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║                                                        ║");
        System.out.println("║      SISTEMA DE GESTIÓN DE USUARIOS Y CREDENCIALES    ║");
        System.out.println("║                                                        ║");
        System.out.println("║              TFI - Programación II                     ║");
        System.out.println("║                                                        ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");
    }

    /**
     * Verifica la conexión a la base de datos.
     */
    private static boolean verificarConexionBD() {
        System.out.println("Verificando conexión a la base de datos...");
        return DatabaseConfig.getInstance().testConnection();
    }
}