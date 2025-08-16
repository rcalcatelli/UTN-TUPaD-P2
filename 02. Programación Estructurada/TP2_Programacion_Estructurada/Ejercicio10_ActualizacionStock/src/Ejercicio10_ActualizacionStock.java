import java.util.Scanner;

public class Ejercicio10_ActualizacionStock {

    // Función para actualizar el stock según ventas y recepciones
    public static int actualizarStock(int stockActual, int cantidadVendida, int cantidadRecibida) {
        // Fórmula: Stock actual - cantidad vendida + cantidad recibida
        int nuevoStock = stockActual - cantidadVendida + cantidadRecibida;
        return nuevoStock;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el stock actual del producto: ");
        int stockActual = scanner.nextInt();

        System.out.print("Ingrese la cantidad vendida: ");
        int cantidadVendida = scanner.nextInt();

        System.out.print("Ingrese la cantidad recibida: ");
        int cantidadRecibida = scanner.nextInt();

        // Calcular el nuevo stock
        int stockNuevo = actualizarStock(stockActual, cantidadVendida, cantidadRecibida);

        System.out.println("El nuevo stock del producto es: " + stockNuevo);

        scanner.close();
    }
}