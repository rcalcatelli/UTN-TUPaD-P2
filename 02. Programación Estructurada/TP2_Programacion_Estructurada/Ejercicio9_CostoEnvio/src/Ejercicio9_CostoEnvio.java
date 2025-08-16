import java.util.Scanner;

public class Ejercicio9_CostoEnvio {

    // Función para calcular el costo de envío según zona y peso
    public static double calcularCostoEnvio(double peso, String zona) {
        double costoPorKg;

        // Determinar el costo según la zona
        if (zona.equalsIgnoreCase("Nacional")) {
            costoPorKg = 5.0;
        } else if (zona.equalsIgnoreCase("Internacional")) {
            costoPorKg = 10.0;
        } else {
            System.out.println("Zona inválida, usando tarifa nacional.");
            costoPorKg = 5.0;
        }

        return peso * costoPorKg;
    }

    // Función que usa la anterior para calcular el total
    public static double calcularTotalCompra(double precioProducto, double peso, String zona) {
        double costoEnvio = calcularCostoEnvio(peso, zona);
        return precioProducto + costoEnvio;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el precio del producto: ");
        double precioProducto = scanner.nextDouble();

        System.out.print("Ingrese el peso del paquete en kg: ");
        double peso = scanner.nextDouble();

        System.out.print("Ingrese la zona de envío (Nacional/Internacional): ");
        String zona = scanner.next();

        // Mostrar el costo de envío por separado
        double costoEnvio = calcularCostoEnvio(peso, zona);
        System.out.println("El costo de envío es: " + costoEnvio);

        // Calcular y mostrar el total
        double total = calcularTotalCompra(precioProducto, peso, zona);
        System.out.println("El total a pagar es: " + total);

        scanner.close();
    }
}