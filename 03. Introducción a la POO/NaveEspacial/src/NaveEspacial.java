public class NaveEspacial {
    private String nombre;
    private double combustible;
    private static final double COMBUSTIBLE_MAXIMO = 100.0;
    private static final double CONSUMO_POR_KM = 0.5;
    private double distanciaRecorrida;

    // Constructor
    public NaveEspacial(String nombre, double combustibleInicial) {
        this.nombre = nombre;
        this.distanciaRecorrida = 0.0;
        recargarCombustible(combustibleInicial);
    }

    // Método para despegar
    public boolean despegar() {
        if (combustible < 10.0) {
            System.out.println("ERROR: Combustible insuficiente para despegar. Se necesitan al menos 10 unidades");
            return false;
        }

        combustible -= 10.0;
        System.out.println("¡" + nombre + " ha despegado exitosamente!");
        System.out.printf("Combustible restante: %.2f unidades%n", combustible);
        return true;
    }

    // Método para avanzar
    public boolean avanzar(double distancia) {
        if (distancia <= 0) {
            System.out.println("ERROR: La distancia debe ser positiva");
            return false;
        }

        double combustibleNecesario = distancia * CONSUMO_POR_KM;

        if (combustible < combustibleNecesario) {
            System.out.printf("ERROR: Combustible insuficiente para avanzar %.2f km%n", distancia);
            System.out.printf("Se necesitan %.2f unidades, pero solo hay %.2f%n",
                    combustibleNecesario, combustible);
            return false;
        }

        // Realizar el viaje
        combustible -= combustibleNecesario;
        distanciaRecorrida += distancia;

        System.out.printf("%s avanzó %.2f km%n", nombre, distancia);
        System.out.printf("Combustible consumido: %.2f unidades%n", combustibleNecesario);
        System.out.printf("Combustible restante: %.2f unidades%n", combustible);

        return true;
    }

    // Método para recargar combustible
    public void recargarCombustible(double cantidad) {
        if (cantidad <= 0) {
            System.out.println("ERROR: La cantidad de combustible debe ser positiva");
            return;
        }

        double combustibleAnterior = combustible;
        combustible += cantidad;

        if (combustible > COMBUSTIBLE_MAXIMO) {
            combustible = COMBUSTIBLE_MAXIMO;
            double exceso = (combustibleAnterior + cantidad) - COMBUSTIBLE_MAXIMO;
            System.out.printf("ADVERTENCIA: Tanque lleno. Se perdieron %.2f unidades por exceso%n", exceso);
        }

        double recargado = combustible - combustibleAnterior;
        System.out.printf("Combustible recargado: %.2f unidades%n", recargado);
        System.out.printf("Combustible total: %.2f/%.2f unidades%n", combustible, COMBUSTIBLE_MAXIMO);
    }

    // Método para mostrar estado
    public void mostrarEstado() {
        System.out.println("=== ESTADO DE LA NAVE " + nombre.toUpperCase() + " ===");
        System.out.printf("Combustible: %.2f/%.2f unidades (%.1f%%)%n",
                combustible, COMBUSTIBLE_MAXIMO, (combustible/COMBUSTIBLE_MAXIMO)*100);
        System.out.printf("Distancia recorrida: %.2f km%n", distanciaRecorrida);
        System.out.println("Estado operativo: " + determinarEstadoOperativo());
        System.out.println("Autonomía restante: " + calcularAutonomia() + " km");
        System.out.println("=======================================");
    }

    // Método privado para determinar estado operativo
    private String determinarEstadoOperativo() {
        if (combustible >= 50) return "Excelente";
        else if (combustible >= 25) return "Bueno";
        else if (combustible >= 10) return "Precaución";
        else return "Crítico";
    }

    // Método privado para calcular autonomía
    private double calcularAutonomia() {
        return combustible / CONSUMO_POR_KM;
    }

    // Getters
    public String getNombre() { return nombre; }
    public double getCombustible() { return combustible; }
    public double getDistanciaRecorrida() { return distanciaRecorrida; }
}