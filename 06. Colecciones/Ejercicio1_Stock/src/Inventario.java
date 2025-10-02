import java.util.ArrayList;

/**
 * Clase que gestiona el inventario de productos.
 * Implementa operaciones CRUD y funcionalidades de filtrado, búsqueda y reportes.
 * Relación 1 a N: Un Inventario contiene múltiples Productos.
 *
 * @author Renzo Calcatelli - UTN - Programación II
 * @version 1.0
 */
public class Inventario {
    // Colección dinámica para almacenar productos
    private ArrayList<Producto> productos;

    /**
     * Constructor que inicializa el ArrayList de productos vacío.
     */
    public Inventario() {
        this.productos = new ArrayList<>();
    }

    /**
     * Agrega un producto al inventario.
     *
     * @param p Producto a agregar
     */
    public void agregarProducto(Producto p) {
        productos.add(p);
        System.out.println("✓ Producto agregado: " + p.getNombre());
    }

    /**
     * Lista todos los productos del inventario mostrando su información completa.
     * Si no hay productos, muestra un mensaje indicándolo.
     */
    public void listarProductos() {
        if (productos.isEmpty()) {
            System.out.println("⚠ No hay productos en el inventario.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    LISTADO DE PRODUCTOS                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

        for (Producto p : productos) {
            p.mostrarInfo();
        }
        System.out.println("─────────────────────────────────────────────────────────────────────");
        System.out.println("Total de productos: " + productos.size());
    }

    /**
     * Busca un producto por su ID.
     *
     * @param id Identificador del producto a buscar
     * @return El producto encontrado, o null si no existe
     */
    public Producto buscarProductoPorId(String id) {
        for (Producto p : productos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Elimina un producto del inventario por su ID.
     *
     * @param id Identificador del producto a eliminar
     */
    public void eliminarProducto(String id) {
        Producto producto = buscarProductoPorId(id);

        if (producto != null) {
            productos.remove(producto);
            System.out.println("✓ Producto eliminado: " + producto.getNombre());
        } else {
            System.out.println("✗ Producto con ID " + id + " no encontrado.");
        }
    }

    /**
     * Actualiza el stock de un producto existente.
     *
     * @param id Identificador del producto
     * @param nuevaCantidad Nueva cantidad en stock
     */
    public void actualizarStock(String id, int nuevaCantidad) {
        Producto producto = buscarProductoPorId(id);

        if (producto != null) {
            int stockAnterior = producto.getCantidad();
            producto.setCantidad(nuevaCantidad);
            System.out.println("✓ Stock actualizado para " + producto.getNombre());
            System.out.println("  Stock anterior: " + stockAnterior + " → Nuevo stock: " + nuevaCantidad);
        } else {
            System.out.println("✗ Producto con ID " + id + " no encontrado.");
        }
    }

    /**
     * Filtra y muestra los productos que pertenecen a una categoría específica.
     *
     * @param categoria Categoría por la cual filtrar
     */
    public void filtrarPorCategoria(CategoriaProducto categoria) {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║  PRODUCTOS DE CATEGORÍA: " + categoria + " - " + categoria.getDescripcion());
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

        boolean encontrado = false;

        for (Producto p : productos) {
            if (p.getCategoria() == categoria) {
                p.mostrarInfo();
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("⚠ No hay productos en esta categoría.");
        }
    }

    /**
     * Calcula y retorna el total de unidades en stock sumando todos los productos.
     *
     * @return Suma total de unidades en el inventario
     */
    public int obtenerTotalStock() {
        int total = 0;

        for (Producto p : productos) {
            total += p.getCantidad();
        }

        return total;
    }

    /**
     * Encuentra y retorna el producto con mayor cantidad en stock.
     *
     * @return El producto con mayor stock, o null si el inventario está vacío
     */
    public Producto obtenerProductoConMayorStock() {
        if (productos.isEmpty()) {
            return null;
        }

        Producto mayorStock = productos.get(0);

        for (Producto p : productos) {
            if (p.getCantidad() > mayorStock.getCantidad()) {
                mayorStock = p;
            }
        }

        return mayorStock;
    }

    /**
     * Filtra y muestra productos cuyo precio esté dentro de un rango específico.
     *
     * @param min Precio mínimo (inclusivo)
     * @param max Precio máximo (inclusivo)
     */
    public void filtrarProductosPorPrecio(double min, double max) {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║  PRODUCTOS CON PRECIO ENTRE $" + min + " Y $" + max);
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

        boolean encontrado = false;

        for (Producto p : productos) {
            if (p.getPrecio() >= min && p.getPrecio() <= max) {
                p.mostrarInfo();
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("⚠ No hay productos en ese rango de precios.");
        }
    }

    /**
     * Muestra todas las categorías disponibles con sus descripciones.
     * Recorre los valores del enum CategoriaProducto.
     */
    public void mostrarCategoriasDisponibles() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   CATEGORÍAS DISPONIBLES                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

        for (CategoriaProducto cat : CategoriaProducto.values()) {
            System.out.println("• " + cat + ": " + cat.getDescripcion());
        }
    }
}