import java.util.Scanner;

public class Ejercicio3_ClasificacionEdad {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese su edad: ");
        int edad = scanner.nextInt();

        String clasificacion;

        // Uso if-else encadenado para rangos de edad
        if (edad < 12) {
            clasificacion = "Niño";
        } else if (edad >= 12 && edad <= 17) {
            clasificacion = "Adolescente";
        } else if (edad >= 18 && edad <= 59) {
            clasificacion = "Adulto";
        } else { // 60 años o más
            clasificacion = "Adulto mayor";
        }

        System.out.println("Eres un " + clasificacion + ".");

        scanner.close();
    }
}