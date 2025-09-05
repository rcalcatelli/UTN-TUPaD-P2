/**
 * Clase Empleado - Sistema de Gestión de Empleados
 * Implementa conceptos de POO: constructores sobrecargados, métodos sobrecargados,
 * encapsulamiento, uso de this, y miembros estáticos.
 *
 * @author Renzo Calcatelli
 * @version 1.0
 */
public class Empleado {
    // ATRIBUTOS DE INSTANCIA (privados para aplicar encapsulamiento)
    private int id;
    private String nombre;
    private String puesto;
    private double salario;

    // ATRIBUTO ESTÁTICO - compartido por todas las instancias
    private static int totalEmpleados = 0;
    private static int contadorId = 1; // Para generar IDs automáticos

    // CONSTRUCTORES SOBRECARGADOS

    /**
     * Constructor completo que recibe todos los atributos como parámetros
     * @param id Identificador único del empleado
     * @param nombre Nombre completo del empleado
     * @param puesto Cargo que desempeña
     * @param salario Salario actual
     */
    public Empleado(int id, String nombre, String puesto, double salario) {
        // Uso de 'this' para distinguir parámetros de atributos de instancia
        this.id = id;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;

        // Incrementar el contador de empleados cada vez que se crea uno
        totalEmpleados++;

        // Actualizar el contador de ID para próximos empleados
        if (id >= contadorId) {
            contadorId = id + 1;
        }
    }

    /**
     * Constructor simplificado que recibe solo nombre y puesto
     * Asigna ID automático y salario por defecto
     * @param nombre Nombre completo del empleado
     * @param puesto Cargo que desempeña
     */
    public Empleado(String nombre, String puesto) {
        // Uso de 'this' para llamar al constructor principal
        this(contadorId, nombre, puesto, 30000.0); // Salario por defecto: $30,000
        // El constructor principal ya incrementa totalEmpleados
    }

    // MÉTODOS SOBRECARGADOS - actualizarSalario

    /**
     * Actualiza el salario aplicando un porcentaje de aumento
     * @param porcentajeAumento Porcentaje de aumento (ej: 15 para 15%)
     */
    public void actualizarSalario(double porcentajeAumento) {
        double aumento = this.salario * (porcentajeAumento / 100);
        this.salario += aumento;

        System.out.println("Salario actualizado con " + porcentajeAumento + "% de aumento.");
        System.out.println("Nuevo salario: $" + String.format("%.2f", this.salario));
    }

    /**
     * Actualiza el salario sumando una cantidad fija
     * @param cantidadFija Cantidad fija a aumentar al salario actual
     */
    public void actualizarSalario(int cantidadFija) {
        this.salario += cantidadFija;

        System.out.println("Salario actualizado con aumento fijo de $" + cantidadFija);
        System.out.println("Nuevo salario: $" + String.format("%.2f", this.salario));
    }

    // MÉTODO toString() - Representación legible del objeto

    /**
     * Devuelve una representación en cadena del empleado
     * @return String con la información del empleado
     */
    @Override
    public String toString() {
        return String.format("=== EMPLEADO ===\n" +
                        "ID: %d\n" +
                        "Nombre: %s\n" +
                        "Puesto: %s\n" +
                        "Salario: $%.2f\n" +
                        "================",
                this.id, this.nombre, this.puesto, this.salario);
    }

    // MÉTODO ESTÁTICO

    /**
     * Método estático que retorna el total de empleados creados
     * @return Número total de empleados instanciados
     */
    public static int mostrarTotalEmpleados() {
        return totalEmpleados;
    }

    /**
     * Método estático adicional para mostrar estadísticas
     */
    public static void mostrarEstadisticas() {
        System.out.println("=== ESTADÍSTICAS ===");
        System.out.println("Total de empleados creados: " + totalEmpleados);
        System.out.println("Próximo ID disponible: " + contadorId);
        System.out.println("====================");
    }

    // MÉTODOS GETTERS Y SETTERS (Encapsulamiento)

    /**
     * Obtiene el ID del empleado
     * @return ID del empleado
     */
    public int getId() {
        return this.id;
    }

    /**
     * Establece el ID del empleado
     * @param id Nuevo ID del empleado
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del empleado
     * @return Nombre del empleado
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Establece el nombre del empleado
     * @param nombre Nuevo nombre del empleado
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el puesto del empleado
     * @return Puesto del empleado
     */
    public String getPuesto() {
        return this.puesto;
    }

    /**
     * Establece el puesto del empleado
     * @param puesto Nuevo puesto del empleado
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    /**
     * Obtiene el salario del empleado
     * @return Salario del empleado
     */
    public double getSalario() {
        return this.salario;
    }

    /**
     * Establece el salario del empleado
     * @param salario Nuevo salario del empleado
     */
    public void setSalario(double salario) {
        if (salario >= 0) {
            this.salario = salario;
        } else {
            System.out.println("Error: El salario no puede ser negativo");
        }
    }
}