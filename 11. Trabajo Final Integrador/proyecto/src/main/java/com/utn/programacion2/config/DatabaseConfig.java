package ar.edu.utn.frbb.tup.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de configuración para gestionar las conexiones a la base de datos MySQL.
 *
 * Esta clase implementa el patrón Singleton para asegurar que solo exista
 * una instancia de la configuración en toda la aplicación. Esto es importante
 * porque queremos tener un control centralizado de cómo nos conectamos a la
 * base de datos.
 *
 * RESPONSABILIDADES:
 * 1. Cargar el driver JDBC de MySQL
 * 2. Proporcionar conexiones a la base de datos
 * 3. Gestionar los parámetros de conexión (URL, usuario, contraseña)
 *
 * PATRÓN SINGLETON:
 * El patrón Singleton garantiza que:
 * - Solo existe una instancia de esta clase
 * - Hay un punto de acceso global a esa instancia
 * - La instancia se crea de forma lazy (solo cuando se necesita)
 *
 * ¿Por qué usar Singleton para la configuración de BD?
 * - Evita crear múltiples instancias con configuraciones potencialmente diferentes
 * - Centraliza la configuración en un solo lugar
 * - Facilita el cambio de configuración (solo hay que modificar un lugar)
 *
 * IMPORTANTE SOBRE CONNECTION POOLING:
 * En aplicaciones de producción reales, NO crearías conexiones directamente
 * en cada método. En su lugar, usarías un Connection Pool (como HikariCP o
 * C3P0) que reutiliza conexiones y mejora dramáticamente el rendimiento.
 *
 * Sin embargo, para este TFI y con fines educativos, usamos conexiones
 * directas para que entiendas los conceptos fundamentales antes de pasar
 * a herramientas más avanzadas.
 */
public class DatabaseConfig {

    // ========================================================================
    // Atributos de configuración de la base de datos
    // ========================================================================

    /**
     * URL de conexión a la base de datos MySQL.
     *
     * Formato: jdbc:mysql://[host]:[puerto]/[nombre_bd]?[parámetros]
     *
     * Componentes:
     * - jdbc:mysql:// → Protocolo JDBC para MySQL
     * - localhost → Servidor donde corre MySQL (en este caso, local)
     * - 3306 → Puerto por defecto de MySQL
     * - sistema_usuarios → Nombre de nuestra base de datos
     *
     * Parámetros adicionales:
     * - useSSL=false → Desactiva SSL (solo para desarrollo local)
     * - serverTimezone=UTC → Establece la zona horaria del servidor
     * - allowPublicKeyRetrieval=true → Permite autenticación en MySQL 8+
     */
    private static final String URL = "jdbc:mysql://localhost:3308/sistema_usuarios?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    /**
     * Usuario de MySQL con permisos sobre la base de datos.
     * En producción, NUNCA deberías hardcodear esto en el código.
     * Usarías variables de entorno, archivos de configuración externos,
     * o un gestor de secretos.
     */
    private static final String USER = "root";

    /**
     * Contraseña del usuario de MySQL.
     * ADVERTENCIA DE SEGURIDAD: En un sistema real, esto vendría de
     * una variable de entorno o un archivo de configuración seguro,
     * NUNCA hardcodeado en el código fuente.
     */
    private static final String PASSWORD = "123123qwer";

    /**
     * Nombre del driver JDBC de MySQL.
     * El driver es la librería que permite a Java comunicarse con MySQL.
     * Debés incluir la dependencia mysql-connector-java en tu proyecto.
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // ========================================================================
    // Implementación del patrón Singleton
    // ========================================================================

    /**
     * Única instancia de la clase (patrón Singleton).
     * Es volatile para garantizar que múltiples threads vean el mismo valor.
     */
    private static volatile DatabaseConfig instance;

    /**
     * Constructor privado para prevenir instanciación externa.
     *
     * Al hacer el constructor privado, nadie fuera de esta clase puede
     * crear instancias con "new DatabaseConfig()". La única forma de obtener
     * una instancia es a través del método getInstance().
     *
     * El constructor carga el driver JDBC de MySQL.
     */
    private DatabaseConfig() {
        try {
            // Cargar el driver JDBC de MySQL
            // Esto registra el driver en el DriverManager de JDBC
            Class.forName(DRIVER);
            System.out.println("✓ Driver JDBC de MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            // Si llegamos acá, significa que no encontró el JAR de MySQL
            System.err.println("✗ Error: No se pudo cargar el driver JDBC de MySQL");
            System.err.println("  Asegurate de tener mysql-connector-java en el classpath");
            throw new RuntimeException("Driver JDBC no encontrado", e);
        }
    }

    /**
     * Obtiene la única instancia de DatabaseConfig (patrón Singleton).
     *
     * Usa double-checked locking para garantizar thread-safety sin sacrificar
     * mucho rendimiento. La primera verificación (sin lock) es por rendimiento,
     * la segunda (con lock) es por seguridad.
     *
     * @return La única instancia de DatabaseConfig
     */
    public static DatabaseConfig getInstance() {
        // Primera verificación sin sincronización (por rendimiento)
        if (instance == null) {
            // Sincronizamos solo cuando instance es null
            synchronized (DatabaseConfig.class) {
                // Segunda verificación con sincronización (por seguridad)
                if (instance == null) {
                    instance = new DatabaseConfig();
                }
            }
        }
        return instance;
    }

    // ========================================================================
    // Métodos para obtener conexiones
    // ========================================================================

    /**
     * Obtiene una nueva conexión a la base de datos.
     *
     * IMPORTANTE: El código que llama a este método es RESPONSABLE de cerrar
     * la conexión cuando termine de usarla. Si no cerrás las conexiones,
     * vas a tener un "connection leak" y eventualmente te vas a quedar sin
     * conexiones disponibles.
     *
     * Patrón recomendado de uso:
     *
     * Connection conn = null;
     * try {
     *     conn = DatabaseConfig.getInstance().getConnection();
     *     // usar la conexión
     * } catch (SQLException e) {
     *     // manejar el error
     * } finally {
     *     if (conn != null) {
     *         try {
     *             conn.close();
     *         } catch (SQLException e) {
     *             // log del error
     *         }
     *     }
     * }
     *
     * O mejor aún, usando try-with-resources (Java 7+):
     *
     * try (Connection conn = DatabaseConfig.getInstance().getConnection()) {
     *     // usar la conexión
     * } catch (SQLException e) {
     *     // manejar el error
     * }
     * // La conexión se cierra automáticamente al salir del try
     *
     * @return Una nueva conexión a la base de datos
     * @throws SQLException Si no se puede establecer la conexión
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Verifica si se puede establecer una conexión a la base de datos.
     *
     * Este método es útil al iniciar la aplicación para verificar que
     * la configuración es correcta y que MySQL está accesible.
     *
     * @return true si se puede conectar, false en caso contrario
     */
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("✓ Conexión a la base de datos establecida correctamente");
            System.out.println("  Base de datos: " + conn.getCatalog());
            System.out.println("  Usuario: " + conn.getMetaData().getUserName());
            return true;
        } catch (SQLException e) {
            System.err.println("✗ Error al conectar a la base de datos:");
            System.err.println("  " + e.getMessage());
            return false;
        }
    }

    // ========================================================================
    // Getters para acceder a los parámetros de configuración
    // ========================================================================

    /**
     * @return La URL de conexión a la base de datos
     */
    public String getUrl() {
        return URL;
    }

    /**
     * @return El usuario de la base de datos
     */
    public String getUser() {
        return USER;
    }

    /**
     * Nota: No proporcionamos un getter para PASSWORD por seguridad.
     * La contraseña solo debe usarse internamente para crear conexiones.
     */
}
