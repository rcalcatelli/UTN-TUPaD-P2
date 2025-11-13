package ar.edu.utn.tup.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    // Configuración de BD - AJUSTAR con tus datos
    private static final String URL = "jdbc:mysql://localhost:3308/sistema_usuarios?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "123123qwer";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Singleton
    private static volatile DatabaseConfig instance;

    private DatabaseConfig() {
        try {
            Class.forName(DRIVER);
            System.out.println("✓ Driver JDBC cargado");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Driver JDBC no encontrado");
            throw new RuntimeException("Driver JDBC no encontrado", e);
        }
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            synchronized (DatabaseConfig.class) {
                if (instance == null) {
                    instance = new DatabaseConfig();
                }
            }
        }
        return instance;
    }

    // Método de instancia
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método estático para compatibilidad
    public static Connection getConnection() throws SQLException {
        return getInstance().createConnection();
    }

    public boolean testConnection() {
        try (Connection conn = createConnection()) {
            System.out.println("✓ Conexión a BD exitosa");
            System.out.println("  Base de datos: " + conn.getCatalog());
            return true;
        } catch (SQLException e) {
            System.err.println("✗ Error al conectar: " + e.getMessage());
            return false;
        }
    }
}