/**
 * Clase que representa un producto en el sistema de inventario.
 * Contiene información sobre identificación, precio, stock y categoría.
 *
 * @author Renzo Calcatelli - UTN - Programación II
 * @version 1.0
 */
public class Producto {
    // Atributos privados (encapsulamiento)
    private String id;
    private String nombre;
    private double precio;
    private int cantidad;
    private CategoriaProducto categoria;

    /**
     * Constructor completo de la clase Producto.
     *
     * @param id Identificador único del producto
     * @param nombre Nombre descriptivo del producto
     * @param precio Precio del producto en pesos
     * @param cantidad Cantidad disponible en stock
     * @param categoria Categoría a la que pertenece el producto
     */
    public Producto(String id, String nombre, double precio, int cantidad, CategoriaProducto categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    // Métodos getter para acceder a los atributos privados

    /**
     * @return El identificador único del producto
     */
    public String getId() {
        return id;
    }

    /**
     * @return El nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return El precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @return La cantidad disponible en stock
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @return La categoría del producto
     */
    public CategoriaProducto getCategoria() {
        return categoria;
    }

    /**
     * Establece una nueva cantidad de stock para el producto.
     *
     * @param cantidad Nueva cantidad en stock
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Muestra en consola toda la información del producto de forma formateada.
     * Incluye: ID, nombre, precio, stock, categoría y descripción de categoría.
     */
    public void mostrarInfo() {
        System.out.println("ID: " + id +
                " | Nombre: " + nombre +
                " | Precio: $" + precio +
                " | Stock: " + cantidad +
                " | Categoría: " + categoria +
                " (" + categoria.getDescripcion() + ")");
    }
}