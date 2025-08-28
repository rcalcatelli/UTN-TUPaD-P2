public class TestNaveEspacial {
    public static void main(String[] args) {
        System.out.println("=== SIMULACIÓN DE NAVE ESPACIAL ===\n");

        // Crear una nave con 50 unidades de combustible
        NaveEspacial nave = new NaveEspacial("Enterprise", 50.0);

        // Mostrar estado inicial
        System.out.println("--- Estado inicial ---");
        nave.mostrarEstado();

        // Intentar despegar
        System.out.println("\n--- Intento de despegue ---");
        if (nave.despegar()) {
            nave.mostrarEstado();
        }

        // Intentar avanzar sin recargar
        System.out.println("\n--- Intento de viaje largo sin recargar ---");
        nave.avanzar(100); // Necesita 50 unidades, pero solo tiene ~40

        // Intentar avanzar distancia menor
        System.out.println("\n--- Viaje corto ---");
        nave.avanzar(20); // Debería funcionar
        nave.mostrarEstado();

        // Recargar combustible
        System.out.println("\n--- Recarga de combustible ---");
        nave.recargarCombustible(60); // Debería limitarse a 100 total
        nave.mostrarEstado();

        // Ahora intentar el viaje largo
        System.out.println("\n--- Viaje largo después de recargar ---");
        nave.avanzar(100);
        nave.mostrarEstado();

        // Simular varios viajes
        System.out.println("\n--- Simulación de múltiples viajes ---");
        double[] distancias = {30, 25, 40, 20};

        for (double distancia : distancias) {
            System.out.println("\n--- Viaje de " + distancia + " km ---");
            if (!nave.avanzar(distancia)) {
                System.out.println("Recargando combustible para continuar...");
                nave.recargarCombustible(50);
                nave.avanzar(distancia);
            }
        }

        // Estado final
        System.out.println("\n--- ESTADO FINAL ---");
        nave.mostrarEstado();
    }
}