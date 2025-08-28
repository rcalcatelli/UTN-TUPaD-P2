public class TestMascota {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE LA CLASE MASCOTA ===\n");

        // Crear una mascota
        Mascota miPerro = new Mascota("Angus", "Perro", 3);

        // Mostrar información inicial
        miPerro.mostrarInfo();

        // Simular el paso del tiempo
        System.out.println("\n--- Simulando el paso del tiempo ---");
        for (int i = 0; i < 5; i++) {
            miPerro.cumplirAnios();
        }

        System.out.println("\n--- Estado final ---");
        miPerro.mostrarInfo();
    }
}