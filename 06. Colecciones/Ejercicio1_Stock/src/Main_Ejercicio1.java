/**
 * Clase principal para ejecutar el Ejercicio 1: Sistema de Stock
 * Implementa las 10 tareas requeridas en el trabajo práctico.
 *
 * @author Renzo Calcatelli - UTN - Programación II
 * @version 1.0
 */
public class Main_Ejercicio1 {
    public static void main(String[] args) {
        // Encabezado del programa
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║         TRABAJO PRÁCTICO 6 - EJERCICIO 1: SISTEMA DE STOCK           ║");
        System.out.println("║                 Universidad Tecnológica Nacional                     ║");
        System.out.println("║                      Programación II                                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝\n");

        // Crear instancia del inventario
        Inventario inventario = new Inventario();

        // ===================================================================
        // TAREA 1: Crear al menos cinco productos con diferentes categorías
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 1: Creación de productos");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        Producto p1 = new Producto("P001", "Arroz Integral 1kg", 850.0, 50, CategoriaProducto.ALIMENTOS);
        Producto p2 = new Producto("P002", "Notebook HP Pavilion", 2500.0, 15, CategoriaProducto.ELECTRONICA);
        Producto p3 = new Producto("P003", "Remera Deportiva Nike", 1800.0, 30, CategoriaProducto.ROPA);
        Producto p4 = new Producto("P004", "Lámpara LED 12W", 1200.0, 25, CategoriaProducto.HOGAR);
        Producto p5 = new Producto("P005", "Mouse Inalámbrico Logitech", 950.0, 40, CategoriaProducto.ELECTRONICA);

        inventario.agregarProducto(p1);
        inventario.agregarProducto(p2);
        inventario.agregarProducto(p3);
        inventario.agregarProducto(p4);
        inventario.agregarProducto(p5);

        // ===================================================================
        // TAREA 2: Listar todos los productos mostrando información y categoría
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 2: Listado completo de productos");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        inventario.listarProductos();

        // ===================================================================
        // TAREA 3: Buscar un producto por ID y mostrar su información
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 3: Búsqueda de producto por ID");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        System.out.println("Buscando producto con ID 'P002'...");
        Producto encontrado = inventario.buscarProductoPorId("P002");

        if (encontrado != null) {
            System.out.println("✓ Producto encontrado:");
            encontrado.mostrarInfo();
        } else {
            System.out.println("✗ Producto no encontrado.");
        }

        // ===================================================================
        // TAREA 4: Filtrar productos que pertenezcan a una categoría específica
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 4: Filtrado por categoría");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        inventario.filtrarPorCategoria(CategoriaProducto.ELECTRONICA);

        // ===================================================================
        // TAREA 5: Eliminar un producto por ID y listar productos restantes
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 5: Eliminación de producto");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        System.out.println("Eliminando producto con ID 'P001'...");
        inventario.eliminarProducto("P001");

        System.out.println("\nProductos restantes:");
        inventario.listarProductos();

        // ===================================================================
        // TAREA 6: Actualizar el stock de un producto existente
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 6: Actualización de stock");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        System.out.println("Actualizando stock del producto 'P003'...");
        inventario.actualizarStock("P003", 45);

        // ===================================================================
        // TAREA 7: Mostrar el total de stock disponible
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 7: Cálculo de stock total");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        int totalStock = inventario.obtenerTotalStock();
        System.out.println("📊 Total de unidades en stock: " + totalStock + " unidades");

        // ===================================================================
        // TAREA 8: Obtener y mostrar el producto con mayor stock
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 8: Producto con mayor stock");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        Producto mayorStock = inventario.obtenerProductoConMayorStock();

        if (mayorStock != null) {
            System.out.println("🏆 Producto con mayor stock:");
            mayorStock.mostrarInfo();
        }

        // ===================================================================
        // TAREA 9: Filtrar productos con precios entre $1000 y $3000
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 9: Filtrado por rango de precio");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        inventario.filtrarProductosPorPrecio(1000, 3000);

        // ===================================================================
        // TAREA 10: Mostrar las categorías disponibles con sus descripciones
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 10: Categorías disponibles");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        inventario.mostrarCategoriasDisponibles();

        // Mensaje de finalización
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║              FIN DEL EJERCICIO 1 - SISTEMA DE STOCK                  ║");
        System.out.println("║                    ✓ Todas las tareas completadas                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝\n");
    }
}
