package ar.edu.utn.tup.main;

import ar.edu.utn.tup.config.DatabaseConfig;
import ar.edu.utn.tup.entities.CredencialAcceso;
import ar.edu.utn.tup.entities.Usuario;
import ar.edu.utn.tup.service.UsuarioService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal con menú de consola para gestión de usuarios.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UsuarioService usuarioService = new UsuarioService();

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("   SISTEMA DE GESTIÓN DE USUARIOS Y CREDENCIALES");
        System.out.println("═══════════════════════════════════════════════════");

        // Verificar conexión a BD
        if (!DatabaseConfig.getInstance().testConnection()) {
            System.err.println("\n✗ No se pudo conectar a la base de datos.");
            System.err.println("Verificá que MySQL esté corriendo y la configuración sea correcta.");
            return;
        }

        System.out.println("\n✓ Conexión a base de datos exitosa\n");

        // Mostrar menú principal
        mostrarMenuPrincipal();

        scanner.close();
        System.out.println("\n¡Hasta luego!");
    }

    private static void mostrarMenuPrincipal() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n═══════════════════════════════════════════════════");
            System.out.println("                    MENÚ PRINCIPAL");
            System.out.println("═══════════════════════════════════════════════════");
            System.out.println("1. Crear Usuario con Credencial");
            System.out.println("2. Buscar Usuario por ID");
            System.out.println("3. Buscar Usuario por Username");
            System.out.println("4. Listar Todos los Usuarios");
            System.out.println("5. Actualizar Usuario");
            System.out.println("6. Activar/Desactivar Usuario");
            System.out.println("7. Eliminar Usuario (Baja Lógica)");
            System.out.println("8. Salir");
            System.out.println("═══════════════════════════════════════════════════");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1 -> crearUsuario();
                    case 2 -> buscarUsuarioPorId();
                    case 3 -> buscarUsuarioPorUsername();
                    case 4 -> listarUsuarios();
                    case 5 -> actualizarUsuario();
                    case 6 -> cambiarEstadoUsuario();
                    case 7 -> eliminarUsuario();
                    case 8 -> {
                        continuar = false;
                        System.out.println("\nCerrando sistema...");
                    }
                    default -> System.out.println("\n✗ Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n✗ Por favor ingrese un número válido.");
            }
        }
    }

    // ========================================================================
    // OPCIÓN 1: CREAR USUARIO CON CREDENCIAL
    // ========================================================================

    private static void crearUsuario() {
        System.out.println("\n═══ CREAR NUEVO USUARIO ═══\n");

        try {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Nombre completo: ");
            String nombreCompleto = scanner.nextLine();

            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            // Crear usuario
            Usuario usuario = new Usuario(username, email, nombreCompleto);

            // Usar método transaccional
            Usuario usuarioCreado = usuarioService.crearUsuarioConCredencial(usuario, password);

            System.out.println("\n✓ Usuario y credencial creados exitosamente");
            System.out.println("  ID: " + usuarioCreado.getId());
            System.out.println("  Username: " + usuarioCreado.getUsername());
            System.out.println("  Email: " + usuarioCreado.getEmail());
            System.out.println("  Credencial ID: " + usuarioCreado.getCredencialAcceso().getId());

        } catch (IllegalArgumentException e) {
            System.err.println("\n✗ Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    // ========================================================================
    // OPCIÓN 2: BUSCAR USUARIO POR ID
    // ========================================================================

    private static void buscarUsuarioPorId() {
        System.out.println("\n═══ BUSCAR USUARIO POR ID ═══\n");

        try {
            System.out.print("Ingrese ID del usuario: ");
            Long id = Long.parseLong(scanner.nextLine());

            Usuario usuario = usuarioService.buscarConCredencial(id);

            if (usuario != null) {
                mostrarUsuario(usuario);
            } else {
                System.out.println("\n✗ No se encontró usuario con ID: " + id);
            }

        } catch (NumberFormatException e) {
            System.err.println("\n✗ ID inválido. Debe ser un número.");
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    // ========================================================================
    // OPCIÓN 3: BUSCAR USUARIO POR USERNAME
    // ========================================================================

    private static void buscarUsuarioPorUsername() {
        System.out.println("\n═══ BUSCAR USUARIO POR USERNAME ═══\n");

        try {
            System.out.print("Ingrese username: ");
            String username = scanner.nextLine();

            Usuario usuario = usuarioService.buscarUsuarioPorUsername(username);

            if (usuario != null) {
                mostrarUsuario(usuario);
            } else {
                System.out.println("\n✗ No se encontró usuario con username: " + username);
            }

        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    // ========================================================================
    // OPCIÓN 4: LISTAR TODOS LOS USUARIOS
    // ========================================================================

    private static void listarUsuarios() {
        System.out.println("\n═══ LISTA DE USUARIOS ═══\n");

        try {
            List<Usuario> usuarios = usuarioService.listarTodosLosUsuarios();

            if (usuarios.isEmpty()) {
                System.out.println("No hay usuarios registrados.");
            } else {
                System.out.println("Total de usuarios: " + usuarios.size() + "\n");

                for (Usuario usuario : usuarios) {
                    System.out.println("─────────────────────────────────────");
                    System.out.println("ID: " + usuario.getId());
                    System.out.println("Username: " + usuario.getUsername());
                    System.out.println("Email: " + usuario.getEmail());
                    System.out.println("Nombre: " + usuario.getNombreCompleto());
                    System.out.println("Activo: " + (usuario.isActivo() ? "Sí" : "No"));
                    System.out.println("Eliminado: " + (usuario.isEliminado() ? "Sí" : "No"));
                }
                System.out.println("─────────────────────────────────────");
            }

        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    // ========================================================================
    // OPCIÓN 5: ACTUALIZAR USUARIO
    // ========================================================================

    private static void actualizarUsuario() {
        System.out.println("\n═══ ACTUALIZAR USUARIO ═══\n");

        try {
            System.out.print("Ingrese ID del usuario a actualizar: ");
            Long id = Long.parseLong(scanner.nextLine());

            Usuario usuario = usuarioService.buscarUsuarioPorId(id);

            if (usuario == null) {
                System.out.println("\n✗ No se encontró usuario con ID: " + id);
                return;
            }

            System.out.println("\nUsuario actual:");
            mostrarUsuario(usuario);

            System.out.println("\nIngrese nuevos datos (Enter para mantener actual):");

            System.out.print("Nuevo email [" + usuario.getEmail() + "]: ");
            String nuevoEmail = scanner.nextLine();
            if (!nuevoEmail.trim().isEmpty()) {
                usuario.setEmail(nuevoEmail);
            }

            System.out.print("Nuevo nombre completo [" + usuario.getNombreCompleto() + "]: ");
            String nuevoNombre = scanner.nextLine();
            if (!nuevoNombre.trim().isEmpty()) {
                usuario.setNombreCompleto(nuevoNombre);
            }

            usuarioService.actualizarUsuario(usuario);

            System.out.println("\n✓ Usuario actualizado exitosamente");
            mostrarUsuario(usuario);

        } catch (NumberFormatException e) {
            System.err.println("\n✗ ID inválido.");
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    // ========================================================================
    // OPCIÓN 6: ACTIVAR/DESACTIVAR USUARIO
    // ========================================================================

    private static void cambiarEstadoUsuario() {
        System.out.println("\n═══ ACTIVAR/DESACTIVAR USUARIO ═══\n");

        try {
            System.out.print("Ingrese ID del usuario: ");
            Long id = Long.parseLong(scanner.nextLine());

            Usuario usuario = usuarioService.buscarUsuarioPorId(id);

            if (usuario == null) {
                System.out.println("\n✗ No se encontró usuario con ID: " + id);
                return;
            }

            System.out.println("\nEstado actual: " + (usuario.isActivo() ? "ACTIVO" : "INACTIVO"));
            System.out.print("¿Desea " + (usuario.isActivo() ? "DESACTIVAR" : "ACTIVAR") + "? (S/N): ");
            String respuesta = scanner.nextLine();

            if (respuesta.equalsIgnoreCase("S")) {
                if (usuario.isActivo()) {
                    usuarioService.desactivarUsuario(id);
                    System.out.println("\n✓ Usuario DESACTIVADO exitosamente");
                } else {
                    usuarioService.activarUsuario(id);
                    System.out.println("\n✓ Usuario ACTIVADO exitosamente");
                }
            } else {
                System.out.println("\nOperación cancelada.");
            }

        } catch (NumberFormatException e) {
            System.err.println("\n✗ ID inválido.");
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    // ========================================================================
    // OPCIÓN 7: ELIMINAR USUARIO (BAJA LÓGICA)
    // ========================================================================

    private static void eliminarUsuario() {
        System.out.println("\n═══ ELIMINAR USUARIO (BAJA LÓGICA) ═══\n");

        try {
            System.out.print("Ingrese ID del usuario a eliminar: ");
            Long id = Long.parseLong(scanner.nextLine());

            Usuario usuario = usuarioService.buscarUsuarioPorId(id);

            if (usuario == null) {
                System.out.println("\n✗ No se encontró usuario con ID: " + id);
                return;
            }

            System.out.println("\nUsuario a eliminar:");
            mostrarUsuario(usuario);

            System.out.print("\n¿Está seguro que desea eliminar este usuario? (S/N): ");
            String respuesta = scanner.nextLine();

            if (respuesta.equalsIgnoreCase("S")) {
                boolean eliminado = usuarioService.eliminarUsuario(id);

                if (eliminado) {
                    System.out.println("\n✓ Usuario eliminado lógicamente");
                    System.out.println("  El usuario ya no aparecerá en listados");
                    System.out.println("  Los datos se conservan para auditoría");
                } else {
                    System.out.println("\n✗ No se pudo eliminar el usuario");
                }
            } else {
                System.out.println("\nEliminación cancelada.");
            }

        } catch (NumberFormatException e) {
            System.err.println("\n✗ ID inválido.");
        } catch (SQLException e) {
            System.err.println("\n✗ Error de base de datos: " + e.getMessage());
        }
    }

    // ========================================================================
    // MÉTODO AUXILIAR: MOSTRAR USUARIO
    // ========================================================================

    private static void mostrarUsuario(Usuario usuario) {
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("INFORMACIÓN DEL USUARIO");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("ID: " + usuario.getId());
        System.out.println("Username: " + usuario.getUsername());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Nombre Completo: " + usuario.getNombreCompleto());
        System.out.println("Activo: " + (usuario.isActivo() ? "Sí" : "No"));
        System.out.println("Eliminado: " + (usuario.isEliminado() ? "Sí" : "No"));
        System.out.println("Fecha Registro: " + usuario.getFechaRegistro());

        if (usuario.getCredencialAcceso() != null) {
            CredencialAcceso cred = usuario.getCredencialAcceso();
            System.out.println("\n--- CREDENCIAL DE ACCESO ---");
            System.out.println("ID Credencial: " + cred.getId());
            System.out.println("Último cambio: " + cred.getUltimoCambio());
            System.out.println("Requiere reset: " + (cred.isRequiereReset() ? "Sí" : "No"));
            System.out.println("Intentos fallidos: " + cred.getIntentosFallidos());
            System.out.println("Último login: " + cred.getUltimoLogin());
        }
        System.out.println("═══════════════════════════════════════════════════");
    }
}