import java.util.Scanner;

public class Ejercicio5_SumaNumerosPares {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numero;
        int sumaPares = 0;

        // El ciclo while es perfecto cuando no sabemos cuántas iteraciones necesitamos
        do {
            System.out.print("Ingrese un número (0 para terminar): ");
            numero = scanner.nextInt();

            // Solo sumar si es par y no es cero (el cero técnicamente es par pero es nuestra señal de parada)
            if (numero != 0 && numero % 2 == 0) {
                sumaPares += numero;
            }
        } while (numero != 0);

        System.out.println("La suma de los números pares es: " + sumaPares);

        scanner.close();
    }
}