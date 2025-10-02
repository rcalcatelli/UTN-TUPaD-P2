/**
 * Clase que representa un autor de libros.
 * Contiene información básica sobre el autor: identificación, nombre y nacionalidad.
 *
 * @author Renzo Calcatelli - UTN - Programación II
 * @version 1.0
 */
public class Autor {
    // Atributos privados (encapsulamiento)
    private String id;
    private String nombre;
    private String nacionalidad;

    /**
     * Constructor completo de la clase Autor.
     *
     * @param id Identificador único del autor
     * @param nombre Nombre completo del autor
     * @param nacionalidad Nacionalidad del autor
     */
    public Autor(String id, String nombre, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    // Métodos getter para acceder a los atributos privados

    /**
     * @return El identificador único del autor
     */
    public String getId() {
        return id;
    }

    /**
     * @return El nombre completo del autor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return La nacionalidad del autor
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Muestra en consola la información completa del autor.
     * Formato: ID, nombre y nacionalidad.
     */
    public void mostrarInfo() {
        System.out.println("Autor ID: " + id +
                " | Nombre: " + nombre +
                " | Nacionalidad: " + nacionalidad);
    }
}