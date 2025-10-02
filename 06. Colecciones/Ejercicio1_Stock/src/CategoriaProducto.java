/**
 * Enumeración que define las categorías de productos disponibles en el inventario.
 * Cada categoría incluye una descripción detallada.
 *
 * @author Renzo Calcatelli - UTN - Programación II
 * @version 1.0
 */
public enum CategoriaProducto {
    ALIMENTOS("Productos comestibles"),
    ELECTRONICA("Dispositivos electrónicos"),
    ROPA("Prendas de vestir"),
    HOGAR("Artículos para el hogar");

    // Atributo privado para almacenar la descripción
    private final String descripcion;

    /**
     * Constructor del enum que asigna una descripción a cada categoría.
     *
     * @param descripcion Texto descriptivo de la categoría
     */
    CategoriaProducto(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la descripción de la categoría.
     *
     * @return String con la descripción de la categoría
     */
    public String getDescripcion() {
        return descripcion;
    }
}