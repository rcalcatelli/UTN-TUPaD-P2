public class TestGallina {
    public static void main(String[] args) {
        System.out.println("=== SIMULACIÓN DE GRANJA DIGITAL ===\n");

        // Crear dos gallinas
        Gallina gallina1 = new Gallina(0); // Pollita
        Gallina gallina2 = new Gallina(2); // Gallina adulta

        // Mostrar estado inicial
        System.out.println("--- Estado inicial ---");
        gallina1.mostrarEstado();
        gallina2.mostrarEstado();

        // Simular el paso del tiempo
        System.out.println("\n--- Simulando 3 años de vida ---");

        for (int año = 1; año <= 3; año++) {
            System.out.println("\n=== AÑO " + año + " ===");

            // Envejecer las gallinas
            gallina1.envejecer();
            gallina2.envejecer();

            // Simular puesta de huevos durante el año
            for (int mes = 1; mes <= 12; mes++) {
                // Cada gallina intenta poner 2-3 huevos por mes
                for (int intento = 1; intento <= 3; intento++) {
                    if (Math.random() > 0.3) { // 70% de probabilidad
                        gallina1.ponerHuevo();
                    }
                    if (Math.random() > 0.3) {
                        gallina2.ponerHuevo();
                    }
                }
            }

            // Mostrar estado anual
            gallina1.mostrarEstado();
            gallina2.mostrarEstado();
        }
    }
}
