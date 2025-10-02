/**
 * Clase que representa un curso universitario.
 * Implementa el lado "N" de la relación bidireccional 1 a N con Profesor.
 * Cada curso tiene exactamente un profesor responsable (o ninguno).
 *
 * IMPORTANTE: La relación es bidireccional, por lo que al cambiar el profesor
 * se debe sincronizar automáticamente con ambos profesores (anterior y nuevo).
 *
 * @author Renzo Calcatelli - UTN - Programación II
 * @version 1.0
 */
public class Curso {
    // Atributos privados (encapsulamiento)
    private String codigo;
    private String nombre;
    private Profesor profesor;  // Referencia al profesor (lado N:1)

    /**
     * Constructor que crea un curso sin profesor asignado.
     * El profesor comienza en null y puede asignarse posteriormente.
     *
     * @param codigo Código único del curso
     * @param nombre Nombre descriptivo del curso
     */
    public Curso(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.profesor = null;  // Inicialmente sin profesor
    }

    // Métodos getter para acceder a los atributos privados

    /**
     * @return El código único del curso
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return El nombre del curso
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return El profesor responsable del curso (puede ser null)
     */
    public Profesor getProfesor() {
        return profesor;
    }

    /**
     * Asigna o cambia el profesor responsable del curso.
     *
     * ESTE ES EL MÉTODO CRÍTICO para mantener el invariante de asociación.
     *
     * Pasos de sincronización:
     * 1. Si había un profesor anterior, quitarse de su lista
     * 2. Asignar el nuevo profesor
     * 3. Si hay nuevo profesor, agregarse a su lista
     *
     * Esto asegura que la relación bidireccional siempre sea consistente.
     *
     * @param nuevoProfesor Nuevo profesor a asignar (puede ser null para desasignar)
     */
    public void setProfesor(Profesor nuevoProfesor) {
        // PASO 1: Remover este curso del profesor anterior (si existía)
        if (this.profesor != null && this.profesor != nuevoProfesor) {
            // Solo remover si no es el mismo profesor
            this.profesor.eliminarCurso(this);
        }

        // PASO 2: Asignar el nuevo profesor
        this.profesor = nuevoProfesor;

        // PASO 3: Agregar este curso al nuevo profesor (si no es null)
        if (nuevoProfesor != null && !nuevoProfesor.getCursos().contains(this)) {
            nuevoProfesor.agregarCurso(this);
        }
    }

    /**
     * Muestra en consola la información completa del curso.
     * Incluye: código, nombre y profesor asignado (si tiene).
     */
    public void mostrarInfo() {
        String nombreProfesor = (profesor != null) ? profesor.getNombre() : "Sin asignar";

        System.out.println("Código: " + codigo +
                " | Curso: " + nombre +
                " | Profesor: " + nombreProfesor);
    }
}