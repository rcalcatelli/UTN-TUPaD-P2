public class TestEstudiante {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE LA CLASE ESTUDIANTE ===\n");

        // Instanciar un estudiante
        Estudiante estudiante1 = new Estudiante("Renzo", "Calcatelli", "Programación II", 6.5);

        // Mostrar información inicial
        estudiante1.mostrarInfo();

        // Simular cambios en las calificaciones
        System.out.println("\n--- Modificando calificaciones ---");
        estudiante1.subirCalificacion(1.5);  // Debería llegar a 8.0
        estudiante1.mostrarInfo();

        estudiante1.bajarCalificacion(3.0);  // Debería llegar a 5.0
        estudiante1.mostrarInfo();

        // Probar validaciones
        System.out.println("\n--- Probando validaciones ---");
        estudiante1.subirCalificacion(6.0);  // Debería limitarse a 10.0
        estudiante1.mostrarInfo();

        estudiante1.bajarCalificacion(15.0); // Debería limitarse a 0.0
        estudiante1.mostrarInfo();
    }
}