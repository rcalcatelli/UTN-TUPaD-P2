public class TestLibro {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE LA CLASE LIBRO ===\n");

        // Crear un libro
        Libro libro = new Libro("Don Quijote", "Miguel de Cervantes", 1605);

        // Mostrar información inicial
        libro.mostrarInfo();

        System.out.println("\n--- Probando validaciones ---");

        // Intentar modificar con año inválido (futuro)
        libro.setAnioPublicacion(2030);

        // Intentar modificar con año inválido (negativo)
        libro.setAnioPublicacion(-500);

        // Modificar con año válido
        libro.setAnioPublicacion(1615);

        System.out.println("\n--- Estado final ---");
        libro.mostrarInfo();
    }
}