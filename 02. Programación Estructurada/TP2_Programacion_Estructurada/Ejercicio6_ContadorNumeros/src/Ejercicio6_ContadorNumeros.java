import java.util.Scanner;

public class Ejercicio6_ContadorNumeros {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int positivos = 0;
        int negativos = 0;
        int ceros = 0;

        // For es perfecto cuando sabemos exactamente cuántas veces iterar
        for (int i = 1; i <= 10; i++) {
            System.out.print("Ingrese el número " + i + ": ");
            int numero = scanner.nextInt();

            // Clasificar el número según su signo
            if (numero > 0) {
                positivos++;
            } else if (numero < 0) {
                negativos++;
            } else { // numero == 0
                ceros++;
            }
        }

        System.out.println("Resultados:");
        System.out.println("Positivos: " + positivos);
        System.out.println("Negativos: " + negativos);
        System.out.println("Ceros: " + ceros);

        scanner.close();
    }
}