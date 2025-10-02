/**
 * Clase principal para ejecutar el Ejercicio 2: Biblioteca y Libros
 * Implementa las 9 tareas requeridas en el trabajo práctico.
 * Demuestra el uso de composición 1 a N entre Biblioteca y Libro.
 *
 * @author Renzo Calcatelli - UTN - Programación II
 * @version 1.0
 */
public class Main_Ejercicio2 {
    public static void main(String[] args) {
        // Encabezado del programa
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║       TRABAJO PRÁCTICO 6 - EJERCICIO 2: BIBLIOTECA Y LIBROS          ║");
        System.out.println("║                 Universidad Tecnológica Nacional                     ║");
        System.out.println("║                      Programación II                                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝\n");

        // ===================================================================
        // TAREA 1: Crear una biblioteca
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 1: Creación de la biblioteca");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        Biblioteca biblioteca = new Biblioteca("Biblioteca Central UTN");
        System.out.println("✓ Biblioteca creada: " + biblioteca.getNombre());

        // ===================================================================
        // TAREA 2: Crear al menos tres autores
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 2: Creación de autores");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        Autor autor1 = new Autor("A001", "Gabriel García Márquez", "Colombiana");
        Autor autor2 = new Autor("A002", "Jorge Luis Borges", "Argentina");
        Autor autor3 = new Autor("A003", "Isabel Allende", "Chilena");

        System.out.println("✓ Autores creados:");
        autor1.mostrarInfo();
        autor2.mostrarInfo();
        autor3.mostrarInfo();

        // ===================================================================
        // TAREA 3: Agregar 5 libros asociados a alguno de los autores
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 3: Agregando libros a la biblioteca");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // Nota: Los libros se crean DENTRO de la biblioteca (composición)
        biblioteca.agregarLibro("ISBN978-0307474728", "Cien Años de Soledad", 1967, autor1);
        biblioteca.agregarLibro("ISBN978-0142437223", "El Aleph", 1949, autor2);
        biblioteca.agregarLibro("ISBN978-1501117015", "La Casa de los Espíritus", 1982, autor3);
        biblioteca.agregarLibro("ISBN978-0802130303", "Ficciones", 1944, autor2);
        biblioteca.agregarLibro("ISBN978-0307389732", "El Amor en los Tiempos del Cólera", 1985, autor1);

        // ===================================================================
        // TAREA 4: Listar todos los libros con información del autor
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 4: Listado completo de libros");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        biblioteca.listarLibros();

        // ===================================================================
        // TAREA 5: Buscar un libro por su ISBN y mostrar información
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 5: Búsqueda de libro por ISBN");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        String isbnBuscado = "ISBN978-0142437223";
        System.out.println("Buscando libro con ISBN: " + isbnBuscado);

        Libro libroEncontrado = biblioteca.buscarLibroPorIsbn(isbnBuscado);

        if (libroEncontrado != null) {
            System.out.println("✓ Libro encontrado:");
            libroEncontrado.mostrarInfo();
            System.out.println("\nInformación del autor:");
            libroEncontrado.getAutor().mostrarInfo();
        } else {
            System.out.println("✗ Libro no encontrado.");
        }

        // ===================================================================
        // TAREA 6: Filtrar libros publicados en un año específico
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 6: Filtrado por año de publicación");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        biblioteca.filtrarLibrosPorAnio(1985);

        // ===================================================================
        // TAREA 7: Eliminar un libro por ISBN y listar restantes
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 7: Eliminación de libro");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        String isbnEliminar = "ISBN978-0307474728";
        System.out.println("Eliminando libro con ISBN: " + isbnEliminar);
        biblioteca.eliminarLibro(isbnEliminar);

        System.out.println("\nLibros restantes en la biblioteca:");
        biblioteca.listarLibros();

        // ===================================================================
        // TAREA 8: Mostrar la cantidad total de libros
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 8: Cantidad total de libros");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        int cantidadTotal = biblioteca.obtenerCantidadLibros();
        System.out.println("📚 Total de libros en " + biblioteca.getNombre() + ": " + cantidadTotal);

        // ===================================================================
        // TAREA 9: Listar todos los autores de los libros disponibles
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 9: Autores disponibles en la biblioteca");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        biblioteca.mostrarAutoresDisponibles();

        // ===================================================================
        // DEMOSTRACIÓN: Composición 1 a N
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  DEMOSTRACIÓN: Concepto de Composición");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        System.out.println("💡 COMPOSICIÓN 1 a N:");
        System.out.println("   • Una Biblioteca (1) contiene múltiples Libros (N)");
        System.out.println("   • Los Libros se crean DENTRO de la Biblioteca");
        System.out.println("   • Si se elimina la Biblioteca, se eliminan sus Libros");
        System.out.println("   • Los Libros NO pueden existir sin una Biblioteca");
        System.out.println("\n📝 En este ejercicio:");
        System.out.println("   • Usamos agregarLibro(params...) que crea el Libro internamente");
        System.out.println("   • Al eliminar un libro, deja de existir completamente");
        System.out.println("   • La biblioteca es responsable del ciclo de vida de sus libros");

        // Mensaje de finalización
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║            FIN DEL EJERCICIO 2 - BIBLIOTECA Y LIBROS                 ║");
        System.out.println("║                    ✓ Todas las tareas completadas                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝\n");
    }
}