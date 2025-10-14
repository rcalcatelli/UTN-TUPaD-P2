import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * EjerciciosExcepciones
 * Clase que contiene todos los ejercicios sobre manejo de excepciones
 * Cada método demuestra un tipo diferente de excepción y su manejo
 */
public class EjerciciosExcepciones {

    /**
     * Ejercicio 1: División segura
     * Maneja ArithmeticException cuando se intenta dividir por cero
     */
    public static void divisionSegura() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("\n=== EJERCICIO 1: División Segura ===");
            System.out.print("Ingrese el dividendo: ");
            int dividendo = scanner.nextInt();

            System.out.print("Ingrese el divisor: ");
            int divisor = scanner.nextInt();

            // Esta operación puede lanzar ArithmeticException si divisor es 0
            int resultado = dividendo / divisor;

            System.out.println("Resultado: " + dividendo + " / " + divisor + " = " + resultado);

        } catch (ArithmeticException e) {
            // Capturamos la excepción de división por cero
            System.err.println("❌ ERROR: No se puede dividir por cero");
            System.err.println("Detalles técnicos: " + e.getMessage());

        } catch (Exception e) {
            // Captura cualquier otra excepción (ej: InputMismatchException)
            System.err.println("❌ ERROR: Entrada inválida. Debe ingresar números enteros");
            scanner.nextLine(); // Limpiar el buffer

        } finally {
            // Este bloque SIEMPRE se ejecuta, haya o no excepción
            System.out.println("Operación de división finalizada");
        }
    }

    /**
     * Ejercicio 2: Conversión de cadena a número
     * Maneja NumberFormatException cuando el texto no es un número válido
     */
    public static void conversionCadenaNumero() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== EJERCICIO 2: Conversión de Cadena a Número ===");
        System.out.print("Ingrese un número: ");
        String texto = scanner.nextLine();

        try {
            // parseInt puede lanzar NumberFormatException
            int numero = Integer.parseInt(texto);
            System.out.println("✓ Conversión exitosa: " + numero);
            System.out.println("El doble del número es: " + (numero * 2));

        } catch (NumberFormatException e) {
            // Capturamos cuando el texto no es un número válido
            System.err.println("❌ ERROR: '" + texto + "' no es un número válido");
            System.err.println("Debe ingresar solo dígitos (ej: 123, -45)");
            System.err.println("Detalles técnicos: " + e.getMessage());
        }
    }

    /**
     * Ejercicio 3: Lectura de archivo
     * Maneja FileNotFoundException cuando el archivo no existe
     * @param nombreArchivo ruta del archivo a leer
     */
    public static void lecturaArchivo(String nombreArchivo) {
        System.out.println("\n=== EJERCICIO 3: Lectura de Archivo ===");
        System.out.println("Intentando leer: " + nombreArchivo);

        BufferedReader reader = null;

        try {
            // FileReader lanza FileNotFoundException si el archivo no existe
            reader = new BufferedReader(new FileReader(nombreArchivo));

            System.out.println("\n--- Contenido del archivo ---");
            String linea;
            int numeroLinea = 1;

            // Leemos línea por línea
            while ((linea = reader.readLine()) != null) {
                System.out.println(numeroLinea + ": " + linea);
                numeroLinea++;
            }
            System.out.println("--- Fin del archivo ---");

        } catch (FileNotFoundException e) {
            // Archivo no encontrado
            System.err.println("❌ ERROR: El archivo '" + nombreArchivo + "' no existe");
            System.err.println("Verifique la ruta y el nombre del archivo");

        } catch (IOException e) {
            // Error al leer el archivo
            System.err.println("❌ ERROR al leer el archivo: " + e.getMessage());

        } finally {
            // Importante: SIEMPRE cerrar el recurso para liberar memoria
            // Esto se ejecuta incluso si hay excepción
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("Archivo cerrado correctamente");
                } catch (IOException e) {
                    System.err.println("Error al cerrar el archivo: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Ejercicio 4: Validación de edad con excepción personalizada
     * Lanza EdadInvalidaException si la edad está fuera del rango válido
     * @param edad la edad a validar
     * @throws EdadInvalidaException si la edad es menor a 0 o mayor a 120
     */
    public static void validarEdad(int edad) throws EdadInvalidaException {
        System.out.println("\n=== EJERCICIO 4: Validación de Edad ===");

        // Validamos el rango de edad
        if (edad < 0) {
            // Lanzamos la excepción personalizada con mensaje específico
            throw new EdadInvalidaException("La edad no puede ser negativa: " + edad);
        }

        if (edad > 120) {
            throw new EdadInvalidaException("La edad no puede ser mayor a 120 años: " + edad);
        }

        // Si llegamos aquí, la edad es válida
        System.out.println("✓ Edad válida: " + edad + " años");

        // Clasificación por edad
        if (edad < 18) {
            System.out.println("Categoría: Menor de edad");
        } else if (edad < 65) {
            System.out.println("Categoría: Adulto");
        } else {
            System.out.println("Categoría: Adulto mayor");
        }
    }

    /**
     * Ejercicio 5: Try-with-resources
     * Demuestra el uso de try-with-resources para manejar recursos automáticamente
     * Los recursos que implementan AutoCloseable se cierran automáticamente
     * @param nombreArchivo ruta del archivo a leer
     */
    public static void tryWithResources(String nombreArchivo) {
        System.out.println("\n=== EJERCICIO 5: Try-with-Resources ===");
        System.out.println("Leyendo: " + nombreArchivo);

        // Try-with-resources: el BufferedReader se cierra AUTOMÁTICAMENTE
        // No necesitamos un bloque finally para cerrar el recurso
        // Esto previene memory leaks y es la forma recomendada desde Java 7
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {

            System.out.println("\n--- Contenido del archivo ---");
            String linea;
            int contador = 0;

            while ((linea = reader.readLine()) != null) {
                contador++;
                System.out.println(linea);
            }

            System.out.println("--- Fin del archivo ---");
            System.out.println("Total de líneas leídas: " + contador);

            // Al salir del bloque try, reader.close() se llama AUTOMÁTICAMENTE

        } catch (FileNotFoundException e) {
            System.err.println("❌ ERROR: Archivo no encontrado: " + nombreArchivo);
            System.err.println("Sugerencia: Cree el archivo primero");

        } catch (IOException e) {
            System.err.println("❌ ERROR de lectura: " + e.getMessage());
        }

        // No necesitamos finally porque try-with-resources cierra automáticamente
        System.out.println("Recurso cerrado automáticamente por try-with-resources");
    }

    /**
     * Método auxiliar para probar la validación de edad
     * Captura y maneja la EdadInvalidaException
     */
    public static void probarValidacionEdad() {
        int[] edadesTest = {25, -5, 150, 0, 120, 18};

        for (int edad : edadesTest) {
            try {
                validarEdad(edad);
            } catch (EdadInvalidaException e) {
                // Capturamos nuestra excepción personalizada
                System.err.println("❌ " + e.getMessage());
            }
            System.out.println(); // Línea en blanco para separar
        }
    }
}