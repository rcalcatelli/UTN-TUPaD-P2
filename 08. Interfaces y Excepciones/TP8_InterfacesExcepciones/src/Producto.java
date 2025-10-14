/**
 * Clase Producto
 * Representa un producto del e-commerce con nombre y precio
 * Implementa Pagable para poder calcular su total
 */
public class Producto implements Pagable {
    // Atributos privados (encapsulamiento)
    private String nombre;
    private double precio;

    /**
     * Constructor con parámetros
     * @param nombre nombre del producto
     * @param precio precio del producto
     */
    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Implementación del método calcularTotal() de la interfaz Pagable
     * Para un producto, el total es simplemente su precio
     * @return el precio del producto
     */
    @Override
    public double calcularTotal() {
        return this.precio;
    }

    /**
     * Método toString para representación en texto del objeto
     * @return cadena con información del producto
     */
    @Override
    public String toString() {
        return String.format("%s - $%.2f", nombre, precio);
    }
}