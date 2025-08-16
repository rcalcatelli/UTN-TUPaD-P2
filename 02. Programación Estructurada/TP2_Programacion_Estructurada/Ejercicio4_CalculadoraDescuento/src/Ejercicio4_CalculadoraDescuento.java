import java.util.Scanner;

class Ejercicio4_CalculadoraDescuento {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el precio del producto: ");
        double precio = scanner.nextDouble();
        System.out.print("Ingrese la categoría del producto (A, B o C): ");
        char categoria = scanner.next().toUpperCase().charAt(0);

        double porcentajeDescuento = 0;

        // Switch es ideal para opciones específicas
        switch (categoria) {
            case 'A':
                porcentajeDescuento = 0.10; // 10%
                break;
            case 'B':
                porcentajeDescuento = 0.15; // 15%
                break;
            case 'C':
                porcentajeDescuento = 0.20; // 20%
                break;
            default:
                System.out.println("Categoría inválida");
                scanner.close();
                return; // Terminar el programa si la categoría es inválida
        }

        double descuentoAplicado = precio * porcentajeDescuento;
        double precioFinal = precio - descuentoAplicado;

        System.out.println("Descuento aplicado: " + (int)(porcentajeDescuento * 100) + "%");
        System.out.println("Precio final: " + precioFinal);

        scanner.close();
    }
}