/**
 * Clase que representa un libro en la biblioteca.
 * Cada libro tiene un ISBN, título, año de publicación y un autor asociado.
 *
 * @author Renzo Calcatelli - UTN - Programación II
 * @version 1.0
 */
public class Libro {
    // Atributos privados (encapsulamiento)
    private String isbn;
    private String titulo;
    private int anioPublicacion;
    private Autor autor;  // Relación con Autor (asociación)

    /**
     * Constructor completo de la clase Libro.
     *
     * @param isbn Identificador único del libro (International Standard Book Number)
     * @param titulo Título del libro
     * @param anioPublicacion Año en que fue publicado el libro
     * @param autor Autor del libro (objeto de tipo Autor)
     */
    public Libro(String isbn, String titulo, int anioPublicacion, Autor autor) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.autor = autor;
    }

    // Métodos getter para acceder a los atributos privados

    /**
     * @return El ISBN del libro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @return El título del libro
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @return El año de publicación
     */
    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    /**
     * @return El autor del libro
     */
    public Autor getAutor() {
        return autor;
    }

    /**
     * Muestra en consola la información completa del libro.
     * Incluye: ISBN, título, año de publicación y nombre del autor.
     */
    public void mostrarInfo() {
        System.out.println("ISBN: " + isbn +
                " | Título: " + titulo +
                " | Año: " + anioPublicacion +
                " | Autor: " + autor.getNombre());
    }
}
