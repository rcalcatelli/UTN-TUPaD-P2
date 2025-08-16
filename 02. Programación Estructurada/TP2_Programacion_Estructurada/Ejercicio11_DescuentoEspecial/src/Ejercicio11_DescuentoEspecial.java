import java.util.Scanner;

public class Ejercicio11_DescuentoEspecial {

    // Variable global - accesible desde cualquier método de la clase
    public static final double DESCUENTO_ESPECIAL = 0.10; // 10%

    // Función que usa la variable global
    public static void calcularDescuentoEspecial(double precio) {
        // Variable local - solo existe dentro de esta función
        double descuentoAplicado = precio * DESCUENTO_ESPECIAL;
        double precioFinal = precio - descuentoAplicado;

        System.out.println("El descuento especial aplicado es: " + descuentoAplicado);
        System.out.println("El precio final con descuento es: " + precioFinal);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el precio del producto: ");
        double precio = scanner.nextDouble();

        // Llamar a la función que usa la variable global
        calcularDescuentoEspecial(precio);

        scanner.close();
    }
}