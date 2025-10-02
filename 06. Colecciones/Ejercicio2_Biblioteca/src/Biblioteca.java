import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase que representa una biblioteca y gestiona su colección de libros.
 * Implementa una relación de COMPOSICIÓN 1 a N con Libro:
 * - Una Biblioteca contiene múltiples Libros
 * - Si la Biblioteca se elimina, también se eliminan sus Libros
 *
 * @author Renzo Calcatelli - UTN - Programación II
 * @version 1.0
 */
public class Biblioteca {
    // Atributos privados
    private String nombre;
    private List<Libro> libros;  // Composición: la biblioteca "posee" los libros

    /**
     * Constructor que inicializa la biblioteca con un nombre.
     * La lista de libros comienza vacía.
     *
     * @param nombre Nombre de la biblioteca
     */
    public Biblioteca(String nombre) {
        this.nombre = nombre;
        this.libros = new ArrayList<>();
    }

    /**
     * @return El nombre de la biblioteca
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Crea y agrega un nuevo libro a la biblioteca.
     * Este método crea el objeto Libro internamente (composición).
     *
     * @param isbn ISBN del libro
     * @param titulo Título del libro
     * @param anioPublicacion Año de publicación
     * @param autor Autor del libro
     */
    public void agregarLibro(String isbn, String titulo, int anioPublicacion, Autor autor) {
        // Crear el libro dentro de la biblioteca (composición)
        Libro libro = new Libro(isbn, titulo, anioPublicacion, autor);
        libros.add(libro);
        System.out.println("✓ Libro agregado: " + titulo);
    }

    /**
     * Lista todos los libros disponibles en la biblioteca.
     * Muestra la información completa de cada libro incluyendo su autor.
     */
    public void listarLibros() {
        if (libros.isEmpty()) {
            System.out.println("⚠ No hay libros en la biblioteca.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║              CATÁLOGO DE LIBROS - " + nombre.toUpperCase());
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

        for (Libro libro : libros) {
            libro.mostrarInfo();
        }

        System.out.println("─────────────────────────────────────────────────────────────────────");
        System.out.println("Total de libros: " + libros.size());
    }

    /**
     * Busca un libro por su ISBN.
     *
     * @param isbn ISBN del libro a buscar
     * @return El libro encontrado, o null si no existe
     */
    public Libro buscarLibroPorIsbn(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    /**
     * Elimina un libro de la biblioteca por su ISBN.
     * Al eliminar el libro, este deja de existir (composición).
     *
     * @param isbn ISBN del libro a eliminar
     */
    public void eliminarLibro(String isbn) {
        Libro libro = buscarLibroPorIsbn(isbn);

        if (libro != null) {
            libros.remove(libro);
            System.out.println("✓ Libro eliminado: " + libro.getTitulo());
            // Al ser composición, el libro ya no existe fuera de la biblioteca
        } else {
            System.out.println("✗ Libro con ISBN " + isbn + " no encontrado.");
        }
    }

    /**
     * Obtiene la cantidad total de libros en la biblioteca.
     *
     * @return Número de libros en la colección
     */
    public int obtenerCantidadLibros() {
        return libros.size();
    }

    /**
     * Filtra y muestra los libros publicados en un año específico.
     *
     * @param anio Año de publicación a filtrar
     */
    public void filtrarLibrosPorAnio(int anio) {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║         LIBROS PUBLICADOS EN EL AÑO " + anio);
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

        boolean encontrado = false;

        for (Libro libro : libros) {
            if (libro.getAnioPublicacion() == anio) {
                libro.mostrarInfo();
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("⚠ No hay libros publicados en ese año.");
        }
    }

    /**
     * Muestra todos los autores disponibles en la biblioteca sin duplicados.
     * Recorre todos los libros y extrae los autores únicos.
     */
    public void mostrarAutoresDisponibles() {
        if (libros.isEmpty()) {
            System.out.println("⚠ No hay libros en la biblioteca.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║              AUTORES DISPONIBLES EN " + nombre.toUpperCase());
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

        // Usar un Set para evitar duplicados
        Set<String> autoresUnicos = new HashSet<>();

        for (Libro libro : libros) {
            String idAutor = libro.getAutor().getId();

            // Solo mostrar si no lo hemos mostrado antes
            if (!autoresUnicos.contains(idAutor)) {
                autoresUnicos.add(idAutor);
                libro.getAutor().mostrarInfo();
            }
        }

        System.out.println("─────────────────────────────────────────────────────────────────────");
        System.out.println("Total de autores: " + autoresUnicos.size());
    }
}