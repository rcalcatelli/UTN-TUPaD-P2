import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Clase Principal - Main
 * Demuestra el funcionamiento completo del Trabajo Práctico 8
 * Integra interfaces, herencia múltiple y manejo de excepciones
 */
public class Main {

    public static void main(String[] args) {
        mostrarMenu();
    }

    /**
     * Muestra un menú interactivo para ejecutar todas las demostraciones
     */
    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n╔═══════════════════════════════════════════════════════╗");
            System.out.println("║   TRABAJO PRÁCTICO 8: INTERFACES Y EXCEPCIONES       ║");
            System.out.println("║              Universidad Tecnológica Nacional         ║");
            System.out.println("╚═══════════════════════════════════════════════════════╝");
            System.out.println("\n--- PARTE 1: SISTEMA E-COMMERCE ---");
            System.out.println("1. Demo completa del sistema E-commerce");
            System.out.println("2. Demo de Interfaces Pagable");
            System.out.println("3. Demo de Medios de Pago con Descuentos");
            System.out.println("4. Demo de Sistema de Notificaciones");
            System.out.println("\n--- PARTE 2: EXCEPCIONES ---");
            System.out.println("5. División segura (ArithmeticException)");
            System.out.println("6. Conversión de cadena a número (NumberFormatException)");
            System.out.println("7. Lectura de archivo (FileNotFoundException)");
            System.out.println("8. Validación de edad (Excepción personalizada)");
            System.out.println("9. Try-with-resources (Buenas prácticas)");
            System.out.println("\n0. Salir");
            System.out.print("\nSeleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        demoEcommerceCompleto();
                        break;
                    case 2:
                        demoPagable();
                        break;
                    case 3:
                        demoMediosPago();
                        break;
                    case 4:
                        demoNotificaciones();
                        break;
                    case 5:
                        EjerciciosExcepciones.divisionSegura();
                        break;
                    case 6:
                        EjerciciosExcepciones.conversionCadenaNumero();
                        break;
                    case 7:
                        System.out.print("Ingrese el nombre del archivo a leer: ");
                        String archivo = scanner.nextLine();
                        EjerciciosExcepciones.lecturaArchivo(archivo);
                        break;
                    case 8:
                        EjerciciosExcepciones.probarValidacionEdad();
                        break;
                    case 9:
                        prepararArchivoTest();
                        EjerciciosExcepciones.tryWithResources("test.txt");
                        break;
                    case 0:
                        System.out.println("\n¡Gracias por usar el sistema! 👋");
                        break;
                    default:
                        System.out.println("❌ Opción inválida");
                }

                if (opcion != 0) {
                    System.out.println("\n[Presione Enter para continuar]");
                    scanner.nextLine();
                }

            } catch (Exception e) {
                System.err.println("❌ Error: Ingrese un número válido");
                scanner.nextLine(); // Limpiar buffer
                opcion = -1; // Continuar el loop
            }

        } while (opcion != 0);

        scanner.close();
    }

    /**
     * Demostración completa del sistema E-commerce
     * Integra todas las interfaces y muestra el flujo completo
     */
    public static void demoEcommerceCompleto() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║        DEMO COMPLETA: SISTEMA E-COMMERCE               ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        // 1. Crear productos
        Producto laptop = new Producto("Laptop Dell XPS 15", 1500.00);
        Producto mouse = new Producto("Mouse Logitech MX Master", 99.99);
        Producto teclado = new Producto("Teclado Mecánico Corsair", 150.00);
        Producto monitor = new Producto("Monitor LG 27'' 4K", 450.00);

        // 2. Crear un cliente
        Cliente cliente = new Cliente(
                "Juan Pérez",
                "juan.perez@email.com",
                "+54 11 1234-5678"
        );

        // 3. Crear un pedido y asociar el cliente
        Pedido pedido = new Pedido(1001);
        pedido.setCliente(cliente);

        // 4. Agregar productos al pedido
        System.out.println("\n📦 Agregando productos al pedido...");
        pedido.agregarProducto(laptop);
        pedido.agregarProducto(mouse);
        pedido.agregarProducto(teclado);
        pedido.agregarProducto(monitor);

        // 5. Mostrar el pedido (interfaz Pagable)
        System.out.println("\n" + pedido);

        // 6. Cambiar estados del pedido (interfaz Notificable)
        System.out.println("\n📋 Procesando el pedido...");
        pedido.cambiarEstado("Procesando");

        try {
            Thread.sleep(1000); // Simular procesamiento
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pedido.cambiarEstado("Enviado");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pedido.cambiarEstado("Entregado");

        // 7. Procesar pago con diferentes medios
        System.out.println("\n💳 Procesando pago...");
        double totalPedido = pedido.calcularTotal();

        // Opción 1: Pago con Tarjeta de Crédito (15% descuento)
        TarjetaCredito tarjeta = new TarjetaCredito(
                "1234567890123456",
                "Juan Pérez",
                0.15
        );

        double totalConDescuento = tarjeta.aplicarDescuento(totalPedido);
        tarjeta.procesarPago(totalConDescuento);

        System.out.println("\n✅ Compra completada exitosamente!");
    }

    /**
     * Demo específica de la interfaz Pagable
     */
    public static void demoPagable() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║          DEMO: INTERFAZ PAGABLE                        ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        System.out.println("\nLa interfaz Pagable permite polimorfismo:");
        System.out.println("Tanto Producto como Pedido implementan calcularTotal()");

        // Crear productos
        Producto p1 = new Producto("Smartphone", 800.00);
        Producto p2 = new Producto("Auriculares", 150.00);

        // Crear pedido
        Pedido pedido = new Pedido(2001);
        pedido.agregarProducto(p1);
        pedido.agregarProducto(p2);

        // Polimorfismo: tratamos ambos como Pagable
        Pagable[] items = {p1, p2, pedido};

        System.out.println("\n--- Calculando totales con polimorfismo ---");
        for (Pagable item : items) {
            String tipo = item.getClass().getSimpleName();
            System.out.println(tipo + " - Total: $" +
                    String.format("%.2f", item.calcularTotal()));
        }
    }

    /**
     * Demo de medios de pago con descuentos
     * Muestra herencia múltiple de interfaces
     */
    public static void demoMediosPago() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║       DEMO: MEDIOS DE PAGO Y DESCUENTOS                ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        double montoPedido = 1000.00;
        System.out.println("\nMonto del pedido: $" + montoPedido);

        // Tarjeta de crédito con 20% descuento
        System.out.println("\n--- Opción 1: Tarjeta de Crédito ---");
        TarjetaCredito tarjeta = new TarjetaCredito(
                "4532123456789012",
                "María García",
                0.20
        );
        double montoTarjeta = tarjeta.aplicarDescuento(montoPedido);
        tarjeta.procesarPago(montoTarjeta);

        // PayPal con 10% descuento
        System.out.println("\n--- Opción 2: PayPal ---");
        PayPal paypal = new PayPal("maria.garcia@email.com", 0.10);
        double montoPaypal = paypal.aplicarDescuento(montoPedido);
        paypal.procesarPago(montoPaypal);

        System.out.println("\n📊 Comparación de descuentos:");
        System.out.println("Tarjeta de Crédito: $" + String.format("%.2f", montoTarjeta));
        System.out.println("PayPal: $" + String.format("%.2f", montoPaypal));
        System.out.println("Ahorro con tarjeta: $" +
                String.format("%.2f", montoPaypal - montoTarjeta));
    }

    /**
     * Demo del sistema de notificaciones
     * Muestra el patrón Observer con interfaces
     */
    public static void demoNotificaciones() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║       DEMO: SISTEMA DE NOTIFICACIONES                  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        // Crear cliente
        Cliente cliente = new Cliente(
                "Carlos Rodríguez",
                "carlos@email.com",
                "+54 11 9876-5432"
        );

        // Crear pedido y asociar cliente
        Pedido pedido = new Pedido(3001);
        pedido.setCliente(cliente);

        // Agregar productos
        pedido.agregarProducto(new Producto("Tablet", 500.00));

        System.out.println("\nSimulando ciclo de vida del pedido...");
        System.out.println("El cliente recibirá notificaciones en cada cambio:\n");

        try {
            pedido.cambiarEstado("Confirmado");
            Thread.sleep(1500);

            pedido.cambiarEstado("En preparación");
            Thread.sleep(1500);

            pedido.cambiarEstado("Despachado");
            Thread.sleep(1500);

            pedido.cambiarEstado("En camino");
            Thread.sleep(1500);

            pedido.cambiarEstado("Entregado");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n✅ Todas las notificaciones fueron enviadas");
    }

    /**
     * Prepara un archivo de prueba para los ejercicios de excepciones
     */
    private static void prepararArchivoTest() {
        try (FileWriter writer = new FileWriter("test.txt")) {
            writer.write("Línea 1: Este es un archivo de prueba\n");
            writer.write("Línea 2: Para demostrar try-with-resources\n");
            writer.write("Línea 3: El recurso se cierra automáticamente\n");
            writer.write("Línea 4: Sin necesidad de finally\n");
            writer.write("Línea 5: ¡Universidad Tecnológica Nacional! ☕\n");
        } catch (IOException e) {
            System.err.println("Error al crear archivo de prueba: " + e.getMessage());
        }
    }
}