public class Ejercicio13_ArrayRecursivo {

    public static void main(String[] args) {
        // Mismo array del ejercicio anterior
        double[] precios = {199.99, 299.5, 149.75, 399.0, 89.99};

        System.out.println("Precios originales:");
        mostrarPreciosRecursivo(precios, 0);

        // Modificar el precio del tercer producto
        precios[2] = 129.99;

        System.out.println("\nPrecios modificados:");
        mostrarPreciosRecursivo(precios, 0);
    }

    // Función recursiva para mostrar precios
    public static void mostrarPreciosRecursivo(double[] precios, int indice) {
        // Caso base: si el índice llegó al final del array, terminar
        if (indice >= precios.length) {
            return;
        }

        // Mostrar el precio actual
        System.out.println("Precio: $" + precios[indice]);

        // Llamada recursiva con el siguiente índice
        mostrarPreciosRecursivo(precios, indice + 1);
    }
}