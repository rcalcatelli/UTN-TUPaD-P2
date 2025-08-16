public class Ejercicio12_ArrayPrecios {

    public static void main(String[] args) {
        // Declarar e inicializar array con precios
        double[] precios = {199.99, 299.5, 149.75, 399.0, 89.99};

        System.out.println("Precios originales:");
        mostrarPrecios(precios);

        // Modificar el precio del tercer producto (índice 2)
        precios[2] = 129.99;

        System.out.println("\nPrecios modificados:");
        mostrarPrecios(precios);
    }

    // Función para mostrar todos los precios usando for-each
    public static void mostrarPrecios(double[] precios) {
        for (double precio : precios) {
            System.out.println("Precio: $" + precio);
        }
    }
}