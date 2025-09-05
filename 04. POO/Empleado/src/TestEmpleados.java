/**
 * Clase de prueba para demostrar el funcionamiento del Sistema de Gestión de Empleados
 * Prueba todos los conceptos implementados en la clase Empleado
 *
 * @author Renzo Calcatelli
 * @version 1.0
 */
public class TestEmpleados {

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTIÓN DE EMPLEADOS ===\n");

        // 1. CREACIÓN DE EMPLEADOS CON DIFERENTES CONSTRUCTORES
        System.out.println("1. CREANDO EMPLEADOS CON CONSTRUCTORES SOBRECARGADOS:\n");

        // Usando constructor completo
        Empleado emp1 = new Empleado(100, "Juan Pérez", "Desarrollador Senior", 85000.0);
        System.out.println("✓ Empleado creado con constructor completo:");
        System.out.println(emp1);
        System.out.println();

        // Usando constructor simplificado (ID automático y salario por defecto)
        Empleado emp2 = new Empleado("María González", "Analista de Sistemas");
        System.out.println("✓ Empleado creado con constructor simplificado:");
        System.out.println(emp2);
        System.out.println();

        // Creando más empleados para probar
        Empleado emp3 = new Empleado("Carlos Rodríguez", "Project Manager");
        Empleado emp4 = new Empleado(200, "Ana Martínez", "Diseñadora UX", 60000.0);
        Empleado emp5 = new Empleado("Luis Fernández", "Tester QA");

        System.out.println("✓ Se crearon 3 empleados adicionales\n");

        // 2. MOSTRAR TOTAL DE EMPLEADOS (MÉTODO ESTÁTICO)
        System.out.println("2. VERIFICANDO CONTADOR DE EMPLEADOS:\n");
        System.out.println("Total de empleados creados: " + Empleado.mostrarTotalEmpleados());
        Empleado.mostrarEstadisticas();
        System.out.println();

        // 3. PROBANDO MÉTODOS SOBRECARGADOS actualizarSalario
        System.out.println("3. PROBANDO MÉTODOS SOBRECARGADOS - actualizarSalario:\n");

        System.out.println("--- Empleado antes del aumento ---");
        System.out.println(emp1);
        System.out.println();

        // Aplicando aumento por porcentaje
        System.out.println("Aplicando aumento del 15% a " + emp1.getNombre() + ":");
        emp1.actualizarSalario(15.0);
        System.out.println();

        System.out.println("--- Empleado después del aumento por porcentaje ---");
        System.out.println(emp1);
        System.out.println();

        // Aplicando aumento fijo
        System.out.println("Aplicando aumento fijo de $5000 a " + emp2.getNombre() + ":");
        emp2.actualizarSalario(5000);
        System.out.println();

        System.out.println("--- Empleado después del aumento fijo ---");
        System.out.println(emp2);
        System.out.println();

        // 4. PROBANDO ENCAPSULAMIENTO CON GETTERS Y SETTERS
        System.out.println("4. PROBANDO ENCAPSULAMIENTO (Getters y Setters):\n");

        System.out.println("Información del empleado 3 usando getters:");
        System.out.println("ID: " + emp3.getId());
        System.out.println("Nombre: " + emp3.getNombre());
        System.out.println("Puesto: " + emp3.getPuesto());
        System.out.println("Salario: $" + String.format("%.2f", emp3.getSalario()));
        System.out.println();

        // Modificando datos usando setters
        System.out.println("Modificando puesto del empleado 3 usando setter:");
        emp3.setPuesto("Senior Project Manager");
        System.out.println("Nuevo puesto: " + emp3.getPuesto());
        System.out.println();

        // Probando validación en setter de salario
        System.out.println("Probando validación - intentando asignar salario negativo:");
        emp3.setSalario(-1000);
        System.out.println("Salario actual: $" + String.format("%.2f", emp3.getSalario()));
        System.out.println();

        // 5. MOSTRANDO INFORMACIÓN FINAL DE TODOS LOS EMPLEADOS
        System.out.println("5. INFORMACIÓN FINAL DE TODOS LOS EMPLEADOS:\n");

        // Array para facilitar la iteración
        Empleado[] empleados = {emp1, emp2, emp3, emp4, emp5};

        for (int i = 0; i < empleados.length; i++) {
            System.out.println("EMPLEADO " + (i + 1) + ":");
            System.out.println(empleados[i]);
            System.out.println();
        }

        // 6. ESTADÍSTICAS FINALES
        System.out.println("6. ESTADÍSTICAS FINALES DEL SISTEMA:\n");
        Empleado.mostrarEstadisticas();

        // Aplicando más aumentos para demostrar los métodos sobrecargados
        System.out.println("\n7. DEMOSTRANDO MÁS AUMENTOS SALARIALES:\n");

        System.out.println("Aplicando 10% de aumento a " + emp4.getNombre() + ":");
        emp4.actualizarSalario(10.0);
        System.out.println();

        System.out.println("Aplicando aumento fijo de $2500 a " + emp5.getNombre() + ":");
        emp5.actualizarSalario(2500);
        System.out.println();

        System.out.println("=== PROGRAMA FINALIZADO EXITOSAMENTE ===");
    }
}